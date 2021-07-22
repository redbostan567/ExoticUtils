package com.kingmo.utils.packet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.kingmo.utils.nms.NMSManager;

public class PacketManager {

	private Player player;

	public PacketManager(Player player) {
		this.player = player;
	}

	public void sendPacket(Object packet) {

		try {
			Class<?> playerConnection = NMSManager.getNMSClass("PlayerConnection");

			Method m = playerConnection.getMethod("sendPacket", NMSManager.getNMSClass("Packet"));

			m.invoke(NMSManager.getConnection(player), packet);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			e.printStackTrace();
		}

	}

	public void sendPacket(Object... pckts) {
		for (Object p : pckts) {
			sendPacket(p);
		}
	}

	public void sendTitle(String top, String bottom) {

		sendTitle(top, bottom, 5, 20, 5);

	}

	public void sendTitle(String top, String bottom, int fadeIn, int stay, int fadeOut) {

		Object packet;
		try {

			Class<?> emum = NMSManager.getNMSClass("PacketPlayOutTitle$EnumTitleAction");

			Method valueOf = emum.getDeclaredMethod("valueOf", String.class);

			Class<?> clazz = NMSManager.getNMSClass("PacketPlayOutTitle");

			Constructor<?> con = clazz.getDeclaredConstructor(emum, NMSManager.getNMSClass("IChatBaseComponent"));

			packet = con.newInstance(valueOf.invoke(emum, "TITLE"), this.createChatComponent(top));
			Object pk2 = con.newInstance(valueOf.invoke(emum, "SUBTITLE"), this.createChatComponent(bottom));

			Object pk3 = clazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(fadeIn, stay,
					fadeOut);

			sendPacket(packet, pk2, pk3);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Object createChatComponent(String message)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException
			, IllegalArgumentException , InvocationTargetException {

		try {

			Class<?> clazz = NMSManager.getNMSClass("IChatBaseComponent$ChatSerializer");

			Method method = clazz.getDeclaredMethod("a", String.class);

			return method.invoke(null, "{\"text\":\"" + message + "\"}");

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw e;
		}

	}

	public void sendActionBar(String name) {

		try {
			Class<?> playOutChat = NMSManager.getNMSClass("PacketPlayOutChat");

			Object packet = playOutChat.getDeclaredConstructor(NMSManager.getNMSClass("IChatBaseComponent"), byte.class)
					.newInstance(this.createChatComponent(name), (byte) 2);

			this.sendPacket(packet);

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
		}
	}

	public void sendFancyMessage(FancyMessage message) {
		player.spigot().sendMessage(message.getComponent());
	}

	public static void sendFancyMessage(Player player, FancyMessage message) {
		player.spigot().sendMessage(message.getComponent());
	}

}
