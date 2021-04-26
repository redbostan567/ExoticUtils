package com.kingmo.utils.commands;

import java.util.List;
import java.util.Map;

public abstract class SubCommand extends Command {

	private Map<Integer, List<String>> tabComplete;

	public SubCommand(String name, List<String> aliases, String description, String usage, String permission,
			Map<Integer, List<String>> tabComplete) {
		super(name, aliases, description, usage, permission, tabComplete);
		this.tabComplete = tabComplete;
	}

	public Map<Integer, List<String>> getTabComplete() {
		return tabComplete;
	}

}
