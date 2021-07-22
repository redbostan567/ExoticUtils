package com.kingmo.utils.glow;


import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.main.ExoticUtilityMain;


public class GlowNameSpaced extends Enchantment {

	public GlowNameSpaced() {
		super(new NamespacedKey(ExoticUtilityMain.getInstance(), "exotic_glow"));
	}


	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return false;
	}


	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}


	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}


	@Override
	public int getMaxLevel() {
		return 0;
	}


	@Override
	public String getName() {
		return "Glow";
	}


	@Override
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
