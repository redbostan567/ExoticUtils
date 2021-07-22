package com.kingmo.utils.rewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.kingmo.utils.config.ConfigManager;
import com.kingmo.utils.item.ItemBuilder;

public class RewardManager {

	private final String path;
	private final Plugin plugin;
	private final RewardType type;
	private final Rewards rewards;

	/**
	 *
	 *
	 * @param path
	 * @param plugin
	 * @param type
	 * @throws NullPointerException
	 */
	public RewardManager(String path, Plugin plugin, RewardType type) throws NullPointerException {
		this.type = type;
		this.plugin = plugin;
		this.path = path;

		Map<Reward, Double> rewardMap = new HashMap<>();

		FileConfiguration config = plugin.getConfig();

		if (!plugin.getConfig().contains(path))
			throw new NullPointerException("No " + path + " found");
		for (String keys : plugin.getConfig().getConfigurationSection(path).getKeys(false)) {
			String absolutePath = path + "." + keys + ".";
			Reward rw = null;
			double weight = config.getDouble(absolutePath + "weight");

			if (config.contains(absolutePath + "item")) {
				rw = new ItemReward(new ItemBuilder(absolutePath + "item", config).getItem());
			}

			if (config.contains(absolutePath + "commands")) {
				List<String> commands = config.getStringList(absolutePath + "commands");
				rw = rw == null ? new CommandReward(commands, true)
						: new CompoundReward(rw, new CommandReward(commands, true));
			}

			if (rw != null)
				rewardMap.put(rw, weight);
			else
				throw new NullPointerException("reward at " + absolutePath + " is not built correctly");
		}

		switch (type) {

		case WEIGHTED_REWARD:
			rewards = new WeightedRewards(rewardMap);
			break;
		default:
			rewards = null;
			break;
		}

	}

	public RewardManager(String path, ConfigManager configManager, RewardType type) {
		this(path, configManager.getPlugin(), type);
	}

	@SuppressWarnings("unused")
	@Deprecated
	private Map<String, Object> buildMap(FileConfiguration config, String path) {
		Material material = Material.AIR;
		String displayName = "";
		List<String> lore = new ArrayList<>();
		boolean glowing = false;
		if (config.contains(path + "mat"))
			material = Material.valueOf((String) config.get(path + "mat"));
		if (config.contains(path + "name"))
			displayName = (String) config.get(path + "name");
		if (config.contains(path + "lore"))
			lore = config.getStringList(path + "lore");
		if (config.contains(path + "glowing"))
			glowing = config.getBoolean(path + "glowing");

		Map<String, Object> map = new HashMap<>();
		map.put("mat", material);
		map.put("name", displayName);
		map.put("lore", lore);
		map.put("glowing", glowing);

		return map;
	}

	public void givePlayerReward(Player p) {
		if (p == null)
			return;
		ItemStack[] is = rewards.getReward(p);
		if (is != null && is.length != 0)
			p.getInventory().addItem(is);
	}

	public String getPath() {
		return path;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public RewardType getType() {
		return type;
	}

	public Rewards getRewards() {
		return rewards;
	}

}
