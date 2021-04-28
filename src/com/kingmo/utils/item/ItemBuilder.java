package com.kingmo.utils.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.kingmo.utils.glow.Glow;	
import com.kingmo.utils.main.Utils;
import com.kingmo.utils.nbt.NBTTagManager;

public class ItemBuilder {

	private ItemStack item;
	private Material material;
	private List<String> lore;
	private String displayName;
	private boolean glowing;

	public ItemBuilder(String path, FileConfiguration config) {
		material = config.contains(path + ".mat") ? Material.valueOf(config.getString(path + ".mat"))
				: Material.DIAMOND;
		lore = config.contains(path + ".lore") ? colorCode(config.getStringList(path + ".lore")) : new ArrayList<>();
		displayName = config.contains(path + ".name")
				? ChatColor.translateAlternateColorCodes('&', config.getString(path + ".name"))
				: "DEFAULT DISPLAY NAME PLEASE SET IN CONFIG";
		glowing = config.contains(path + ".glow") ? config.getBoolean(path + ".glow") : false;

		item = new ItemStack(material);
		ItemMeta im = item.getItemMeta();

		im.setLore(lore);
		im.setDisplayName(displayName);
		if (glowing)
			im.addEnchant(new Glow(), 0, true);
		item.setItemMeta(im);

	}

	private List<String> colorCode(List<String> stringList) {
		stringList.forEach((string) -> {
			string = ChatColor.translateAlternateColorCodes('&', string);
		});
		return stringList;
	}

	public ItemBuilder(String name, Material m, List<String> lore) {
		ItemStack item = new ItemStack(m);
		ItemMeta itemMeta = item.getItemMeta();

		itemMeta.setDisplayName(name);
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		this.item = item;
	}

	public ItemBuilder(String name, Material m) {
		this(name, m, new ArrayList<>());
	}

	public ItemBuilder(Material m) {
		this(m.name(), m);
	}

	public ItemBuilder setName(String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return this;
	}

	public ItemBuilder setLore(String... lores) {
		List<String> lore = Utils.toList(lores);
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
		return this;
	}

	public ItemBuilder setGlow(boolean glow) {
		ItemMeta im = item.getItemMeta();
		if (glow)
			im.addEnchant(new Glow(), 0, true);
		item.setItemMeta(im);
		return this;
	}

	public ItemBuilder addNBTTag(String key, Object base) {
		NBTTagManager manager = new NBTTagManager(item);
		manager.setTag(key, base);
		item = manager.addTagToItem(item);
		return this;
	}

	public ItemBuilder addNBTTag(Map<String, Object> tags) {
		if(tags==null)return this;
		for(String str: tags.keySet()) {
			this.addNBTTag(str, tags.get(str));
		}
		
		return this;
		
	}

	public ItemStack getItem() {
		return item;
	}

}
