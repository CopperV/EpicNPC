package me.Vark123.EpicNPC.Core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import de.oliver.fancynpcs.api.Npc;
import lombok.Getter;

@Getter
public class EpicNPCManager {

	private static final EpicNPCManager inst = new EpicNPCManager();
	
	private final AtomicInteger idController;
	private final Map<Integer, EpicNPC> npcs;
	private final Map<Integer, Integer> idMap;
	
	private EpicNPCManager() {
		idController = new AtomicInteger();
		
		npcs = new LinkedHashMap<>();
		idMap = new LinkedHashMap<>();
	}
	
	public static final EpicNPCManager get() {
		return inst;
	}
	
	public void registerNpc(EpicNPC npc) {
		npcs.put(npc.getId(), npc);
	}
	
	public void initNpcs() {
		npcs.values().parallelStream()
			.filter(npc -> npc.getNpcData() == null || npc.getNpc() == null)
			.forEach(npc -> {
				npc.initNpc();
				idMap.put(npc.getNpc().getEntityId(), npc.getId());
			});
	}
	
	public Optional<EpicNPC> getNpc(Npc npc) {
		return getNpc(npc.getEntityId());
	}
	
	public Optional<EpicNPC> getNpc(int entityId) {
		if(!idMap.containsKey(entityId))
			return Optional.empty();
		return Optional.ofNullable(npcs.get(idMap.get(entityId)));
	}
	
}
