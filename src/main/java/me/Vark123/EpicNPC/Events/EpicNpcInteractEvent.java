package me.Vark123.EpicNPC.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import me.Vark123.EpicNPC.Core.EpicNPC;

@Getter
public class EpicNpcInteractEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	@Setter
	private boolean cancelled;

	private Player player;
	private EpicNPC npc;
	
	public EpicNpcInteractEvent(Player p, EpicNPC npc) {
		this(p, npc, false);
	}
	
	public EpicNpcInteractEvent(Player p, EpicNPC npc, boolean async) {
		super(async);
		this.player = p;
		this.npc = npc;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
