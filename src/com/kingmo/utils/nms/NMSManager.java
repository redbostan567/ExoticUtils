package com.kingmo.utils.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.glow.GlowId;
import com.kingmo.utils.glow.GlowNameSpaced;
import com.kingmo.utils.main.Utils;

public class NMSManager {
	
	private static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]
			+ ".";

	private static final boolean legacyEnchants = Utils.startsWithAny("v", NMSManager.getVersion(), Utils.toList("1_8",
			"1_9",
			"1_10",
			"1_11",
			"1_12",
			"1_7"));
	
	private static final boolean legacyText = Utils.startsWithAny("v", NMSManager.getVersion(), Utils.toList("1_8", "1_9", "1_10"));
	
	public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
		String name = "net.minecraft.server." + version + nmsClassString;
		Class<?> nmsClass = Class.forName(name);
		return nmsClass;
	}

	public static Object getConnection(Player player) throws SecurityException, NoSuchMethodException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method getHandle = player.getClass().getMethod("getHandle");
		Object nmsPlayer = getHandle.invoke(player);
		Field conField = nmsPlayer.getClass().getField("playerConnection");
		Object con = conField.get(nmsPlayer);
		return con;
	}

	public static Class<?> getCBClass(String string) throws ClassNotFoundException {
		String name = "org.bukkit.craftbukkit." + version + string;
		return Class.forName(name);
	}

	public static Class<?> getCBClass(String name, String... packages) throws ClassNotFoundException {
		StringBuilder builder = new StringBuilder("org.bukkit.craftbukkit." + version);

		for (String str : packages)
			builder.append(str + ".");

		return Class.forName(builder.append(name).toString());

	}

	public static Object asNMSCopy(ItemStack is) throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> itemStack = NMSManager.getCBClass("CraftItemStack", "inventory");

		Method asNMSCopy = itemStack.getMethod("asNMSCopy", ItemStack.class);

		return asNMSCopy.invoke(null, is);
	}

	public static ItemStack asBukkitCopy(Object o) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> itemStack = NMSManager.getCBClass("CraftItemStack", "inventory");

		Method asBukkitCopy = itemStack.getDeclaredMethod("asBukkitCopy", NMSManager.getNMSClass("ItemStack"));

		return (ItemStack) asBukkitCopy.invoke(null, o);
	}

	public static Enchantment getGlow() {
		System.out.println(NMSManager.version);
		if (NMSManager.isEnchantLegacy())
			return new GlowId();
		return new GlowNameSpaced();
	}

	public static String getVersion() {
		return NMSManager.version;
	}

	public static boolean isEnchantLegacy() {
		return legacyEnchants;
	}

	public static boolean isDotSpigotLegacy() {
		return legacyText;
	}

}
