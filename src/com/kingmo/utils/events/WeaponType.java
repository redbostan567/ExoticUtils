package com.kingmo.utils.events;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.nms.NMSManager;

public enum WeaponType implements NonStackableItemType {
	SWORD, PICKAXE, AXE, SHOVEL, HOE;
	
	public static final WeaponType[] ALL = {SWORD, PICKAXE, AXE, SHOVEL, HOE};

	public static NonStackableItemType getType(Material m) {
		
		if(NMSManager.isRenamedItemEnum())
		
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
		else switch(m) {
		
		case DIAMOND_SWORD:
			return SWORD;
		case IRON_SWORD:
			return SWORD;
		case GOLDEN_SWORD:
			return SWORD;
		case STONE_SWORD:
			return SWORD;
		case WOODEN_SWORD:
			return SWORD;
		case DIAMOND_PICKAXE:
			return PICKAXE;
		case IRON_PICKAXE:
			return PICKAXE;
		case GOLDEN_PICKAXE:
			return PICKAXE;
		case STONE_PICKAXE:
			return PICKAXE;
		case WOODEN_PICKAXE:
			return PICKAXE;
		case DIAMOND_SHOVEL:
			return SHOVEL;
		case IRON_SHOVEL:
			return SHOVEL;
		case GOLDEN_SHOVEL:
			return SHOVEL;
		case STONE_SHOVEL:
			return SHOVEL;
		case WOODEN_SHOVEL:
			return SHOVEL;
		case DIAMOND_AXE:
			return AXE;
		case IRON_AXE:
			return AXE;
		case GOLDEN_AXE:
			return AXE;
		case STONE_AXE:
			return AXE;
		case WOODEN_AXE:
			return AXE;
		case DIAMOND_HOE:
			return HOE;
		case IRON_HOE:
			return HOE;
		case GOLDEN_HOE:
			return HOE;
		case STONE_HOE:
			return HOE;
		case WOODEN_HOE:
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
			return !NMSManager.isRenamedItemEnum() ? Material.DIAMOND_SPADE : Material.DIAMOND_SHOVEL;
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
		return getType(type) != null;
	}
	
	public static boolean checkWeapon(ItemStack is) {
		if (isWeapon(is.getType())) {
			return true;
		}
		return false;

	}

	
}
