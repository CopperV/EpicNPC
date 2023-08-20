package me.Vark123.EpicNPC;

import org.bukkit.Bukkit;

import me.Vark123.EpicNPC.Commands.NpcCommand;

public final class CommandManager {

	private CommandManager() {}
	
	public static void setExecutors() {
		Bukkit.getPluginCommand("enpc").setExecutor(new NpcCommand());
	}
	
}
