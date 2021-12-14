package com.tuling.teach.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// TODO 待完成项： 本机应用隔离
// TODO 基于表达示进行装载 至指定切面 
// TODO 插件版本更新问题解决
public class DefaultSpringPluginFactory implements ApplicationContextAware, InitializingBean, SpringPluginFactory {

	private static final String BASE_DIR;
	private Map<String, PluginConfig> configs = new HashMap<>();
	private ApplicationContext applicationContext; // 应用上下文
	private Map<String, Advice> adviceCache = new HashMap<>();
	static {
		BASE_DIR = System.getProperty("user.home") + "/.plugins/";
	}

	/**
	 * 激活插件 1、获取plugin 配置 2、获取spring ioc 当中所有的bean 3、安装条件验证 4、构建切面通知对象
	 * 5、添加至aop切面
	 */
	@Override
	public void activePlugin(String pluginId) {
		if (!configs.containsKey(pluginId)) {
			throw new RuntimeException(String.format("指定插件不存在 id=%s", pluginId));
		}
		PluginConfig config = configs.get(pluginId);

		for (String name : applicationContext.getBeanDefinitionNames()) {
			Object bean = applicationContext.getBean(name);
			if (bean == this)
				continue;
			if (!(bean instanceof Advised))
				continue;
			if (findAdvice(config.getClassName(), (Advised) bean) != null)
				continue;

			Advice advice = null;
			try {
				advice = buildAdvice(config);
				((Advised) bean).addAdvice(advice);
			} catch (Exception e) {
				throw new RuntimeException("安装失败", e);
			}
		}
		try {
			config.setActive(true);
			storeConfigs();
		} catch (IOException e) {
			// TODO 需要回滚已添加的切面
			throw new RuntimeException("激活失败", e);
		}
	}
	/**
	 * 1、获取配置
	 * 2、判断指定的bean 是否已切入了指定的通知
	 * 3、调用remove 方法进行移除
	 * 4、保存配置至本地
	 */
	@Override
	public void disablePlugin(String pluginId) {
		if (!configs.containsKey(pluginId)) {
			throw new RuntimeException(String.format("指定插件不存在 id=%s", pluginId));
		}
		PluginConfig config = configs.get(pluginId);

		for (String name : applicationContext.getBeanDefinitionNames()) {
			Object bean = applicationContext.getBean(name);
			if (bean instanceof Advised) {
				Advice advice = findAdvice(config.getClassName(), (Advised) bean);
				if (advice != null)
					((Advised) bean).removeAdvice(advice);
			}
		}
		config.setActive(false);
		try {
			storeConfigs();
		} catch (IOException e) {
			// TODO 保存失败需要回滚
			throw new RuntimeException("禁用失败", e);
		}

	}

	private Advice findAdvice(String className, Advised advised) {
		for (Advisor a : advised.getAdvisors()) {
			if (a.getAdvice().getClass().getName().equals(className)) {
				return a.getAdvice();
			}
		}
		return null;
	}

	/**
	 * 安装我们的插件 1 下载插件jar 包 2 装载插件jar 包装载至 classLoader 3 获 取 plugin class 并实例化 4
	 * 把advice 添加至 aop bean 5 将插件的配置保存至本地
	 */
	@Override
	public void installPlugin(PluginConfig plugin, Boolean active) {
		if (configs.containsKey(plugin.getId())) {
			throw new RuntimeException(String.format("已存在指定的插件 id=%s", plugin.getId()));
		}
		// 填充至插件库
		configs.put(plugin.getId(), plugin);

		// 下载远程插件
		try {
			buildAdvice(plugin);
		} catch (Exception e1) {
			configs.remove(plugin.getId());
			throw new RuntimeException(String.format("插件构建失败 id=%s", plugin.getId()), e1);
		}

		// 持久化至本地库
		try {
			storeConfigs();
		} catch (IOException e) {
			configs.remove(plugin.getId());
			throw new RuntimeException(String.format("插件安装失败 id=%s", plugin.getId()), e);
		}

		if (active != null && active) {
			// 安装当前 插件
			activePlugin(plugin.getId());
		}

	}
	/**
	 * 保存配置至本地
	 * TODO 序列化方式 改为json 更为合理
	 * @throws IOException
	 */
	private void storeConfigs() throws IOException {
		String baseDir = BASE_DIR;
		File configFile = new File(baseDir + "PluginConfigs.obj");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			configFile.createNewFile();
		}
		OutputStream out = new FileOutputStream(configFile);
		ObjectOutputStream output = new ObjectOutputStream(out);
		Map<String, PluginConfig> storeConfig = new HashMap<>();
		storeConfig.putAll(configs);
		output.writeObject(storeConfig);
		output.flush();
		output.close();
	}

	@Override
	public void uninstallPlugin(PluginConfig plugin) {
			disablePlugin(plugin.getId()); // 禁用指定插件
			configs.remove(plugin.getId()); // 缓存中删除
			try {
				storeConfigs(); // 更新保存配置
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loaderLocals();
	}

	/**
	 * 装载本地已安装插件
	 */
	public void loaderLocals() {
		try {
			Map<String, PluginConfig> localConfig = readerLocalConfigs();
			if (localConfig == null) {
				return;
			}
			configs.putAll(localConfig);
			for (PluginConfig config : localConfig.values()) {
				if (config.getActive())
					activePlugin(config.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取本地配置
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, PluginConfig> readerLocalConfigs() throws Exception {
		String baseDir = BASE_DIR;
		File configFile = new File(baseDir + "PluginConfigs.obj");
		if (!configFile.exists()) {
			return null;
		}
		InputStream in = new FileInputStream(configFile);
		ObjectInputStream input;
		Map<String, PluginConfig> result = null;
		try {
			input = new ObjectInputStream(in);
			result = (Map<String, PluginConfig>) input.readObject();
		} finally {
			in.close();
		}
		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private String getLocalJarFile(PluginConfig config) {
		String jarName = config.getJarRemoteUrl().substring(config.getJarRemoteUrl().lastIndexOf("/"));
		return BASE_DIR + jarName;
	}

	public Advice buildAdvice(PluginConfig config) throws Exception {
		if (adviceCache.containsKey(config.getClassName())) {
			return adviceCache.get(config.getClassName());
		}

		File jarFile = new File(getLocalJarFile(config));
		// 从远程下载plugin 文件至本地
		if (!jarFile.exists()) {
			URL url = new URL(config.getJarRemoteUrl());
			InputStream stream = url.openStream();
			jarFile.getParentFile().mkdirs();
			try {
				Files.copy(stream, jarFile.toPath());
			} catch (Exception e) {
				jarFile.deleteOnExit();
				throw new RuntimeException(e);
			}
			stream.close();
		}
		// 将本地jar 文件加载至 classLoader
		URLClassLoader loader = (URLClassLoader) getClass().getClassLoader();
		URL targetUrl = jarFile.toURI().toURL();
		boolean isLoader = false;
		for (URL url : loader.getURLs()) {
			if (url.equals(targetUrl)) {
				isLoader = true;
				break;
			}
		}
		if (!isLoader) {
			Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			add.setAccessible(true);
			add.invoke(loader, targetUrl);
		}
		// 初始化 Plugin Advice 实例化
		Class<?> adviceClass = loader.loadClass(config.getClassName());
		if (!Advice.class.isAssignableFrom(adviceClass)) {
			throw new RuntimeException(
					String.format("plugin 配置错误 %s非 %s的实现类 ", config.getClassName(), Advice.class.getName()));
		}
		adviceCache.put(adviceClass.getName(), (Advice) adviceClass.newInstance());
		return adviceCache.get(adviceClass.getName());
	}

	public void doBefore(JoinPoint joinPoint) {

	}

	private static long copy(InputStream source, OutputStream sink) throws IOException {
		long nread = 0L;
		byte[] buf = new byte[8192];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
			nread += n;
		}
		return nread;
	}

	@Override
	public List<PluginConfig> getPluginList() {
		try {
			return new ArrayList<>(readerLocalConfigs().values());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("插件获取失败", e);
		}
	}
}
