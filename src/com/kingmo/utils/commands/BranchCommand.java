package com.kingmo.utils.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	public BranchCommand(String name, List<String> aliases, String description, String usage, List<SubCommand> cmds) {
		this(name, aliases, description, usage, "", new HashMap<>(), cmds);
	}

	public BranchCommand(String name, List<String> aliases, String description, String usage, String permission,
			Map<Integer, List<String>> tabComplete, List<SubCommand> cmd) {

		this(name, aliases, description, usage, permission, tabComplete, cmd, Utils.color("&f"), Utils.color("&b&l"));

	}

	public BranchCommand(String name, List<String> aliases, String description, String usage, String permission,
			Map<Integer, List<String>> tabComplete, List<SubCommand> cmd, String colorOne, String colorTwo) {
		super(name, aliases, description, usage, permission, tabComplete);

		this.cmds = new HashMap<>();

		for (SubCommand sub : cmd) {

			cmds.put(sub.getName(), sub);

			for (String alias : sub.getAliases())
				cmds.put(alias, sub);

		}
		
		this.COLOR_CODE_ONE = colorOne;
		this.COLOR_CODE_TWO = colorTwo;
		
		cmds.put("help", new BranchHelpCommand(this));
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {
		
		if(args.length == 0) 
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
		
		if(args.length == 0) 
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
		this.cmds.put(cmd.getName(), cmd);
	}

}

class BranchHelpCommand extends SubCommand {

	BranchCommand branchCommand;

	BranchHelpCommand(BranchCommand comm) {
		super("help", new ArrayList<>(),
				"This command will show options for this entire branch command",
				comm.getUsage() + " help", "", new HashMap<>());

		this.branchCommand = comm;
	}

	@Override
	public boolean run(CommandSender sender, String[] args) {

		sender.sendMessage(branchCommand.getCOLOR_CODE_ONE() + "------------ " + branchCommand.getCOLOR_CODE_TWO()
				+ this.branchCommand.getName() + branchCommand.getCOLOR_CODE_ONE() + " ------------");
		
		
		for(SubCommand cmd: branchCommand.getCommands().values()) 
			sender.sendMessage(branchCommand.getCOLOR_CODE_TWO() + this.branchCommand.getName() + " " + cmd.getName());
		
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


		for (SubCommand cmd : branchCommand.getCommands().values()) {

			manager.sendFancyMessage(
					new FancyMessage(branchCommand.getCOLOR_CODE_TWO() + "/" + this.branchCommand.getName() +  " " + cmd.getName(),
							"/" + this.branchCommand.getName() + " " + cmd.getName(), ClickEvent.Action.SUGGEST_COMMAND,
							this.formatHover(cmd)));

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
