package com.kingmo.utils.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements Reward{
	
	private ItemStack is;
	
	public ItemReward(ItemStack is) {
		this.is = is;
	}
	
	@Override
	public ItemStack[] reward(Player player) {
	
		ItemStack[] iss = {is};
		return iss;
		
	}

}
