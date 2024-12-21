package me.Vark123.EpicNPC.Listeners.Outer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import me.Vark123.EpicNPC.Core.EpicNPCManager;
import me.Vark123.EpicNPC.Events.EpicNpcInteractEvent;

public class NpcInteractListener implements Listener {

	@EventHandler
	public void onClick(NpcInteractEvent e) {
		if(e.isCancelled())
			return;
		
		Npc npc = e.getNpc();
		EpicNPCManager.get().getNpc(npc).ifPresent(eNpc -> {
			Player p = e.getPlayer();
			
			ItemStack it = p.getInventory().getItemInMainHand();
			if(it != null && it.getType().equals(Material.FISHING_ROD)
					&& p.getWorld().getName().equals("Tanalorr")) {
				return;
			}
			
			EpicNpcInteractEvent event = new EpicNpcInteractEvent(p, eNpc, e.isAsynchronous());
			Bukkit.getPluginManager().callEvent(event);
			
			e.setCancelled(event.isCancelled());
		});
	}
	
}
