package com.kingmo.utils.rewards;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Rewards {

	
	public ItemStack[] getReward(Player player);
	
	public Map<Reward, Double> getRewardMap();
	
}
