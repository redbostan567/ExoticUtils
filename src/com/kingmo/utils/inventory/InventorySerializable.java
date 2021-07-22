package com.kingmo.utils.inventory;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventorySerializable implements ConfigurationSerializable {

	private final ItemStack[] items;
	private final int size;
	private final String name;
	private boolean clickable = false;

	public InventorySerializable(Inventory i) {
		items = i.getContents();
		size = i.getSize();

		if (i instanceof InventoryView) {
			name = ((InventoryView) i).getTitle();
		} else
			name = i.getType().getDefaultTitle();

	}

	public InventorySerializable(Map<String, Object> map) {
		items = (ItemStack[]) map.get("items");
		size = (int) map.get("size");
		name = (String) map.get("name");
	}

	@Override
	public Map<String, Object> serialize() {

		Map<String, Object> map = new HashMap<>();

		map.put("items", items);
		map.put("size", size);
		map.put("name", name);

		return map;
	}

	public Inventory createInventory() {
		return createInventory(name);
	}

	public Inventory createInventory(String title) {

		Inventory i = Bukkit.createInventory(null, size, title);
		i.setContents(items);
		if (!clickable)
			InventoryManager.addNoClick(i);
		return i;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public int getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
}
