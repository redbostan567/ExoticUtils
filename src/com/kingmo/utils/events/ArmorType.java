package com.kingmo.utils.events;

import org.bukkit.Material;

public enum ArmorType implements NonStackableItemType{

	HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	
	public static final ArmorType[] ALL = {HELMET, CHESTPLATE, LEGGINGS, BOOTS};
	
	public static int getDiamondArmorValue(ArmorType t) {
		switch(t) {
		case HELMET :
			return 3;
		case CHESTPLATE :
			return 8;
		case LEGGINGS :
			return 6;
		case BOOTS :
			return 3;
		default :
			return 0;
		}
	}

	public static String toString(ArmorType t) {
		switch(t) {
		case HELMET :
			return "helmet";
		case CHESTPLATE :
			return "chestplate";
		case LEGGINGS :
			return "leggings";
		case BOOTS :
			return "boots";
		default :
			return "does not exist";
		}
		
	}
	
	public static ArmorType value(String s) {
		s.toUpperCase();
		switch (s) {
		case "HELMET":
			return ArmorType.HELMET;
		case "CHESTPLATE":
			return ArmorType.CHESTPLATE;
		case "LEGGINGS":
			return ArmorType.LEGGINGS;
		case "BOOTS":
			return ArmorType.BOOTS;
		default :
			return ArmorType.CHESTPLATE;
		}
		
	}

	public static int getSlotNumber(ArmorType t) {
		
		switch(t) {
		case HELMET:
			return 3;
		case CHESTPLATE:
			return 2;
		case LEGGINGS:
			return 1;
		case BOOTS:
			return 0;
		default:
			break;
		
		}
		
		return 0;
	}

	public static NonStackableItemType getType(Material m) {
		switch(m) {
		case DIAMOND_HELMET:
			return HELMET;
		case IRON_HELMET:
			return HELMET;
		case GOLD_HELMET:
			return HELMET;
		case CHAINMAIL_HELMET:
			return HELMET;
		case LEATHER_HELMET:
			return HELMET;
		case DIAMOND_CHESTPLATE:
			return CHESTPLATE;
		case IRON_CHESTPLATE:
			return CHESTPLATE;
		case GOLD_CHESTPLATE:
			return CHESTPLATE;
		case CHAINMAIL_CHESTPLATE:
			return CHESTPLATE;
		case LEATHER_CHESTPLATE:
			return CHESTPLATE;
		case DIAMOND_LEGGINGS:
			return LEGGINGS;
		case IRON_LEGGINGS:
			return LEGGINGS;
		case GOLD_LEGGINGS:
			return LEGGINGS;
		case CHAINMAIL_LEGGINGS:
			return LEGGINGS;
		case LEATHER_LEGGINGS:
			return LEGGINGS;
		case DIAMOND_BOOTS:
			return BOOTS;
		case IRON_BOOTS:
			return BOOTS;
		case GOLD_BOOTS:
			return BOOTS;
		case CHAINMAIL_BOOTS:
			return BOOTS;
		case LEATHER_BOOTS:
			return BOOTS;
		default :
			return null;
		
		}

	}

	@Override
	public Material getType() {
		switch(this) {
		case HELMET:
			return Material.DIAMOND_HELMET;
		case CHESTPLATE:
			return Material.DIAMOND_CHESTPLATE;
		case LEGGINGS:
			return Material.DIAMOND_LEGGINGS;
		case BOOTS:
			return Material.DIAMOND_BOOTS;
		}
		return null;
	}

	@Override
	public boolean equals(Material m) {
		// TODO Auto-generated method stub
		return this.equals(ArmorType.getType(m));
	}
	
	public static boolean isArmor(Material type) {
		switch (type) {

		case DIAMOND_HELMET:
			return true;
		case DIAMOND_CHESTPLATE:
			return true;
		case DIAMOND_LEGGINGS:
			return true;
		case DIAMOND_BOOTS:
			return true;
		case IRON_HELMET:
			return true;
		case IRON_CHESTPLATE:
			return true;
		case IRON_LEGGINGS:
			return true;
		case IRON_BOOTS:
			return true;
		case CHAINMAIL_HELMET:
			return true;
		case CHAINMAIL_CHESTPLATE:
			return true;
		case CHAINMAIL_LEGGINGS:
			return true;
		case CHAINMAIL_BOOTS:
			return true;
		case GOLD_HELMET:
			return true;
		case GOLD_CHESTPLATE:
			return true;
		case GOLD_LEGGINGS:
			return true;
		case GOLD_BOOTS:
			return true;
		case LEATHER_HELMET:
			return true;
		case LEATHER_CHESTPLATE:
			return true;
		case LEATHER_LEGGINGS:
			return true;
		case LEATHER_BOOTS:
			return true;
		default:
			return false;
		}

	}
	
}