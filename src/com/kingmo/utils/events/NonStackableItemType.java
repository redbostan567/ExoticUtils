package com.kingmo.utils.events;

import org.bukkit.Material;

/**
 * @author Kingmo100
 * @apiNote there is really a no use class. 
 * It is just meant to link the two enums, WeaponType and ArmorType. 
 * You can do more with this class, but for now it has no uses
 *
 */
public interface NonStackableItemType {

	
	/**
	 *
	 * @param Material
	 * @return the highest type of this non-stackable item
	 */
	static NonStackableItemType getType(Material m) {
		return null;
	}
	
	Material getType();
	
	boolean equals(Material m);
	
}
