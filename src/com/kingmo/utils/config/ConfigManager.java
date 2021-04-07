package com.kingmo.utils.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

	private YamlConfiguration config;
	private Plugin plugin;
	
	public ConfigManager(YamlConfiguration config, Plugin plugin) {
		this.config = config;
		this.plugin = plugin;
	}
	
	
	public Map<String, Object> getMap(String path) throws NullPointerException{
		
		Map<String, Object> returnable = new HashMap<>();
		
	
		if(!config.contains(path))throw new NullPointerException(path + " was not located in " + config.getName());
	
		for(String obj : config.getConfigurationSection(path).getKeys(false)) {	
		
			returnable.put(obj, config.get(path + "." + obj));
			
		}
		
		return returnable;
	}
	
	public Map<String, Double> getDoubleMap(String path) throws NullPointerException{
		
		Map<String, Double> returnable = new HashMap<>();

		if(!config.contains(path))throw new NullPointerException(path + " was not located in " + config.getName());
		for(String obj : config.getConfigurationSection(path).getKeys(false)) {	
			returnable.put(obj, config.getDouble(path + "." + obj));	
		}
		return returnable;
	}
	
	public Map<String, String> getStringMap(String path) throws NullPointerException{
		
		Map<String, String> returnable = new HashMap<>();

		if(!config.contains(path))throw new NullPointerException(path + " was not located in " + config.getName());
		for(String obj : config.getConfigurationSection(path).getKeys(false)) {	
			returnable.put(obj, config.getString(path + "." + obj));	
		}
		return returnable;
	}
	
	public Map<String, List<?>> getListMap(String path) throws NullPointerException{
		
		Map<String, List<?>> returnable = new HashMap<>();

		if(!config.contains(path))throw new NullPointerException(path + " was not located in " + config.getName());
		for(String obj : config.getConfigurationSection(path).getKeys(false)) {	
			returnable.put(obj, config.getList(path + "." + obj));	
		}
		return returnable;
	}
	
	
	
	
	

	public YamlConfiguration getConfig() {
		return config;
	}


	public Plugin getPlugin() {
		return plugin;
	}
	
	
	
	
}
