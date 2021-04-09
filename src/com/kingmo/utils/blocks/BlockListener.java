package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.main.Utils;

public class BlockListener implements Listener {

	private static Map<String, BlockType> registeredBlocks = new HashMap<>();
	private static Map<Location, Block> activeBlocks = new HashMap<>();

	public BlockListener(List<BlockType> type) {

		for (BlockType t : type) {
			registeredBlocks.put(t.getItemName(), t);
		}

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {

		ItemStack stack = e.getItemInHand();

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

		block.run();
		if (blockType.getDelay() > 0)
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"),
					new LoopedRunnable(block), block.getType().getDelay());

		block.onPlace(e.getPlayer());

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
		
		activeBlocks.remove(loc);
	}

	public boolean registerBlock(BlockType type) {
		String key = type.getItemName();
		if (registeredBlocks.containsKey(key))
			return false;
		registeredBlocks.put(key, type);
		return true;

	}

}
