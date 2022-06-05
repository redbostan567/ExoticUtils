package com.kingmo.utils.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipEvent extends PlayerEvent
implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	private ItemStack oldA;
	private ItemStack newA;
	private EquipMethod em;
	private ArmorType t;
	private boolean cancel = false;
	@SuppressWarnings("unused")
	private Player p;
	public ArmorEquipEvent(Player p) {
		super(p);
		em = EquipMethod.LOG_IN;
		t = ArmorType.CHESTPLATE;
		newA = new ItemStack(Material.AIR);
		oldA = new ItemStack(Material.AIR);
	}
	
	public ArmorEquipEvent(Player p, EquipMethod em) {
		super(p);
		this.em = em;
		t = ArmorType.CHESTPLATE;
		newA = new ItemStack(Material.AIR);
		oldA = new ItemStack(Material.AIR);
		
	}
	
	public ArmorEquipEvent(Player p, EquipMethod em, ArmorType t, ItemStack oldA, ItemStack newA) {
		super(p);
		this.p = p;
		this.em = em;
		this.newA = newA;
		this.oldA = oldA;
		this.t = t;
		
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
	public ItemStack getOldArmor() {
		return oldA;
	}

	public void setOldArmor(ItemStack oldA) {
		this.oldA = oldA;
	}

	public ItemStack getNewArmor() {
		return newA;
	}

	public void setNewArmor(ItemStack newA) {
		this.newA = newA;
	}

	public EquipMethod getEquipMethod() {
		return em;
	}

	public void setEquipMethod(EquipMethod em) {
		this.em = em;
	}

	public ArmorType getType() {
		return t;
	}

	public void setType(ArmorType t) {
		this.t = t;
	}


	public void setPlayer(Player p) {
		this.p = p;
	}

}
