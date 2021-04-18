package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.main.ExoticUtilityMain;
import com.kingmo.utils.main.Utils;

/**
 * 
 * Handles all custom block placement and breaking. <br>
 * </br>
 * To use register {@link BlockType} using
 * {@link BlockListener#registerBlock(BlockType)}
 * 
 * @author Kingmo100
 * @since V1.0
 */
public class BlockListener implements Listener {

	private static Map<String, BlockType> registeredBlocks = new HashMap<>();
	private static Map<Location, Block> activeBlocks = new HashMap<>();
	
	
	public BlockListener(List<BlockType> type) {

		for (BlockType t : type) {
			registeredBlocks.put(t.getItemName(), t);
		}

	}

	public BlockListener(Map<Location, Block> active) {
		BlockListener.activeBlocks = active;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {

		ItemStack stack = e.getItemInHand();
		
		Player player = e.getPlayer();
		
		if (!Utils.nonNull(stack))
			return;

		BlockType blockType = registeredBlocks.getOrDefault(stack.getItemMeta().getDisplayName(), null);
		if (blockType == null)
			return;

		Location loc = e.getBlock().getLocation();

		Block block = null;
		try {
			block = blockType.getBlockFromType(loc);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		block.run(loc, player);
		
		LoopedRunnable loop = new LoopedRunnable(block, player, loc);
		if (blockType.getDelay() > 0)
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"),
					loop, block.getType().getDelay());

		
		block.onPlace(e.getPlayer());
		
		ExoticUtilityMain.addLoop(loc, loop);
		
		activeBlocks.put(loc, block);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Location loc = e.getBlock().getLocation();

		if (!activeBlocks.containsKey(loc))
			return;

		Block block = activeBlocks.get(loc);

		block.setBroken(true);
		block.onBreak(e.getPlayer());

		e.getBlock().setType(Material.AIR);
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))loc.getWorld().dropItemNaturally(loc, Block.createItemStack(block.getType()));
		
		if(block.getType().showName())
		block.getArmorStand().remove();
		block.setArmorStand(null);
		
		ExoticUtilityMain.removeLoop(loc);
		activeBlocks.remove(loc);
	}
	
	public static void clearStands() {
		System.out.println(activeBlocks);
		for(Block b: activeBlocks.values()) {
			if(b.getType().showName()) {
				System.err.println(b.getArmorStand());
				b.getArmorStand().remove();
			}
		}
	}

	public static boolean registerBlock(BlockType type) {
		String key = type.getItemName();
		if (registeredBlocks.containsKey(key))
			return false;
		registeredBlocks.put(key, type);
		return true;

	}

	public static void registerBlocks(List<BlockType> types) {

		for (BlockType type : types)
			BlockListener.registerBlock(type);
	}

	public static Map<String, BlockType> getRegisteredBlocks() {
		return BlockListener.registeredBlocks;
	}

	public static Map<Location, Block> getActiveBlocks() {
		return BlockListener.activeBlocks;
	}
	
	public static void addArmorStand(Block block) {
		Location loc = block.getBlock().getLocation().add(0.5, -1, 0.5);
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setCustomName(block.getName());
		as.setCustomNameVisible(true);
		as.setGravity(false);
		
		block.setArmorStand(as);
	}

	public static void loadStands() {
		for(Block b: activeBlocks.values()) {
			if(b.getType().showName()) {
				BlockListener.addArmorStand(b);
			}
		}
		
	}

}
