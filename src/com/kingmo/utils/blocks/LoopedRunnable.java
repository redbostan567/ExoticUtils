package com.kingmo.utils.blocks;

import org.bukkit.Bukkit;

public class LoopedRunnable implements Runnable {

	private final Block block;

	public LoopedRunnable(Block block) {
		this.block = block;
	}

	@Override
	public void run() {
		block.loopedRun();
		if (!block.isBroken())
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ExoticUtils"), this,
					block.getType().getDelay());
	}

}
