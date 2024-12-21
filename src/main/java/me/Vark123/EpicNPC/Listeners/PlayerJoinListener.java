package me.Vark123.EpicNPC.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Vark123.EpicNPC.Core.EpicNPCManager;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		EpicNPCManager.get().initNpcs();
	}
	
}
