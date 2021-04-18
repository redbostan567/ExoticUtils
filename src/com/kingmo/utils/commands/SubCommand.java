package com.kingmo.utils.commands;

import java.util.List;
import java.util.Map;

public abstract class SubCommand extends Command{

	public SubCommand(String name, List<String> aliases, String description, String usage, String permission,
			Map<Integer, List<String>> tabComplete) {
		super(name, aliases, description, usage, permission, tabComplete);
	}	
	
}
