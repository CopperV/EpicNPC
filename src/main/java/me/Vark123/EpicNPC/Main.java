package me.Vark123.EpicNPC;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

@Getter
public class Main extends JavaPlugin {

	@Getter
	private static Main inst;
	
	private String prefix;

	@Override
	public void onEnable() {
		inst = this;
		
		CommandManager.setExecutors();
		ListenerManager.registerListeners();
		FileManager.init();
	}

	@Override
	public void onDisable() {
		
	}
	
}
