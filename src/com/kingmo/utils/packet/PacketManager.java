package com.kingmo.utils.packet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.kingmo.utils.main.ExoticUtilityMain;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PacketManager {

	private Player player;

	public PacketManager(Player player) {
		this.player = player;
	}

	public void sendPacket(Packet<?> packet) {
		PlayerConnection connect = ((CraftPlayer) player).getHandle().playerConnection;
		connect.sendPacket(packet);
	}

	public void sendPacket(Packet<?>... pckts) {
		for (Packet<?> p : pckts) {
			sendPacket(p);
		}
	}

	public void sendTitle(String top, String bottom) {

		sendTitle(top, bottom, 5, 20, 5);

	}

	public void sendTitle(String top, String bottom, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, this.createChatComponent(top));
		PacketPlayOutTitle pk2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, this.createChatComponent(bottom));
		PacketPlayOutTitle pk3 = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
		sendPacket(packet, pk2, pk3);
	}

	public IChatBaseComponent createChatComponent(String message) {
		return ChatSerializer.a("{\"text\":\"" + message + "\"}");
	}

	public void sendActionBar(String name) {
		PacketPlayOutChat packet = new PacketPlayOutChat(createChatComponent(name), (byte) 2);
		this.sendPacket(packet);
	}

	public void sendItemName(String message) {

		//TODO: doesnt work
		
		int slot = player.getInventory().getHeldItemSlot();
		ItemStack stack0 = player.getInventory().getItem(slot);
		ItemStack stack = new ItemStack(stack0.getType(), stack0.getAmount(), stack0.getDurability());
		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
		// fool the client into thinking the item name has changed, so it actually
		// (re)displays it
		meta.setDisplayName(message);
		stack.setItemMeta(meta);

		PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(0, slot + 36, CraftItemStack.asNMSCopy(stack));
		this.sendPacket(packet);
		
	
		new BukkitRunnable() {

			@Override
			public void run() {
				PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(0, slot + 36, CraftItemStack.asNMSCopy(stack0));
				sendPacket(packet);
			}
			
		}.runTaskLater(ExoticUtilityMain.getInstance(), 5);

	}
	
	public void sendFancyMessage(FancyMessage message) {
		player.spigot().sendMessage(message.getComponent());
	}
	
	public static void sendFancyMessage(Player player, FancyMessage message) {
		player.spigot().sendMessage(message.getComponent());
	}

}
