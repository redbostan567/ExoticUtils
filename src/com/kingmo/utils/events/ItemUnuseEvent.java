package com.kingmo.utils.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ItemUnuseEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
	private ItemStack is;
	private WeaponEquipMethod em;
	private WeaponType t;
	private boolean cancel = false;
	private Player p;

	public ItemUnuseEvent(Player p, ItemStack is, WeaponEquipMethod em, WeaponType wt) {
		this.p = p;
		this.is = is;
		this.em = em;
		this.t = wt;
	}
	
	  public static final HandlerList getHandlerList() {
		  return handlers;
		  }

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancel = arg0;
		
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public ItemStack getWeapon() {
		return is;
	}

	public void setWeapon(ItemStack is) {
		this.is = is;
	}


	public WeaponEquipMethod getEquipMethod() {
		return em;
	}

	public void setEquipMethod(WeaponEquipMethod em) {
		this.em = em;
	}

	public WeaponType getType() {
		return t;
	}

	public void setType(WeaponType t) {
		this.t = t;
	}


	public void setPlayer(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return p;
	}
}
