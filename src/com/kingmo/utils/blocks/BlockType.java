package com.kingmo.utils.blocks;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.kingmo.utils.nbt.NBTTagManager;

public interface BlockType extends ConfigurationSerializable {
	/**
	 * @return The Name put in maps for the block. Relatively unused
	 */
	public String getName();
	/**
	 *
	 * @return The Item Name for the Block's ItemStack
	 */
	public String getItemName();

	/**
	 *
	 * @return The Lore for the Block's ItemStack
	 */
	public List<String> getLore();

	/**
	 *
	 * @return Whether or not the ItemStack will be glowing
	 */
	public boolean isGlowing();

	/**
	 *
	 * @return The NBTTags for the ItemStack
	 */
	public Map<String, Object> getTags();

	/**
	 *
	 * @return The Material for the Block's ItemStack
	 */
	public Material getMaterial();
	/**
	 *
	 * @return the Block Class that defines all methods for the BlockType
	 */
	public Class<? extends Block> getBlockClass();
	/**
	 * Serializes the BlockTypes make sure all data is held in the block.
	 */
	@Override
	public Map<String, Object> serialize();

	/**
	 * @return Returns the ID. This will be used later when I develop a client.
	 */
	public String getID();

	public int getDelay();

	public boolean showName();

	public default Block getBlockFromType(Location loc) throws ClassNotFoundException {
		Block block = null;
		try {
			block = this.getBlockClass().getDeclaredConstructor(Location.class, BlockType.class).newInstance(loc,
					this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			throw new ClassNotFoundException("Block class for BlockType " + this.toString() + " was not found.");
		}

		return block;
	}

	public default Map<String, Object> defSerialize(){
		Map<String, Object> map = new HashMap<>();

		map.put("class", this.getBlockClass());
		map.put("id", this.getID());
		map.put("name", this.getName());
		map.put("itemName", this.getItemName());
		map.put("lore", this.getLore());
		map.put("mat", this.getMaterial().toString());
		map.put("glow", this.isGlowing());
		map.put("tags", NBTTagManager.castTo(this.getTags()));
		map.put("delay", this.getDelay());
		map.put("show", this.showName());

		return map;
	}

}
