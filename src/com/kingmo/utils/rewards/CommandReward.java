package com.kingmo.utils.rewards;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandReward implements Reward{

	private final List<String> command;
	private final boolean runByConsole;

	public CommandReward(List<String> command, boolean runByConsole) {
		this.command = command;
		this.runByConsole = runByConsole;
	}

	@Override
	public ItemStack[] reward(Player player) {
		if(runByConsole)for(String cmd: command)Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
		else for(String cmd: command)Bukkit.dispatchCommand(player, cmd.replace("%player%", player.getName()));
		return null;
	}



}
