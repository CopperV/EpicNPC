package me.Vark123.EpicNPC.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.Vark123.EpicNPC.Events.EpicNpcInteractEvent;
import me.Vark123.EpicNPC.ZNPC.Npc;
import me.clip.placeholderapi.PlaceholderAPI;

public class EpicNpcRunCommandListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onClick(EpicNpcInteractEvent e) {
		if(e.isCancelled())
			return;
		
		Npc npc = e.getNpc();
		Player p = e.getPlayer();
		npc.getCommands().ifPresent(cmds -> {
			cmds.stream().forEachOrdered(cmd -> {
				String placeholderCmd = PlaceholderAPI.setPlaceholders(p, cmd);
				placeholderCmd = placeholderCmd.replace("%player%", p.getName());
				if(placeholderCmd.contains("asOp: ")) {
					placeholderCmd = placeholderCmd.replace("asOp: ", "");
					p.setOp(true);
					Bukkit.dispatchCommand(p, placeholderCmd);
					p.setOp(false);
				} else if(placeholderCmd.contains("asPlayer: ")) {
					placeholderCmd = placeholderCmd.replace("asPlayer: ", "");
					Bukkit.dispatchCommand(p, placeholderCmd);
				} else {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), placeholderCmd);
				}
			});
		});
	}
	
}
