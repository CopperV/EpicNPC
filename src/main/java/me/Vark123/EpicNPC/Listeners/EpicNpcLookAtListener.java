package me.Vark123.EpicNPC.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.Vark123.EpicNPC.Events.EpicNpcInteractEvent;
import me.Vark123.EpicNPC.ZNPC.Npc;

public class EpicNpcLookAtListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onClick(EpicNpcInteractEvent e) {
		if(e.isCancelled())
			return;
		
		Npc npc = e.getNpc();
		if(npc.isLookAt())
			npc.lookAt(e.getPlayer());
	}
	
}
