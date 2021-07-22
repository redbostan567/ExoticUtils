package com.kingmo.utils.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kingmo.utils.main.Utils;
import com.kingmo.utils.packet.FancyMessage;
import com.kingmo.utils.packet.PacketManager;

import net.md_5.bungee.api.chat.ClickEvent;

public class BranchCommand extends Command {

	private Map<String, SubCommand> cmds;
	private final String COLOR_CODE_ONE;
	private final String COLOR_CODE_TWO;

	private Map<Integer, List<String>> tabComplete = new HashMap<>();

	public BranchCommand(String name, List<String> aliases, String description, String usage, List<SubCommand> cmds) {
		this(name, aliases, description, usage, "", cmds);
	}

	public BranchCommand(String name, List<String> aliases, String description, String usage, String permission,
			List<SubCommand> cmd) {

		this(name, aliases, description, usage, permission, cmd, Utils.color("&f"), Utils.color("&b&l"));

	}

	public BranchCommand(String name, List<String> aliases, String description, String usage, String permission,
			List<SubCommand> cmd, String colorOne, String colorTwo) {
		super(name, aliases, description, usage, permission);

		this.COLOR_CODE_ONE = colorOne;
		this.COLOR_CODE_TWO = colorTwo;

		this.cmds = new HashMap<>();

		for (SubCommand sub : cmd) {

			cmds.put(sub.getName(), sub);

			sub.setColorOne(colorOne);
			sub.setColorTwo(colorTwo);

			for (String alias : sub.getAliases())
				cmds.put(alias, sub);

		}

		cmds.put("help", new BranchHelpCommand(this));

		tabComplete.put(0, cmds.keySet().stream().collect(Collectors.toList()));

	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {

		if (cmds.containsKey(args[0])) {

			SubCommand cmd = cmds.get(args[0]);

			Map<Integer, List<String>> tab2 = cmd.isConcurrentUpdatingTabComplete() ? cmd.getUpdatedTabComplete()
					: cmd.getTabComplete();
			Map<Integer, List<String>> tabComplete = this.tabComplete;

			for (int i : tab2.keySet()) {
				tabComplete.put(i + 1, tab2.get(i));
			}

		}

		return tabComplete.getOrDefault(args.length - 1, new ArrayList<>());
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location loc) {
		return this.tabComplete(sender, alias, args);
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {

		if (args.length == 0)
			return cmds.get("help").run(sender, args);

		if (!cmds.containsKey(args[0])) {
			sender.sendMessage(CommandManager.ALIAS_NOT_FOUND.replace("[command]", this.getName()));
			return false;
		}
		SubCommand sub = cmds.get(args[0]);
		return sub.run(sender, args);
	}

	@Override
	public boolean run(Player player, String[] args) {

		if (args.length == 0)
			return cmds.get("help").run(player, args);

		if (!cmds.containsKey(args[0])) {
			player.sendMessage(CommandManager.ALIAS_NOT_FOUND.replace("[command]", this.getName()));
			return false;
		}
		SubCommand sub = cmds.get(args[0]);
		return sub.run(player, args);

	}

	public String getCOLOR_CODE_ONE() {
		return COLOR_CODE_ONE;
	}

	public String getCOLOR_CODE_TWO() {
		return COLOR_CODE_TWO;
	}

	public Map<String, SubCommand> getCommands() {
		return cmds;
	}

	protected void addSubCommand(SubCommand cmd) {
		cmd.setColorOne(COLOR_CODE_ONE);
		cmd.setColorTwo(COLOR_CODE_TWO);

		this.cmds.put(cmd.getName(), cmd);

		for(String alias: cmd.getAliases())
			this.cmds.put(alias, cmd);

		tabComplete.put(0, cmds.keySet().stream().collect(Collectors.toList()));
	}

}

class BranchHelpCommand extends SubCommand {

	BranchCommand branchCommand;

	BranchHelpCommand(BranchCommand comm) {
		super("help", new ArrayList<>(), "This command will show options for this entire branch command",
				comm.getUsage() + " help", "", new HashMap<>());

		this.branchCommand = comm;
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {

		sender.sendMessage(branchCommand.getCOLOR_CODE_ONE() + "------------ " + branchCommand.getCOLOR_CODE_TWO()
				+ this.branchCommand.getName() + branchCommand.getCOLOR_CODE_ONE() + " ------------");

		Set<SubCommand> used = new HashSet<>();

		for (SubCommand cmd : branchCommand.getCommands().values()) {
			if(!used.contains(cmd)) {sender.sendMessage(branchCommand.getCOLOR_CODE_TWO() + this.branchCommand.getName() + " " + cmd.getName());
				used.add(cmd);
			}
		}

		sender.sendMessage(branchCommand.getCOLOR_CODE_ONE() + "------------------------------");
		return true;
	}

	@Override
	public boolean run(Player player, String[] args) {

		PacketManager manager = new PacketManager(player);

		FancyMessage top = new FancyMessage(
				branchCommand.getCOLOR_CODE_ONE() + "------------ " + branchCommand.getCOLOR_CODE_TWO()
						+ this.branchCommand.getName() + branchCommand.getCOLOR_CODE_ONE() + " ------------",
				"/" + branchCommand.getName(), ClickEvent.Action.SUGGEST_COMMAND, this.formatHover(branchCommand));

		manager.sendFancyMessage(top);

		Set<SubCommand> used = new HashSet<>();

		for (SubCommand cmd : branchCommand.getCommands().values()) {

			if(used.contains(cmd))continue;

			manager.sendFancyMessage(
					new FancyMessage(
							branchCommand.getCOLOR_CODE_TWO() + "/" + this.branchCommand.getName() + " "
									+ cmd.getName(),
							"/" + this.branchCommand.getName() + " " + cmd.getName(), ClickEvent.Action.SUGGEST_COMMAND,
							this.formatHover(cmd)));
			used.add(cmd);
		}

		player.sendMessage(branchCommand.getCOLOR_CODE_ONE() + "------------------------------");

		return true;
	}

	private String[] formatHover(Command cmd) {
		String[] str = new String[3];

		str[0] = branchCommand.getCOLOR_CODE_ONE() + "Description : " + branchCommand.getCOLOR_CODE_TWO()
				+ cmd.getDescription();
		str[1] = "";
		str[2] = branchCommand.getCOLOR_CODE_ONE() + "Usage : " + branchCommand.getCOLOR_CODE_TWO() + cmd.getUsage();

		return str;
	}

}
