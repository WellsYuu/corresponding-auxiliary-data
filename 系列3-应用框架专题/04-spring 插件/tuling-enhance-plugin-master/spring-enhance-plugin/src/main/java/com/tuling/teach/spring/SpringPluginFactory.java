package com.tuling.teach.spring;

import java.util.List;

public interface SpringPluginFactory {
	
	/**
	 * 装载指定插件
	 * 
	 * @param pluginId
	 */
	public void activePlugin(String pluginId);

	/**
	 * 移除指定插件
	 * 
	 * @param pluginId
	 */
	public void disablePlugin(String pluginId);
	
	/**
	 * 安装插件
	 * @param plugin
	 */
	public void installPlugin(PluginConfig plugin,Boolean load);
	
	/**
	 * 卸载插件
	 * @param plugin
	 */
	public void uninstallPlugin(PluginConfig plugin);
	
	public List<PluginConfig> getPluginList();

}
