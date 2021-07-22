package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class LoopedRunnable implements Runnable, ConfigurationSerializable {

	private Block block;
	private final OfflinePlayer player;
	private final Location location;
	private long endTime;
	private long startTime;


	public LoopedRunnable(Block block, OfflinePlayer player, Location loc) {
		this.block = block;
		this.player = player;
		this.location = loc;
		endTime = System.currentTimeMillis();
	}

	public LoopedRunnable(Map<String, Object> map) {
		this.block = (Block) map.get("block");
		this.player = Bukkit.getOfflinePlayer(UUID.fromString((String) map.get("player")));
		this.endTime = System.currentTimeMillis() + (long) map.get("time");
		this.startTime = System.currentTimeMillis();
		location = block.getBlock().getLocation();
	}

	@Override
	public void run() {
		if (!block.isBroken()) {
		block.loopedRun(location, player);
			startTime = endTime;
			endTime += block.getType().getDelay();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"), this,
					block.getType().getDelay());
		}
	}

	public long getEndTime() {
		return endTime;
	}

	public long getTimeLeft() {
		return endTime-System.currentTimeMillis();
	}

	public long getStartTime() {
		return startTime;
	}

	@Override
	public Map<String, Object> serialize(){
		Map<String, Object> map = new HashMap<>();

		map.put("time", this.getTimeLeft());
		map.put("block", block);
		map.put("player", player.getUniqueId().toString());
		return map;
	}

	public void setBlock(Block b) {
		this.block=b;
	}

	public Block getBlock() {
		return block;
	}
}
