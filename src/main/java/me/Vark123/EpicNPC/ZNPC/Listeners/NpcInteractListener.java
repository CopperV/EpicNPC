package me.Vark123.EpicNPC.ZNPC.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.gonalez.znpcs.npc.NPC;
import io.github.gonalez.znpcs.npc.event.NPCInteractEvent;
import me.Vark123.EpicNPC.Events.EpicNpcInteractEvent;
import me.Vark123.EpicNPC.ZNPC.Npc;
import me.Vark123.EpicNPC.ZNPC.NpcManager;

public class NpcInteractListener implements Listener {

	@EventHandler
	public void onClick(NPCInteractEvent e) {
		if(!e.isRightClick())
			return;
		
		NPC npc = e.getNpc();
		Npc epicNpc = NpcManager.get().getNpcs().get(npc.getNpcPojo().getId());
		if(epicNpc == null)
			return;
		Player p = e.getPlayer();
		
		EpicNpcInteractEvent event = new EpicNpcInteractEvent(p, epicNpc, e.isAsynchronous());
		Bukkit.getPluginManager().callEvent(event);
	}
	
}
