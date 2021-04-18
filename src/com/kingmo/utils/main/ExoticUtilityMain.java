package com.kingmo.utils.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.kingmo.utils.blocks.Block;
import com.kingmo.utils.blocks.BlockListener;
import com.kingmo.utils.blocks.BlockType;
import com.kingmo.utils.blocks.LoopedRunnable;
import com.kingmo.utils.commands.CommandManager;
import com.kingmo.utils.test.TestMain;

public class ExoticUtilityMain extends JavaPlugin {

	private static final String activeBlock = "blocks.txt";
	private static final String loops = "loops.txt";
	private static File blockFile;
	
	private Logger log = this.getLogger();

	private static File loopFile;

	private static Map<Location, LoopedRunnable> loopMap;
	
	private CommandManager cmdManager;
	
	static {
		ConfigurationSerialization.registerClass(Block.class);
		ConfigurationSerialization.registerClass(BlockType.class);
		ConfigurationSerialization.registerClass(LoopedRunnable.class);
	}
	
	public void onEnable() {
		
		
		this.createSaveFiles();

		log.info("Loading stored data");

		this.loadData();

		log.info("Loaded stored data");

		BlockListener.loadStands();
		
		log.info("Loading commands");
		
		cmdManager = new CommandManager(this);
		TestMain.test();
		
		log.info("ExoticUtils enabled");

	}
	
	public CommandManager getCommandManager() {
		return cmdManager;
	}

	

	private void createSaveFiles() {
		File folder = FileManager.getPluginDir(this);
		blockFile = FileManager.createFile(folder, activeBlock);
		loopFile = FileManager.createFile(folder, loops);
	}

	public void onDisable() {

		BlockListener.clearStands();
		
		log.info("Saving stored data");

		this.saveData();

		log.info("Saved stored data");

		this.getLogger().log(Level.INFO, "ExoticUtils disabled");
		
		
	}

	private void saveData() {
		Utils.save(blockFile, BlockListener.getActiveBlocks());
		log.info("Saved active block information");
		Utils.save(loopFile, loopMap);
		log.info("Saved out-going loop information");
	}

	@SuppressWarnings("unchecked")
	private void loadData() {

		Map<Location, Block> active = (Map<Location, Block>) Utils.loadData(blockFile) == null
				? new HashMap<>()
				: (Map<Location, Block>) Utils.loadData(blockFile);

		Bukkit.getPluginManager().registerEvents(new BlockListener(active), this);

		loopMap = (Map<Location, LoopedRunnable>) Utils.loadData(loopFile) == null ? new HashMap<>()
				: (Map<Location, LoopedRunnable>) Utils.loadData(loopFile);

		for (Location loc : loopMap.keySet()) {
			
			LoopedRunnable run = loopMap.get(loc);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"), run,
					run.getTimeLeft());
			
			run.setBlock(active.get(loc));
			
		}

	}

	public static ExoticUtilityMain getInstance() {
		return (ExoticUtilityMain) Bukkit.getPluginManager().getPlugin("ExoticUtils");
	}

	public static void addLoop(Location loc, LoopedRunnable loop) {
		loopMap.put(loc,
				loop);
	}
	
	public static void removeLoop(Location loc) {
		loopMap.remove(loc);
	}

}
