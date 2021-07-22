package com.kingmo.utils.commands;

import java.util.List;
import java.util.Map;

public abstract class SubCommand extends Command {

	private Map<Integer, List<String>> tabComplete;
	private String colorOne;
	private String colorTwo;
	private boolean concurrentUpdatingTabComplete;
	
	
	public SubCommand(String name, List<String> aliases, String description, String usage, String permission,
			Map<Integer, List<String>> tabComplete) {
		super(name, aliases, description, usage, permission, tabComplete);
		this.tabComplete = tabComplete;
	}
	
	public SubCommand(String name, List<String> aliases, String description, String usage, String permission,
			boolean concurrentUpdatingTabComplete, Map<Integer, List<String>> tabComplete) {
		super(name, aliases, description, usage, permission, tabComplete);
		this.tabComplete = tabComplete;
	}
	
	public Map<Integer, List<String>> getUpdatedTabComplete(){
		return tabComplete;
	}

	public Map<Integer, List<String>> getTabComplete() {
		return tabComplete;
	}
	
	public String getColorOne() {
		return colorOne;
	}

	public void setColorOne(String colorOne) {
		this.colorOne = colorOne;
	}

	public String getColorTwo() {
		return colorTwo;
	}

	public void setColorTwo(String colorTwo) {
		this.colorTwo = colorTwo;
	}

	public boolean isConcurrentUpdatingTabComplete() {
		return concurrentUpdatingTabComplete;
	}

	protected void setConcurrentUpdatingTabComplete(boolean concurrentUpdatingTabComplete) {
		this.concurrentUpdatingTabComplete = concurrentUpdatingTabComplete;
	}

	
	
}
