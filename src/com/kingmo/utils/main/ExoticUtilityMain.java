package com.kingmo.utils.main;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class ExoticUtilityMain extends JavaPlugin {

	public void onEnable() {
		this.getLogger().log(Level.INFO, "ExoticUtils enabled");
	}
	
	public void onDisable() {
		this.getLogger().log(Level.INFO, "ExoticUtils disabled");
	}
	
}
