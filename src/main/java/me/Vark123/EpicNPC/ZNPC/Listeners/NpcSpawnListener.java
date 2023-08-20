package me.Vark123.EpicNPC.ZNPC.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.gonalez.znpcs.npc.NPC;
import io.github.gonalez.znpcs.npc.event.NPCSpawnEvent;
import me.Vark123.EpicNPC.Events.EpicNpcSpawnEvent;
import me.Vark123.EpicNPC.ZNPC.Npc;
import me.Vark123.EpicNPC.ZNPC.NpcManager;

public class NpcSpawnListener implements Listener {
	
	@EventHandler
	public void onSpawn(NPCSpawnEvent e) {
		if(e.isCancelled())
			return;
		
		NPC npc = e.getNpc();
		Npc epicNpc = NpcManager.get().getNpcs().get(npc.getNpcPojo().getId());
		if(epicNpc == null)
			return;
		Player p = e.getPlayer();
		EpicNpcSpawnEvent event = new EpicNpcSpawnEvent(p, epicNpc, e.isAsynchronous());
		Bukkit.getPluginManager().callEvent(event);
		
		e.setShow(!event.isCancelled());
	}

}
