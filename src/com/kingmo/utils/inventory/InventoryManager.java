package com.kingmo.utils.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager{

	private final Map<Integer, Map<String, InventorySerializable>> inventories;
	private final Map<Integer, Map<ItemStack, InventorySerializable>> clickThroughMap;
	private int currentLevel = 0;
	private InventorySerializable mainInv;
	private Player player;
	private static List<Inventory> noClickList = new ArrayList<>();

	public InventoryManager(Player player) {
		this(Bukkit.createInventory(null, 9*6), player);
	}

	public InventoryManager(Inventory inv, Player player) {
		inventories = new HashMap<>();
		clickThroughMap = new HashMap<>();

		mainInv = new InventorySerializable(inv);

		this.player = player;

	}

	public void addInvententoryToLevel(int level, Inventory i) {

		Map<String, InventorySerializable> list = inventories.getOrDefault(i, new HashMap<>());

		InventorySerializable inv = new InventorySerializable(i);

		list.put(inv.getName(), inv);

		inventories.put(level, list);
	}

	public void addInvententoryToLevel(int level, Inventory i, ItemStack is) {

		Map<String, InventorySerializable> list = inventories.getOrDefault(i, new HashMap<>());

		InventorySerializable inv = new InventorySerializable(i);

		list.put(inv.getName(), inv);

		if (level - 1 >= 0) {

			Map<ItemStack, InventorySerializable> itemToInv = clickThroughMap.getOrDefault(level - 1, new HashMap<>());

			itemToInv.put(is, inv);

			clickThroughMap.put(level - 1, itemToInv);
		}

		inventories.put(level, list);
	}

	public List<Inventory> getInventoriesAtLevel(int level) {

		List<Inventory> finalList = new ArrayList<>();

		for (String inv : inventories.get(level).keySet())
			finalList.add(inventories.get(level).get(inv).createInventory());

		return finalList;
	}

	public Inventory getFirstInventory() {
		return mainInv.createInventory();
	}

	public Inventory openFirstInventory() {
		player.openInventory(mainInv.createInventory());
		return mainInv.createInventory();
	}

	public Inventory openInventoryByItemStack(ItemStack is) {
		Inventory in = this.clickThroughMap.get(currentLevel).get(is).createInventory();
		player.openInventory(in);
		currentLevel++;
		return in;
	}

	public static void addNoClick(Inventory inv) {
		InventoryManager.noClickList.add(inv);
	}

	public static boolean isClickable(Inventory inv) {
		return !InventoryManager.noClickList.contains(inv);
	}



}
