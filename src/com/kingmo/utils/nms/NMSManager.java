package com.kingmo.utils.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NMSManager {

	private static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]
			+ ".";

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
	
	public static Object asNMSCopy(ItemStack is) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> itemStack = NMSManager.getCBClass("CraftItemStack", "inventory");
		
		Method asNMSCopy = itemStack.getMethod("asNMSCopy", ItemStack.class);
		
		return asNMSCopy.invoke(null, is);
	}
	
	public static ItemStack asBukkitCopy(Object o) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<?> itemStack = NMSManager.getCBClass("CraftItemStack", "inventory");
		
		Method asBukkitCopy = itemStack.getDeclaredMethod("asBukkitCopy", NMSManager.getNMSClass("ItemStack"));
		
		return (ItemStack) asBukkitCopy.invoke(null, o);
	}

}
