package com.kingmo.utils.packet;

import java.util.List;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class  FancyListMessage <I> {
	
	private List<FancyMessage> fancyMessage;
	
	public FancyListMessage(List<I> list, Consumer<I> message, Consumer<I> command, Consumer<I>... hoverText ) {
		this(list, message, command, ClickEvent.Action.RUN_COMMAND, HoverEvent.Action.SHOW_TEXT, hoverText);
	}
	
	@SafeVarargs
	public FancyListMessage(List<I> list, Consumer<I> message, Consumer<I> command, ClickEvent.Action clickEvent, HoverEvent.Action hoverEvent, Consumer<I>... hoverText) {
		for(I obj: list) {
			
			String[] hover = new String[hoverText.length];
			
			for(int i = 0; i < hoverText.length; i++) 
				hover[i] = hoverText[i].consume(obj);
			
			
			FancyMessage fMessage = new FancyMessage(message.consume(obj), command.consume(obj), clickEvent, hoverEvent, hover);
			fancyMessage.add(fMessage);
		}
	}

	public List<FancyMessage> getFancyMessages(){
		return fancyMessage;
	}
	
	public void sendMessage(CommandSender player) {
		for(FancyMessage message: fancyMessage) {
			message.sendMessage(player);
		}
	}
	
}
