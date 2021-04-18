package com.kingmo.utils.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.Plugin;

public class FileManager {

	public static File createFile(File dir, String name) {
		File newFile = new File(dir.getPath() + "/" + name);
		
		if(!newFile.exists())
			try {
				newFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return newFile;
	}
	
	public static File getPluginDir(Plugin plugin) {
		
		File dir = plugin.getDataFolder();
		
		if(!dir.exists())dir.mkdir();
		
		return dir;	
	}
	
}
