package me.Vark123.EpicNPC.ZNPC;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.configuration.ConfigurationSection;

import lombok.Getter;

@Getter
public final class NpcManager {

	private static final NpcManager inst = new NpcManager();
	
	private final Map<Integer, Npc> npcs;
	private final AtomicInteger idController;
	
	private NpcManager() {
		npcs = new LinkedHashMap<>();
		idController = new AtomicInteger();
	}
	
	public static final NpcManager get() {
		return inst;
	}
	
	public Optional<Npc> registerNpc(ConfigurationSection section) {
		Npc npc = new Npc(section);
		return Optional.ofNullable(npcs.put(npc.getId(), npc));
	}
	
	public void initNpcs() {
		npcs.values().parallelStream()
			.filter(npc -> npc.getNpc() == null)
			.forEach(npc -> npc.initNpc());
	}
	
}
