package me.Vark123.EpicNPC;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.gonalez.znpcs.user.ZUser;
import lombok.Getter;
import me.Vark123.EpicNPC.Events.EpicNpcSpawnEvent;
import me.Vark123.EpicNPC.ZNPC.NpcManager;

@Getter
public class Main extends JavaPlugin {

	@Getter
	private static Main inst;

	private String prefix;

	@Override
	public void onEnable() {
		inst = this;

		this.prefix = "§x§c§3§d§f§1§5§lE§x§a§5§d§8§1§a§lp§x§8§6§d§1§1§e§li§x§6§8§c§a§2§3§lc§x§4§9§c§2§2§8§lN§x§2§b§b§b§2§c§lP§x§0§c§b§4§3§1§lC";

		CommandManager.setExecutors();
		ListenerManager.registerListeners();
		FileManager.init();

		Bukkit.getOnlinePlayers().stream().forEach(p -> {
			NpcManager.get().initNpcs();
			NpcManager.get().getNpcs().values().stream().forEach(npc -> {
				EpicNpcSpawnEvent event = new EpicNpcSpawnEvent(p, npc);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled())
					return;
				ZUser user = new ZUser(p.getUniqueId());
				npc.getNpc().spawn(user);
			});
		});
	}

	@Override
	public void onDisable() {
		NpcManager.get().getNpcs().values().parallelStream().forEach(npc -> {
			npc.getNpc().deleteViewers();
		});
		NpcManager.get().getNpcs().clear();
	}

}
