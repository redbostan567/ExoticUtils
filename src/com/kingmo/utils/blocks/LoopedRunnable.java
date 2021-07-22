package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * 
 * @author Kingmo100
 * @apiNote an automatic, self-renewing runnable, which will restart on reboot
 *          of server.
 */
public class LoopedRunnable implements Runnable, ConfigurationSerializable {
	/**
	 * Block which is currently looping
	 */
	private Block block;
	/**
	 * Player which the Block will react with.
	 */
	private final OfflinePlayer player;
	/**
	 * the Location of the Block
	 */
	private final Location location;
	/**
	 * Time which the next loop of the runnable will begin
	 */
	private long endTime;
	/**
	 * Time at which the most recent loop of the runnable started
	 */
	private long startTime;

	/**
	 * 
	 * @param block  : block which will have its
	 *               {@link Block#loopedRun(Location, OfflinePlayer)} called on the
	 *               specified delay
	 * @param player : the {@link OfflinePlayer} which
	 *               {@link Block#loopedRun(Location, OfflinePlayer)} calls for
	 * @param loc    : the {@link Location} which
	 *               {@link Block#loopedRun(Location, OfflinePlayer)} calls for
	 */
	public LoopedRunnable(Block block, OfflinePlayer player, Location loc) {
		this.block = block;
		this.player = player;
		this.location = loc;
		endTime = System.currentTimeMillis();
	}

	/**
	 * Used for serialization. Do not use.
	 * 
	 * @param map
	 */
	public LoopedRunnable(Map<String, Object> map) {
		this.block = (Block) map.get("block");
		this.player = Bukkit.getOfflinePlayer(UUID.fromString((String) map.get("player")));
		this.endTime = System.currentTimeMillis() + (long) map.get("time");
		this.startTime = System.currentTimeMillis();
		location = block.getBlock().getLocation();
	}

	/**
	 * Called every time the block loops and then schedules another loopedRunnable,
	 * unless the original block is broken.
	 */
	@Override
	public void run() {
		//checks if the block is broken
		if (!block.isBroken()) {
			//if block is not broken restarts the loop and calculaates new endtime and startime.
			block.loopedRun(location, player);
			startTime = endTime;
			endTime += block.getType().getDelay();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"), this,
					block.getType().getDelay());
		}
	}
	/**
	 * 
	 * @return Returns when the most current loop of the runnable will end.
	 */
	public long getEndTime() {
		return endTime;
	}
	/**
	 * 
	 * @return Returns the time left of the most current loop.
	 */
	public long getTimeLeft() {
		return endTime - System.currentTimeMillis();
	}
	/**
	 * 
	 * @return Returns when the most current loop started.
	 */
	public long getStartTime() {
		return startTime;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();

		map.put("time", this.getTimeLeft());
		map.put("block", block);
		map.put("player", player.getUniqueId().toString());
		return map;
	}
	/**
	 * 
	 * @param b : the new block which overrides the previous block.
	 */
	public void setBlock(Block b) {
		this.block = b;
	}
	/**
	 * 
	 * @return Returns the block which is currently being run.
	 */
	public Block getBlock() {
		return block;
	}
}
