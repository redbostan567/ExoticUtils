package com.kingmo.utils.blocks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.item.ItemBuilder;
import com.kingmo.utils.nbt.NBTString;
import com.kingmo.utils.nbt.NBTTagManager;

/**
 * @author Kingmo100
 * @since V1.0
 * @see BlockListener
 */
public abstract class Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The block currently being modified by the "Block" class
	 */
	private org.bukkit.block.Block block;
	/**
	 * The type of block to be defined by users of API
	 */
	private BlockType type;
	
	/**
	 * @param loc  Location of the custom block
	 * @param type Type of the custom block
	 */
	public Block(Location loc, BlockType type) {
		block = loc.getBlock();
		block.setType(type.getMaterial());
		this.type = type;
	}
	
	/**
	 * @usage Dont use. This is just for serialization
	 * @param ser
	 */

	public Block(Map<String, Object> ser) {
		String world = (String) ser.get("world");
		int x = (int) ser.get("x");
		int y = (int) ser.get("y");
		int z = (int) ser.get("z");
		BlockType type = (BlockType) ser.get("block");

		Location loc = new Location(Bukkit.getWorld(world), x, y, z);
		block = loc.getBlock();
		this.type = type;
		
	}

	
	/**
	 * @apiNote called when block is initially placed.
	 * @see #loopedRun()
	 */
	public abstract void run();

	
	/**
	 * @apiNote only use if {@link BlockType#getDelay()} != null || 0
	 */
	public void loopedRun() {
		
	}
	
	
	/**
	 * @apiNote Do not use, needed only for serialization. 
	 * 
	 * @return serialized map of all objects in the code
	 */
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();

		Location loc = block.getLocation();

		map.put("world", loc.getWorld().getName());
		map.put("x", loc.getX());
		map.put("y", loc.getY());
		map.put("z", loc.getZ());
		map.put("block", type);

		return map;
	}

	/**
	 * Gives the bukkit block this custom block refers to.
	 * @return the bukkit block at the location the custom block is at
	 */
	public org.bukkit.block.Block getBlock() {
		return block;
	}

	
	/**
	 * @param block : sets a new bukkit block. Can be used to change location.
	 * @apiNote only use in the creation as it will mess up the hashmap. 
	 */
	protected void setBlock(org.bukkit.block.Block block) {
		this.block = block;
	}

	/**
	 * Gives the type of block this custom block refers to.
	 * @return the type of block.
	 */
	public BlockType getType() {
		return type;
	}

	/**
	 * 
	 * @param type : the new type of block.
	 * @apiNote Do not use if not in an abstraction.
	 */
	protected void setType(BlockType type) {
		this.type = type;
	}
	/**
	 * @apiNote defined in the abstract class if something special wants to be done for the block. 
	 * @param breaker : the player who broke the block 
	 */
	public void onBreak(Player breaker) {
	}
	
	/** 
	 * @apiNote defined in the abstract class if something special wants to be done for the block place.
	 * @param place : the player who places the block.
	 */
	public void onPlace(Player place) {
	}

	
	/**
	 * 
	 * @param is : the item stack trying to be placed.
	 * @return returns true if the item stack is the item stack contains the NBT tag block ID.
	 */
	public boolean isBlock(ItemStack is) {
		NBTTagManager manager = new NBTTagManager(is);
		return manager.containsTag("block-id") ? manager.getStringTag("block-id").equals(this.getType().getID()) : false;
	}
	
	/**
	 * 
	 * @param t : The type of block's ItemStack being created.
	 * @return the ItemStack of the given BlockType 
	 */

	public static ItemStack createItemStack(BlockType t) {
		return new ItemBuilder(t.getItemName(), t.getMaterial(), t.getLore()).setGlow(t.isGlowing())
				.addNBTTag(t.getTags()).addNBTTag("block-id", new NBTString(t.getID())).getItem();
	}

}
