package me.Vark123.EpicNPC.Listeners.Outer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.events.NpcSpawnEvent;
import me.Vark123.EpicNPC.Core.EpicNPCManager;
import me.Vark123.EpicNPC.Events.EpicNpcSpawnEvent;

public class NpcSpawnListener implements Listener {
	
	@EventHandler
	public void onSpawn(NpcSpawnEvent e) {
		if(e.isCancelled())
			return;
		
		Npc npc = e.getNpc();
		EpicNPCManager.get().getNpc(npc).ifPresent(eNpc -> {
			Player p = e.getPlayer();
			EpicNpcSpawnEvent event = new EpicNpcSpawnEvent(p, eNpc, e.isAsynchronous());
			Bukkit.getPluginManager().callEvent(event);

			e.setCancelled(event.isCancelled());
		});
	}

}
