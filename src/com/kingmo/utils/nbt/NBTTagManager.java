package com.kingmo.utils.nbt;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class NBTTagManager{
	
	private NBTTagCompound tag;
	
	public NBTTagManager() {
		tag = new NBTTagCompound();
	}
	
	public NBTTagManager(ItemStack is) {
		tag = CraftItemStack.asNMSCopy(is).getTag();
	}
	
	public ItemStack addTagToItem(ItemStack is) {
		
		net.minecraft.server.v1_8_R3.ItemStack nmsis = CraftItemStack.asNMSCopy(is);
		
		NBTTagCompound tag = nmsis.getTag();
		tag.a(this.tag);
		nmsis.setTag(tag);
		
		return CraftItemStack.asBukkitCopy(nmsis);
	}
	
	public void setTag(String name, NBTBase nbt) {
		tag.set(name, nbt);
	}
	
	public boolean containsTag(String name) {
		
		return tag != null ? tag.hasKey(name) : false;
	}
	
	public void removeTag(String name) {
		tag.remove(name);
	}
	
	public NBTBase getTag(String name) {
		return tag.get(name);
	}
	
	public Entity addTagToEntity(Entity entity) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		
		NBTTagCompound tag = nmsEntity.getNBTTag();
		tag.a(this.tag);
		nmsEntity.f(tag);
		
		return CraftEntity.getEntity((CraftServer) Bukkit.getServer(), nmsEntity);
	}

	public String getStringTag(String string) {
		return tag.getString(string);
	}
	
	public void update(ItemStack is) {
		tag = CraftItemStack.asNMSCopy(is).getTag();
	}
	
	
	public static Map<String, NBTBase> cast(Map<String, String> tags) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unused")
	public static Map<String, String> castTo(Map<String, NBTBase> tags){
		
		Map<String, String> finalMap = new HashMap<>();
		
		for(String key : tags.keySet()) {
			
		}
		return null;
	}
}
