package com.kingmo.utils.glow;


import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.nms.NMSManager;


public class Glow extends Enchantment {

	public Glow() {
		super((NamespacedKey) NMSManager.getGlowEnchantID());
	}

	
	public boolean canEnchantItem(ItemStack arg0) {
		return false;
	}

	
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	
	public int getMaxLevel() {
		return 0;
	}

	
	public String getName() {
		return "Glow";
	}

	
	public int getStartLevel() {
		return 0;
	}


	
	public boolean isCursed() {
		return false;
	}


	
	public boolean isTreasure() {
		return false;
	}
	



}
