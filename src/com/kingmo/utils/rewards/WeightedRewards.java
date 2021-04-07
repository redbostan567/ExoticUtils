package com.kingmo.utils.rewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.netty.util.internal.ThreadLocalRandom;

public class WeightedRewards implements Rewards{

	private final Map<Reward, Double> rewards;
	private final double totalWeight;
	private final List<Reward> weightedList;
	
	public WeightedRewards(Map<Reward, Double> rewards) {
		this.rewards = rewards;
		double totalWeight = 0;
		List<Reward> weightedList = new ArrayList<>();
		
		for(Reward r: rewards.keySet()) {
			totalWeight+=rewards.get(r);
			for(int i = 0; i < rewards.get(r); i++) weightedList.add(r);
		}
		
		this.totalWeight = totalWeight;
		this.weightedList = weightedList;
	}
	
	
	@Override
	public ItemStack[] getReward(Player player) {
		int random = ThreadLocalRandom.current().nextInt((int) totalWeight);
		Reward reward = weightedList.get(random);
		
		return reward.reward(player);
	}

	@Override
	public Map<Reward, Double> getRewardMap() {
		return rewards;
	}
	
	
}
