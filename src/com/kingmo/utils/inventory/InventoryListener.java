package com.kingmo.utils.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if (!InventoryManager.isClickable(e.getClickedInventory()))
			e.setCancelled(true);
		else if (!InventoryManager.isClickable(e.getWhoClicked().getOpenInventory().getTopInventory()))
			e.setCancelled(true);
	}

}
