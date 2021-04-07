package com.kingmo.utils.rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompoundReward implements Reward{

	private final Reward[] rewards;
	
	public CompoundReward(Reward... rewards) {
		this.rewards = rewards;
	}
	
	@Override
	public ItemStack[] reward(Player player) {
		List<ItemStack> lis = new ArrayList<>();
		
		for(Reward r: rewards) {
			ItemStack[] is = r.reward(player);
			if(is != null) 
				for(ItemStack t: is)lis.add(t);
		}
		
		return lis.toArray(new ItemStack[lis.size()]);
	}
	
	public static CompoundReward combine(Reward... rw0) {
		return new CompoundReward(rw0);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Reward r: rewards) {
			builder.append(r.toString());
		}
		return builder.toString();
	}

}
