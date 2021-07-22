package com.kingmo.utils.blocks;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kingmo.utils.commands.Command;
import com.kingmo.utils.commands.CommandManager;
import com.kingmo.utils.main.Utils;

public class GiveBlockCommand extends Command {

	public GiveBlockCommand() {
		super("giveblock", Utils.toList("gblock", "giveb"), "gives the player a block", "/giveblock [PLAYER] [BLOCK]");

	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(CommandManager.get(this, CommandManager.PLAYER_ONLY));
			return false;
		}

		Player player = Bukkit.getPlayer(args[0]);

		if (player == null) {
			sender.sendMessage(CommandManager.get(this, CommandManager.INCORRECT_USAGE));
			return false;
		}

		String[] strs = { args[1] };

		return this.run(player, strs);
	}

	@Override
	public boolean run(Player player, String[] args) {

		if (args.length == 1) {
			BlockType type = BlockListener.getBlock(args[0].toUpperCase());

			if (type == null) {
				player.sendMessage(CommandManager.get(this, CommandManager.INCORRECT_USAGE));
				return false;
			}

			player.getInventory().addItem(Block.createItemStack(type));
			player.sendMessage(Utils.color("&3&lYou have been given a " + type.getItemName() + "."));
		} else if (args.length >= 2) {

			Player reciever = Bukkit.getPlayer(args[0]);

			BlockType type = BlockListener.getBlock(args[1].toUpperCase());

			if (reciever == null || type == null) {
				player.sendMessage(CommandManager.get(this, CommandManager.INCORRECT_USAGE));
				return false;
			}

			reciever.getInventory().addItem(Block.createItemStack(type));
			reciever.sendMessage(Utils.color("&3&lYou have been given a " + type.getItemName() + "."));

		} else {

			player.sendMessage(CommandManager.get(this, CommandManager.INCORRECT_USAGE));
			return false;
		}

		return true;
	}

}
