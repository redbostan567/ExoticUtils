package com.kingmo.utils.events;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum WeaponType implements NonStackableItemType {
	SWORD, PICKAXE, AXE, SHOVEL, HOE;
	
	public static final WeaponType[] ALL = {SWORD, PICKAXE, AXE, SHOVEL, HOE};

	public static NonStackableItemType getType(Material m) {
		switch(m) {
		
		case DIAMOND_SWORD:
			return SWORD;
		case IRON_SWORD:
			return SWORD;
		case GOLD_SWORD:
			return SWORD;
		case STONE_SWORD:
			return SWORD;
		case WOOD_SWORD:
			return SWORD;
		case DIAMOND_PICKAXE:
			return PICKAXE;
		case IRON_PICKAXE:
			return PICKAXE;
		case GOLD_PICKAXE:
			return PICKAXE;
		case STONE_PICKAXE:
			return PICKAXE;
		case WOOD_PICKAXE:
			return PICKAXE;
		case DIAMOND_SPADE:
			return SHOVEL;
		case IRON_SPADE:
			return SHOVEL;
		case GOLD_SPADE:
			return SHOVEL;
		case STONE_SPADE:
			return SHOVEL;
		case WOOD_SPADE:
			return SHOVEL;
		case DIAMOND_AXE:
			return AXE;
		case IRON_AXE:
			return AXE;
		case GOLD_AXE:
			return AXE;
		case STONE_AXE:
			return AXE;
		case WOOD_AXE:
			return AXE;
		case DIAMOND_HOE:
			return HOE;
		case IRON_HOE:
			return HOE;
		case GOLD_HOE:
			return HOE;
		case STONE_HOE:
			return HOE;
		case WOOD_HOE:
			return HOE;
		
		default :
			return null;
		}
	}

	@Override
	public Material getType() {
		switch(this) {
		case SWORD:
			return Material.DIAMOND_SWORD;
		case SHOVEL:
			return Material.DIAMOND_SPADE;
		case AXE:
			return Material.DIAMOND_AXE;
		case HOE:
			return Material.DIAMOND_HOE;
		case PICKAXE:
			return Material.DIAMOND_PICKAXE;
		}
		return null;
	
	}

	@Override
	public boolean equals(Material m) {
		
		return this.equals(WeaponType.getType(m));
	}

	
	public static boolean isWeapon(Material type) {
		switch (type) {
		case DIAMOND_SWORD:
			return true;
		case IRON_SWORD:
			return true;
		case GOLD_SWORD:
			return true;
		case STONE_SWORD:
			return true;
		case WOOD_SWORD:
			return true;
		case DIAMOND_AXE:
			return true;
		case IRON_AXE:
			return true;
		case GOLD_AXE:
			return true;
		case STONE_AXE:
			return true;
		case WOOD_AXE:
			return true;
		case DIAMOND_SPADE:
			return true;
		case IRON_SPADE:
			return true;
		case GOLD_SPADE:
			return true;
		case STONE_SPADE:
			return true;
		case WOOD_SPADE:
			return true;
		case DIAMOND_PICKAXE:
			return true;
		case IRON_PICKAXE:
			return true;
		case GOLD_PICKAXE:
			return true;
		case STONE_PICKAXE:
			return true;
		case WOOD_PICKAXE:
			return true;
		default:
			break;
		}
		return false;
	}
	
	public static boolean checkWeapon(ItemStack is) {
		if (isWeapon(is.getType())) {
			return true;
		}
		return false;

	}

	
}
