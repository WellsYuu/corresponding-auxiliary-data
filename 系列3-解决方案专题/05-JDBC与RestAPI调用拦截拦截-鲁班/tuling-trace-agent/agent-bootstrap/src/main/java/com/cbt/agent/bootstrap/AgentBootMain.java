package com.cbt.agent.bootstrap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.omg.CORBA.portable.ApplicationException;

import sun.misc.IOUtils;

public class AgentBootMain {

    private static final String IS_OPEN = "open";
    public static final String PRO_KEY = "proKey";
    public static final String PRO_SECRET = "proSecret";
    private static final String SERVICE_URL = "serviceUrl";
    static final String FAST_JSON_URL = "fastJson.url";
    private static final String APP_ID = "appId";
    private static final String DEBUG_ON="debug";

    public static final String HTTP_CONNECT_TIMEOUT = "httpConnectTimeout";
    public static final String HTTP_READ_TIMEOUT = "httpReadTimeout";
    public static final String HTTP_DOWNLOAD_TIMEOUT = "httpDownloadTimeout";

    public static final String DEFAULT_SERVICE_URL = "http://www.cbtu.pro/api?method=client.login";
    private static final String DEFAULT_APP_NAME = "com.cbt.agent.core.DefaultApplication";

    private static CbtSessionInfo session;
    private static AgentApplication application;
    // 单位毫秒
    private static int httpConnectTimeout = 3000;
    private static int httpReadTimeout = 10000;
    private static int httpDownloadTimeout = 20000;
    // debug开关
    private static boolean debug=false;

    // 参数:
    // pro.key=
    // 访问远程服务 获取属性配置,

    public static void agentmain(String args, Instrumentation inst) {

    }

    // 在应用启动前调用
    public static void premain(String agentArgs, Instrumentation inst) {
        try {
            if (debug) {
                outPrintln(String.format("藏宝图服务开始启动,参数:%s",agentArgs));
            }
            run(agentArgs, inst);
        } catch (Throwable e) {
            new Exception("藏宝图服务加载异常:", e).printStackTrace();
        }
    }

    // 在应用启动前调用
    public static void run(String agentArgs, Instrumentation inst) {
        // 装载配置文件
        if (debug) {
            outPrintln("藏宝图服务开始初始化配置文件");
        }
        String defaultProperties = null;
        if (agentArgs != null && !agentArgs.trim().equals("")) {
            defaultProperties = agentArgs.replaceAll(",", "\n");
        }
        Properties agentConfig = null;
        try {
            Properties config = loadLocalProperties(defaultProperties,"/conf/cbt.properties");
            initLocalConfig(config);
            agentConfig = config;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
        }
        if (debug) {
            outPrintln("藏宝图服务初始化配置文件结束");
        }
        if (!Boolean.valueOf(agentConfig.getProperty(IS_OPEN, "false"))) {
            if (debug) {
                outPrintln(String.format("参数%s=false藏宝图服务未开启,服务已停止"));
            }
            return;
        }

        // 读取fastjson-1.1.23.jar 配置默认属性
        if (debug) {
            outPrintln(String.format("藏宝图服务正在初始化fastJson组件"));
        }
        if (agentConfig != null && !agentConfig.containsKey(FAST_JSON_URL)) {
            if (debug) {
                outPrintln(String.format("藏宝图服务正在加载默认fastjson组件"));
            }
            getAgentRootPath();
            File fastFile = new File(getAgentRootPath(), "fastjson-1.1.23.jar");
            if (!fastFile.exists()) {
                throw new RuntimeException("藏宝图服务启动失败:找到不文件" + fastFile.toString());
            }
            if (debug) {
                outPrintln(String.format("藏宝图服务默认fastjson 组件加载成功%s",fastFile.toString()));
            }
            agentConfig.setProperty(FAST_JSON_URL, "file:" + fastFile.toString());
        } else if (agentConfig != null && agentConfig.containsKey(FAST_JSON_URL)) {
            if (debug) {
                outPrintln(String.format("藏宝图服加载自定义fastJson组件成功%s", agentConfig.get(FAST_JSON_URL)));
            }
        }
        if (debug) {
            outPrintln(String.format("藏宝图服务初始化fastJson组件结束"));
        }
        if (agentConfig != null) {

            String url = agentConfig.getProperty(SERVICE_URL, DEFAULT_SERVICE_URL);
            String proKey = agentConfig.getProperty(PRO_KEY);
            String secret = agentConfig.getProperty(PRO_SECRET);
            if (debug) {
                outPrintln(String.format("藏宝图服务开始登陆远程服务中心%s",url));
            }
            try {
                session = loginRemoteServer(url, proKey, secret);
            } catch (Exception e) {
                errPrintln(String.format("登陆藏宝图服务中心失败：%s", e.getMessage()));
            }
            if (debug) {
                if(session!=null) {
                    outPrintln(String.format("藏宝图服务开始登陆远程服务中心成功"));
                }
            }
        }

        File clientFile = null;
        // 检查更新客户端版本
        if (session != null) {
            if (debug) {
                outPrintln(String.format("藏宝图服务正在检查agent版本"));
            }
            try {
                File file = getCacheFile();
                FileInputStream inputStream = new FileInputStream(file);
                byte[] currentLib = IOUtils.readFully(inputStream, -1, false);
                inputStream.close();
                if (!session.getClientMd5().equals(UtilEncryption.md5(currentLib))) {
                    clientFile = downloadUpldateClientLib(session);
                } else {
                    clientFile = file;
                }
            } catch (Exception e) {
                if (debug) {
                    e.printStackTrace();
                }
                errPrintln(String.format("藏宝图服务更新agent版本失败：%s", e.getMessage()));
                return;
            }
        }

        // 附加 agent class path 并装载boot 类
        Class<ApplicationException> appClass = null;
        if (clientFile != null) {
            try {
                if (debug) {
                    outPrintln(String.format("藏宝图服务正在加载agent组件,来自%s",clientFile.toString()));
                }
                URLClassLoader classLoader = (URLClassLoader) AgentBootMain.class.getClassLoader();
                Method addurl_method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addurl_method.setAccessible(true);
                addurl_method.invoke(classLoader, clientFile.toURI().toURL());
                appClass = (Class<ApplicationException>) classLoader.loadClass(DEFAULT_APP_NAME);

                if (debug) {
                    outPrintln(String.format("藏宝图服务agent组件加载成功"));
                }
            } catch (Exception e) {
                new Exception("藏宝图服务agent组件加载失败",e).printStackTrace();
            }
        }

        // 实例化boot 对象,并进行初始启动
        if (appClass != null) {
            AgentApplication app = null;
            try {
                if (debug) {
                    outPrintln(String.format("藏宝图服务正在初始化agent应用:%s",appClass.getName()));
                }
                app = (AgentApplication) appClass.getMethod("getInstance").invoke(appClass);

                app.init(session, agentConfig, null, inst);
                // 添加转换器
                inst.addTransformer(app.getTransformer());
                application = app;

            } catch (Exception e) {
                new Exception("藏宝图服务agent 应用初始化失败",e).printStackTrace();
            }
        }

        if (application != null) {
            outPrintln("藏宝图服务启动成功!");
        }
    }

    /**
     * 登陆远程服务器
     * 
     * @param proKey
     * @param secret
     * @param appId
     * @return
     */
    private static CbtSessionInfo loginRemoteServer(String url, String proKey, String secret) throws Exception {
        CbtSessionInfo session = null;
        String currentTime = System.currentTimeMillis() + "";
        LoginParam param = new LoginParam();
        param.setProKey(proKey);
        param.setAppPath(System.getProperty("user.dir"));
        param.setClientMacAddress(NetUtils.getLocalMac());
        param.setClientIp(NetUtils.getLocalHost());
        param.setLoginTime(System.currentTimeMillis());
        param.setPlatform("java");
        String sign = secret + param.toString() + secret;
        sign = UtilEncryption.md5(sign.getBytes());
        Map<String, String> map = param.toMaps();
        map.put("sign", sign);
        String result;
        if (url.startsWith("http")) {
            result = invokeHttpApi(url, map);
        } else {
            InputStream s = new URL(url).openStream();
            try {
                result = new String(sun.misc.IOUtils.readFully(s, -1, false));
            } finally {
                s.close();
            }
        }
        session = converyToSessionBean(result);
        outPrintln("藏宝图服务登陆成功!");
        return session;
    }

    private static CbtSessionInfo converyToSessionBean(String result) {
        Hashtable<String, String[]> map = HttpUtils.parseQueryString(result);
        CbtSessionInfo session = new CbtSessionInfo();
        for (Entry<String, String[]> e : map.entrySet()) {
            if (e.getKey().equals("sessionId")) {
                session.setSessionId(e.getValue()[0]);
            } else if (e.getKey().equals("proKey")) {
                session.setProKey(e.getValue()[0]);
            } else if (e.getKey().equals("appId")) {
                session.setAppId(e.getValue()[0]);
            } else if (e.getKey().equals("clientVersion")) {
                session.setClientVersion(e.getValue()[0]);
            } else if (e.getKey().equals("clientMd5")) {
                session.setClientMd5(e.getValue()[0]);
            } else if (e.getKey().equals("clientUploadUrls")) {
                try {
                    String str = new String(UtilEncryption.decodeBase64(e.getValue()[0]), UtilEncryption.UTF_8);
                    session.setClientUploadUrls(str.split(","));
                } catch (IOException e1) {
                    if (debug) {
                        e1.printStackTrace();
                    }
                    throw new RuntimeException(String.format("服务返回数据解析失败:%s:%s", e1.getClass().getName(), e1.getMessage()));
                }
            } else if (e.getKey().equals("loginTime")) {
                session.setLoginTime(Long.parseLong(e.getValue()[0]));
            } else if (e.getKey().equals("configs")) {
                try {
                    byte[] bytes = UtilEncryption.decodeBase64(e.getValue()[0]);
                    Properties p = new Properties();
                    p.load(new ByteArrayInputStream(bytes));
                   session.setConfigs(p);
                } catch (IOException e1) {
                    if (debug) {
                        e1.printStackTrace();
                    }
                    throw new RuntimeException(String.format("服务返回数据解析失败:%s:%s", e1.getClass().getName(), e1.getMessage()));
                }
            }

        }
        return session;
    }

    private static File downloadUpldateClientLib(CbtSessionInfo session) throws Exception {
        long beginTime = System.currentTimeMillis();

        // 多更新渠道
        for (String url : session.getClientUploadUrls()) {
            outPrintln(String.format("正在尝试更新藏宝图服务.... 版本=%s ,url=%s", session.getClientVersion(), url));
            URL rurl = new URL(url);
            URLConnection conn = rurl.openConnection();
            conn.setConnectTimeout(httpConnectTimeout);
            conn.setReadTimeout(httpDownloadTimeout);
            InputStream input = null;
            try {
                conn.connect();
                input = conn.getInputStream();
            } catch (IOException e) {
                if(debug) {
                    e.printStackTrace();
                }
                errPrintln(String.format("藏宝图服务版本更新失败：%s,url=%s", e.getMessage(), url));
                continue;
            }

            // 保存至缓存目录
            FileOutputStream outputStream = null;
            try {
                File file = getCacheFile();
                outputStream = new FileOutputStream(file);
                copyLarge(input, outputStream, new byte[1024 * 4]);
                outputStream.flush();
                long endTime = System.currentTimeMillis();
                String ys = new BigDecimal(endTime - beginTime).divide(new BigDecimal(1000)).toString() + "(秒)";
                outPrintln(String.format("藏宝图服务版本更新成功：用时=%s,当前版本=%s", ys, session.getClientVersion()));
                return file;
            } finally {
                if (input != null)
                    input.close();
                if (outputStream != null)
                    outputStream.close();
            }
        }
        return null;
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    // 获取缓存文件,如果没有就创建一个
    private static File getCacheFile() throws Exception {
        String f = System.getProperty("user.dir") + "/temp/cbt-agent/cbt-agent.lib";
        if (debug) {
            outPrintln(String.format("藏宝图服务正在读取agent 本地缓存%s",f));
        }
        File file = new File(f);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                if (debug) {
                    outPrintln(String.format("藏宝图服务正在创建agent 本地缓存文件%s",f));
                }
                file.createNewFile();
            } catch (IOException e) {
                throw new Exception(String.format("创建藏宝图缓存文件失败：%s:%s", e.getClass().getName(), e.getMessage()));
            }
        }
        return file;
    }

    private static void initLocalConfig(Properties config) throws Exception {
        if (!Boolean.valueOf(config.getProperty(IS_OPEN, "false"))) {
            return;
        }

        if (config.getProperty(PRO_KEY) == null) {
            throw new Exception(String.format("配置项'%s'为空!", PRO_KEY));
        } else if (config.getProperty(PRO_SECRET) == null) {
            throw new Exception(String.format("配置项'%s'为空!", PRO_SECRET));
        }
        if (config.containsKey(FAST_JSON_URL)) {
            File f = new File(config.getProperty(FAST_JSON_URL));
            if (!f.exists() || !f.isFile()) {
                throw new Exception(String.format("错误的配置'%s' 指定文件不存在 %s!", FAST_JSON_URL, config.getProperty(FAST_JSON_URL)));
            }
        }
        if (config.containsKey(HTTP_CONNECT_TIMEOUT)) {
            httpConnectTimeout = Integer.parseInt(config.getProperty(HTTP_CONNECT_TIMEOUT));
        }
        if (config.containsKey(HTTP_READ_TIMEOUT)) {
            httpReadTimeout = Integer.parseInt(config.getProperty(HTTP_READ_TIMEOUT));
        }
        if (config.containsKey(HTTP_DOWNLOAD_TIMEOUT)) {
            httpDownloadTimeout = Integer.parseInt(config.getProperty(HTTP_DOWNLOAD_TIMEOUT));
        }
        if (config.containsKey(DEBUG_ON)) {
            debug=Boolean.valueOf(config.getProperty(DEBUG_ON,"false"));
        }
    }

    private static String invokeHttpApi(String url, Map<String, String> params) {
        String result = new String();
        String param = url.contains("?") ? "" : "?";
        for (Entry<String, String> e : params.entrySet()) {
            param += "&" + e.getKey() + "=" + e.getValue();
        }

        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "text/html;charset=UTF-8");
            // conn.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(httpConnectTimeout);
            conn.setReadTimeout(httpReadTimeout);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            String errorCode = conn.getHeaderField("cbt-error-code");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // 服务返回异常
            if (errorCode != null) {
                throw new RuntimeException(result);
            }

        } catch (IOException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new RuntimeException(String.format("无法正常连接藏宝图服务: %s:%s", e.getClass().getName(), e.getMessage()), e);
        } finally {
            // 关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        return result;
    }

    public static Properties loadLocalProperties(String defaultProperties,String path) {
        Properties appPros = null;
        InputStream stream = null;
        String fileText = System.getProperty("user.dir") + path;
        if (fileText != null && !fileText.trim().equals("")) {
            File file = new File(fileText);
            if (file.exists() && file.isFile()) {
                try {
                    stream = new FileInputStream(file);
                    outPrintln(String.format("加载藏宝图配置文件来自%s", file.toString()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // 加载全局配置文件
        if (stream == null) {
            File file = new File(getAgentRootPath(), path);
            if (file.exists() && file.isFile()) {
                try {
                    stream = new FileInputStream(file);
                    outPrintln(String.format("加载藏宝图配置文件来自%s", file.toString()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // 加载默认配置文件
        if (stream == null && defaultProperties != null) {
            stream = new ByteArrayInputStream(defaultProperties.getBytes());
            outPrintln(String.format("加载藏宝图配置文件来自%s ", "jvm启动参数"));
        }
        if (stream != null) {
            try {
                appPros = new Properties();
                appPros.load(stream);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return appPros;
    }

    public static String getAgentRootPath() {
        File bootRoot;
        URL bootUrl = AgentBootMain.class.getProtectionDomain().getCodeSource().getLocation();
        String bootPath;
        try {
            bootPath = java.net.URLDecoder.decode(bootUrl.getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        if (bootUrl.getProtocol().equals("file")) {
            bootRoot = new File(bootPath).getParentFile();
        } else {
            // 实际情况中不会发生该异常
            throw new RuntimeException("cbt agent url Protocol must file ，The actual is url=" + bootUrl);
        }
        return bootRoot.toString();
    }
    private  static void outPrintln(String str ){
        DateFormat df=new SimpleDateFormat("[yyyy-MM-dd:hh:mm:ss]");
        System.out.println(df.format(new Date())+str);
    }
    private  static void errPrintln(String str ){
        DateFormat df=new SimpleDateFormat("[yyyy-MM-dd:hh:mm:ss]");
        System.err.println(df.format(new Date())+str);
    }
}
