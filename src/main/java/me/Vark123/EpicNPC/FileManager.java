package me.Vark123.EpicNPC;

import java.io.File;
import java.util.Arrays;

import org.bukkit.configuration.file.YamlConfiguration;

import me.Vark123.EpicNPC.ZNPC.NpcManager;

public final class FileManager {

	private static File npcs = new File(Main.getInst().getDataFolder(), "npc");

	private FileManager() {}
	
	public static void init() {
		if(!Main.getInst().getDataFolder().exists())
			Main.getInst().getDataFolder().mkdir();
		
		if(!npcs.exists())
			npcs.mkdir();
		
		Arrays.asList(npcs.listFiles()).stream()
			.filter(file -> file.isFile() 
					&& file.getName().endsWith(".yml"))
			.map(YamlConfiguration::loadConfiguration)
			.forEach(NpcManager.get()::registerNpc);
	}
	
}
