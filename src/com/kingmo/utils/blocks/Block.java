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

public abstract class Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private org.bukkit.block.Block block;
	private BlockType type;

	public Block(Location loc, BlockType type) {
		block = loc.getBlock();
		block.setType(type.getMaterial());
		this.type = type;
	}

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

	public abstract void run();

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

	public org.bukkit.block.Block getBlock() {
		return block;
	}

	protected void setBlock(org.bukkit.block.Block block) {
		this.block = block;
	}

	public BlockType getType() {
		return type;
	}

	protected void setType(BlockType type) {
		this.type = type;
	}

	public void onBreak(Player breaker) {
	}

	public void onPlace(Player place) {
	}

	public boolean isBlock(ItemStack is) {
		NBTTagManager manager = new NBTTagManager(is);
		return manager.getStringTag("block-id").equals(this.getType().getID());
	}

	public static ItemStack createItemStack(BlockType t) {
		return new ItemBuilder(t.getItemName(), t.getMaterial(), t.getLore()).setGlow(t.isGlowing())
				.addNBTTag(t.getTags()).addNBTTag("block-id", new NBTString(t.getID())).getItem();
	}

}
