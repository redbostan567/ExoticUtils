package com.kingmo.utils.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import com.kingmo.utils.config.ConfigManager;
import com.kingmo.utils.main.ExoticUtilityMain;
import com.kingmo.utils.main.Utils;

public class CommandManager {

	public static final ConfigManager config = new ConfigManager(ExoticUtilityMain.getInstance());

	public static final String PERMISSION_DENIED = config.getStringOrDefault("permission-deny-message", Utils.color(
			"&4&lYou are not allowed to perform this command. If you think this is an error please contact the server administration."));

	public static final String ALIAS_NOT_FOUND = config.getStringOrDefault("alias-not-found", Utils.color(
			"&4&lThis alias of command:[command] was not found. Use /[command] help to look at available aliases. If you think this an error please contact the server administration"));

	public static final String PLAYER_ONLY = config.getStringOrDefault("player-only-message", Utils.color(
			"&4&lThis command is only for in-game players and thus you cannot use it. Please go in-game and try again."));

	public static final String INCORRECT_USAGE = config.getStringOrDefault("incorrect-usage-message", Utils.color(
			"&4&lYou have made an error when typing this command. Please use /[command] help in order to find available aliases and their uses."));

	private static SimpleCommandMap COMMAND_REGISTER;

	private final List<Command> commands = new ArrayList<>();

	public CommandManager(Plugin plugin) {
		String version = plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		String name = "org.bukkit.craftbukkit." + version + ".CraftServer";
		Class<?> craftserver = null;
		try {
			craftserver = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (craftserver != null) {
				COMMAND_REGISTER = (SimpleCommandMap) craftserver.cast(plugin.getServer()).getClass()
						.getMethod("getCommandMap", new Class[0]).invoke(plugin.getServer(), new Object[0]);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

	public void registerCommand(Command... commands) {
		for (Command command : commands) {
			registerCommand(command);
		}
	}

	public void registerCommand(final Command command) {
		commands.add(command);

		COMMAND_REGISTER.register("", command);
	}

	public List<String> findClosest(Collection<String> list, String startsWith) {
		List<String> closest = new ArrayList<>();
		if ((startsWith == null) || (startsWith.isEmpty())) {
			closest.addAll(list);
		} else {
			for (String string : list) {
				if (string.toLowerCase().startsWith(startsWith.toLowerCase())) {
					closest.add(string);
				}
			}
		}
		return closest;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public static String get(Command cmd, String string) {
		return string.replace("[command]", cmd.getName());
	}

}
