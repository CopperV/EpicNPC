package me.Vark123.EpicNPC.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.Setter;
import me.Vark123.EpicNPC.ZNPC.Npc;

@Getter
public class EpicNpcSpawnEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	@Setter
	private boolean cancelled;

	private Player player;
	private Npc npc;
	
	public EpicNpcSpawnEvent(Player p, Npc npc) {
		this(p, npc, false);
	}
	
	public EpicNpcSpawnEvent(Player p, Npc npc, boolean async) {
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
