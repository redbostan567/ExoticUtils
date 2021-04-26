package com.kingmo.utils.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public abstract class Command extends BukkitCommand {

	private Map<Integer, List<String>> tabComplete;

	public Command(String name, List<String> aliases, String description, String usage) {
		this(name, aliases, description, usage, "", new HashMap<>());
	}

	public Command(String name, List<String> aliases, String description, String usage, String permission) {
		this(name, aliases, description, usage, permission, new HashMap<>());
	}

	public Command(String name, List<String> aliases, String description, String usage, String permission,
			Map<Integer, List<String>> tabComplete) {
		super(name);
		this.setAliases(aliases);
		this.setDescription(description);
		this.setUsage(usage);
		this.setPermission(permission);
		this.setPermissionMessage(CommandManager.PERMISSION_DENIED);
		this.tabComplete = tabComplete;
	}

	public boolean run(Player player, String[] args) {
		return run((CommandSender) player, args);
	}

	public abstract boolean run(CommandSender sender, String[] args);

	@Override
	public boolean execute(CommandSender sender, String alias, String[] args) {

		if (sender instanceof Player)
			return this.run((Player) sender, args);
		else
			return run(sender, args);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		return this.testPermission(sender) ? tabComplete.get(args.length - 1) : new ArrayList<>();
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location loc) {
		return this.testPermission(sender) ? tabComplete.get(args.length - 1) : new ArrayList<>();
	}
}
