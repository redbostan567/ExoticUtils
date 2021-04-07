package com.kingmo.utils.rewards;

public enum RewardType {

	WEIGHTED_REWARD(WeightedRewards.class);
	
	private Class<?> clazz;
	
	RewardType(Class<?> clazz) throws NullPointerException{
	
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
}
