package com.kingmo.utils.blocks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import net.minecraft.server.v1_8_R3.NBTBase;

public interface BlockType extends Serializable {

	public String getName();

	public String getItemName();

	public List<String> getLore();

	public boolean isGlowing();

	public Map<String, NBTBase> getTags();

	public Material getMaterial();

	public Class<? extends Block> getBlockClass();

	public Map<String, Object> serialize();

	public String getID();
	
	public int getDelay();

}
