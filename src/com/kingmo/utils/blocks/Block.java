package com.kingmo.utils.blocks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.item.ItemBuilder;
import com.kingmo.utils.main.Utils;
import com.kingmo.utils.nbt.NBTString;
import com.kingmo.utils.nbt.NBTTagManager;

/**
 * @author Kingmo100
 * @since V1.0
 * @see BlockListener
 */
public abstract class Block implements ConfigurationSerializable {

	/**
	 * The type of block to be defined by users of API
	 */
	private BlockType type;

	private Location loc;

	private ArmorStand stand;

	/**
	 * Dictates whether or not the block exists in the world.
	 */
	private boolean broken = false;

	/**
	 * @param loc  Location of the custom block
	 * @param type Type of the custom block
	 */
	public Block(Location loc, BlockType type) {
		org.bukkit.block.Block block = loc.getBlock();
		block.setType(type.getMaterial());
		this.type = type;

		this.loc = loc;

		if (type.showName())
			BlockListener.addArmorStand(this);

	}

	/**
	 * @usage Dont use. This is just for serialization
	 * @param ser
	 */

	public Block(Map<String, Object> ser) {
		Location loc = (Location) ser.get("loc");
		BlockType type = (BlockType) ser.get("block");

		this.loc = loc;
		this.type = type;
		this.deserialize(ser);

	}

	/**
	 * @apiNote called when block is initially placed.
	 * @see #loopedRun()
	 */
	public abstract void run(Location location, OfflinePlayer player);

	/**
	 * @apiNote only use if {@link BlockType#getDelay()} != null || 0
	 */
	public void loopedRun(Location location, OfflinePlayer player) {
		if (player.isOnline())
			((Player) player).sendMessage(Utils.color("&b&lYour custom block has been activated again"));
		this.run(location, player);
	}

	/**
	 * Use for any additional serialized values. Generally unrecommended.
	 */
	public Map<String, Object> serialize(Map<String, Object> map) {
		return map;
	}

	public void deserialize(Map<String, Object> map) {

	}

	/**
	 * @apiNote Do not use, needed only for serialization.
	 * 
	 * @return serialized map of all objects in the code
	 */
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();

		map.put("loc", loc);
		map.put("block", type);

		map = this.serialize(map);

		return map;
	}

	/**
	 * Gives the bukkit block this custom block refers to.
	 * 
	 * @return the bukkit block at the location the custom block is at
	 */
	public org.bukkit.block.Block getBlock() {
		return loc.getBlock();
	}

	/**
	 * Gives the type of block this custom block refers to.
	 * 
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
	 * @apiNote defined in the abstract class if something special wants to be done
	 *          for the block.
	 * @param breaker : the player who broke the block
	 */
	public void onBreak(Player breaker) {
		breaker.sendMessage(Utils.color("&c&lYou have broken a custom block"));
	}

	/**
	 * @apiNote defined in the abstract class if something special wants to be done
	 *          for the block place.
	 * @param place : the player who places the block.
	 */
	public void onPlace(Player place) {
		place.sendMessage(Utils.color("&c&lYou have placed a custom block"));
	}

	/**
	 * 
	 * @param is : the item stack trying to be placed.
	 * @return returns true if the item stack is the item stack contains the NBT tag
	 *         block ID.
	 */
	public boolean isBlock(ItemStack is) {
		NBTTagManager manager = new NBTTagManager(is);
		return manager.containsTag("block-id") ? manager.getStringTag("block-id").equals(this.getType().getID())
				: false;
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

	public boolean isBroken() {
		return broken;
	}

	public void setBroken(boolean broke) {
		this.broken = broke;
	}

	public String getName() {
		return this.getType().getItemName();
	}

	public void setArmorStand(ArmorStand stand) {
		this.stand = stand;
	}

	public ArmorStand getArmorStand() {
		return stand;
	}

}
