package com.kingmo.utils.packet;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class FancyMessage {

	private TextComponent component;
	private HoverEvent hover;
	
	public FancyMessage(String basicMessage, String command, String... hoverText) {
		this(basicMessage, command, ClickEvent.Action.RUN_COMMAND, hoverText);
	}
	
	public FancyMessage(String basicMessage, String command, ClickEvent.Action action, String... hoverText) {
		this(basicMessage, command, action, HoverEvent.Action.SHOW_TEXT, hoverText);
	}
	
	@SuppressWarnings("deprecation")
	public FancyMessage(String basicMessage, String command, ClickEvent.Action click, HoverEvent.Action hover, String... hoverText) {
		TextComponent comp = new TextComponent(TextComponent.fromLegacyText(basicMessage));
		if(command!=null)comp.setClickEvent(new ClickEvent(click, command));
		
		StringBuilder builder = new StringBuilder();
		
		int max = hoverText.length-1;
		
		for(int i = 0; i < max; i++) {
			builder.append(hoverText[i] + "\n");	
		}
		builder.append(hoverText[max]);
		this.hover = new HoverEvent(hover, new ComponentBuilder(builder.toString()).create());
		if(hover!=null)comp.setHoverEvent(this.hover);
		
		this.component = comp;
	}
	
	public TextComponent getComponent() {
		return component;
	}
	
	public void sendMessage(CommandSender player) {
		player.spigot().sendMessage(this.getComponent());
	}
	
}
