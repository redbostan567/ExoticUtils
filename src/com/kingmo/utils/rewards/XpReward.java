package com.kingmo.utils.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.xp.ExpManager;

public class XpReward implements Reward{

	private int xp;

	public XpReward(int xp) {
		this.xp = xp;
	}

	@Override
	public ItemStack[] reward(Player player) {
		ExpManager manager = new ExpManager(player);
		manager.addXp(xp);
		return null;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

}
