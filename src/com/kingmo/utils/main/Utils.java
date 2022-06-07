package com.kingmo.utils.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.kingmo.utils.commands.Command;
import com.kingmo.utils.commands.CommandManager;
import com.kingmo.utils.main.ReturnableMessage;
import com.kingmo.utils.packet.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;



public class Utils {

	public static boolean random(int min, int max) {
		Random r = new Random();
		return r.nextInt(max + 1) <= min;
	}

	public static int randomNumber(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@SafeVarargs
	public static <E> List<E> toList(E... e){
		List<E> list = new ArrayList<>();
		for(E e1: e)list.add(e1);

		return list;
	}

	@SafeVarargs
	public static <E> Set<E> toSet(E... e){

		Set<E> set = new HashSet<>();

		for(E e1: e)set.add(e1);
		return set;
	}

	public static <E> List<E> add(List<E> list, E obj){
		list.add(obj);
		return list;
	}

	public static <E> List<E> weigh(List<E> list, E type, int amount) {
		for(int i = 0; i < amount; i++)list.add(type);
		return list;
	}

	public static Object loadData(File f) {
		Object ob;
		try {
			if (!f.exists())
				f.createNewFile();

			ObjectInputStream ois = new BukkitObjectInputStream(new FileInputStream(f));
			ob = ois.readObject();
			ois.close();
			System.out.println("Loaded " + f.getName());
			return ob;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void save(File f, Object o) {
		ObjectOutputStream oos;

		System.out.println(o);

		try {
			if (!f.exists())
				f.createNewFile();
			oos = new BukkitObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean nonNull(ItemStack stack) {

		return stack != null ? stack.hasItemMeta() ? stack.getItemMeta().hasLore() && stack.getItemMeta().hasDisplayName() : false : false;
	}


	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static List<String> toColoredList(String... e) {
		List<String> list = new ArrayList<>();
		for(String e1: e)list.add(Utils.color(e1));

		return list;
	}

	public static <I> List<I> toList(Set<I> set){
		List<I> list = new ArrayList<>();
		for(I i: set)list.add(i);
		return list;
	}
	
	public static boolean startsWithAny(String test, List<String> list) {
		return startsWithAny("", test, list);
	}

	public static boolean startsWithAny(String prefix, String test, List<String> list) {
		for(String str: list)
			if (test.startsWith(prefix + str))return true;

		return false;
	}

	public static <I> I sendAndReturn(CommandSender sender, I returnable, Command cmd, String... message){
		for(String msg: message){
			sender.sendMessage(CommandManager.get(cmd, msg));
		}
		return returnable;
	}

	public static <I> I sendAndReturn(CommandSender sender, I returnable, String... message){
		for(String msg: message){
			sender.sendMessage(msg);
		}
		return returnable;
	}

	public static <I> I sendAndReturn(CommandSender sender, ReturnableMessage<I> msg){
		sender.sendMessage(msg.getMessage());
		return msg.getReturned();
	}
	
	public static <I> I sendAndReturn(CommandSender sender, I returnable, FancyMessage msg) {
		msg.sendMessage(sender);
		return returnable;
	}




}
