package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener{

	private static Map<String, BlockType> registeredBlocks = new HashMap<>();
	private static Map<Location, Block> activeBlocks = new HashMap<>();
	
	public BlockListener(List<BlockType> type) {
		
		for(BlockType t: type) {
			registeredBlocks.put(t.getItemName(), t);
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		
		
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
	}
	
	
	public boolean registerBlock(BlockType type) {
		String key = type.getItemName();
		if(registeredBlocks.containsKey(key))return false;
		registeredBlocks.put(key, type);
		return true;
		
	}
	
}
