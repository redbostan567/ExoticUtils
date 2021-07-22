package com.kingmo.utils.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabManager {

	private Map<Integer, List<String>> tabComplete = new HashMap<>();
	private int currentArg;

	public TabManager(int currentArgument) {
		this.currentArg = currentArgument - 1;
	}

	public TabManager addArgumentsToCurrentArg(List<String> addOn) {
		List<String> current = tabComplete.getOrDefault(currentArg, new ArrayList<>());
		current.addAll(addOn);
		tabComplete.put(currentArg, current);
		return this;
	}

	public TabManager goFoward() {
		currentArg++;
		return this;
	}

	public TabManager goBack() {
		currentArg--;
		return this;
	}

	public TabManager addArgAndGoFoward(List<String> add) {
		this.addArgumentsToCurrentArg(add);
		this.goFoward();
		return this;
	}

	public Map<Integer, List<String>> getTabComplete() {
		return tabComplete;
	}

	public static <I> List<String> listToString(Iterable<I> to, Stringable str) {

		List<String> newList = new ArrayList<>();

		for (I i : to)
			newList.add(str.makeString(i));

		return newList;
	}

	public static <I> List<String> listToString(Iterable<I> to) {

		return TabManager.listToString(to, (obj) -> {
			return obj.toString();
		});

	}

}
