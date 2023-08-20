package me.Vark123.EpicNPC;

import org.bukkit.Bukkit;

import me.Vark123.EpicNPC.Listeners.EpicNpcLookAtListener;
import me.Vark123.EpicNPC.Listeners.EpicNpcRunCommandListener;
import me.Vark123.EpicNPC.Listeners.PlayerJoinListener;
import me.Vark123.EpicNPC.ZNPC.Listeners.NpcInteractListener;
import me.Vark123.EpicNPC.ZNPC.Listeners.NpcSpawnListener;

public final class ListenerManager {

	private ListenerManager() {}
	
	public static void registerListeners() {
		Main inst = Main.getInst();
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), inst);
		Bukkit.getPluginManager().registerEvents(new NpcSpawnListener(), inst);
		Bukkit.getPluginManager().registerEvents(new NpcInteractListener(), inst);

		Bukkit.getPluginManager().registerEvents(new EpicNpcLookAtListener(), inst);
		Bukkit.getPluginManager().registerEvents(new EpicNpcRunCommandListener(), inst);
	}
	
}
