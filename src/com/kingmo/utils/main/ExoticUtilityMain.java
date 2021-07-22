package com.kingmo.utils.main;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import com.kingmo.utils.blocks.Block;
import com.kingmo.utils.blocks.BlockListener;
import com.kingmo.utils.blocks.BlockType;
import com.kingmo.utils.blocks.GiveBlockCommand;
import com.kingmo.utils.blocks.LoopedRunnable;
import com.kingmo.utils.commands.CommandManager;
import com.kingmo.utils.glow.GlowNameSpaced;
import com.kingmo.utils.inventory.InventoryListener;
import com.kingmo.utils.inventory.InventorySerializable;
import com.kingmo.utils.nbt.NBTBase;


public class ExoticUtilityMain extends JavaPlugin {

	private static final String activeBlock = "blocks.txt";
	private static final String loops = "loops.txt";
	private static File blockFile;

	private Logger log = this.getLogger();

	private static File loopFile;

	private static Map<Location, LoopedRunnable> loopMap;

	private CommandManager cmdManager;

	private GlowNameSpaced glow;

	static {
		ConfigurationSerialization.registerClass(Block.class);
		ConfigurationSerialization.registerClass(BlockType.class);
		ConfigurationSerialization.registerClass(LoopedRunnable.class);
		ConfigurationSerialization.registerClass(NBTBase.class);
		ConfigurationSerialization.registerClass(InventorySerializable.class);
	}

	@Override
	public void onEnable() {

		registerEvents();

		registerEnchants();

		createSaveFiles();

		log.info("Loading commands");

		cmdManager = new CommandManager(this);

		this.loadCommands();

		//TestMain.test();


		log.info("ExoticUtils enabled");

	}

	private void registerEnchants() {
		glow = new GlowNameSpaced();

		ExoticUtilityMain.registerEnchantment(glow);
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

	}

	public void loadAPI() {
		this.loadData();

		log.info("Loaded stored data");

		BlockListener.loadStands();
	}

	private void loadCommands() {
		cmdManager.registerCommand(new GiveBlockCommand());

	}

	public CommandManager getCommandManager() {
		return cmdManager;
	}

	private void createSaveFiles() {
		File folder = FileManager.getPluginDir(this);
		blockFile = FileManager.createFile(folder, activeBlock);
		loopFile = FileManager.createFile(folder, loops);
	}

	@Override
	public void onDisable() {

		unregisterEnchants();

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
		Map<Location, Block> active = new HashMap<>();

		 active = (Map<Location, Block>) Utils.loadData(blockFile) == null ? new HashMap<>()
				: (Map<Location, Block>) Utils.loadData(blockFile);

		Bukkit.getPluginManager().registerEvents(new BlockListener(active), this);

		loopMap = (Map<Location, LoopedRunnable>) Utils.loadData(loopFile) == null ? new HashMap<>()
				: (Map<Location, LoopedRunnable>) Utils.loadData(loopFile);

		for (Location loc : loopMap.keySet()) {

			LoopedRunnable run = loopMap.get(loc);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, run,
					run.getTimeLeft());

			run.setBlock(active.get(loc));

		}

	}

	public static ExoticUtilityMain getInstance() {
		return (ExoticUtilityMain) Bukkit.getPluginManager().getPlugin("ExoticUtils");
	}

	public static void addLoop(Location loc, LoopedRunnable loop) {
		loopMap.put(loc, loop);
	}

	public static void removeLoop(Location loc) {
		loopMap.remove(loc);
	}

	private void unregisterEnchants() {
		ExoticUtilityMain.unregisterEnchantment(glow);
	}

	@SuppressWarnings("deprecation")
	public static void unregisterEnchantment(Enchantment ench) {
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byKey");
			Field byNameField = Enchantment.class.getDeclaredField("byName");

			byIdField.setAccessible(true);
			byNameField.setAccessible(true);

			@SuppressWarnings("unchecked")
			HashMap<NamespacedKey, Enchantment> byId = (HashMap<NamespacedKey, Enchantment>) byIdField.get(null);
			@SuppressWarnings("unchecked")
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);

			if(byId.containsKey(ench.getKey()))
			byId.remove(ench.getKey());

			if(byName.containsKey(ench.getName()))
			byName.remove(ench.getName());
			} catch (Exception ignored) { }
	}

	 public static void registerEnchantment(Enchantment enchantment) {
	        boolean registered = true;
	        try {
	            Field f = Enchantment.class.getDeclaredField("acceptingNew");
	            f.setAccessible(true);
	            f.set(null, true);
	            Enchantment.registerEnchantment(enchantment);
	        } catch (Exception e) {
	            registered = false;
	            e.printStackTrace();
	        }
	        if(registered){
	            // It's been registered!
	        }
	    }


}
