package me.Vark123.EpicNPC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.gonalez.znpcs.user.ZUser;
import me.Vark123.EpicNPC.FileManager;
import me.Vark123.EpicNPC.Main;
import me.Vark123.EpicNPC.Events.EpicNpcSpawnEvent;
import me.Vark123.EpicNPC.ZNPC.NpcManager;

public class NpcCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("enpc")) 
			return false;
		if(!sender.hasPermission("epicnpc.admin"))
			return false;
		if(args.length == 0) {
			showCorrectUsage(sender);
			return false;
		}
		switch(args[0].toLowerCase()) {
			case "reload":
				NpcManager.get().getNpcs().values().parallelStream().forEach(npc -> {
					npc.getNpc().deleteViewers();
				});
				NpcManager.get().getNpcs().clear();
				
				FileManager.init();
				NpcManager.get().initNpcs();
				
				Bukkit.getOnlinePlayers().stream().forEach(p -> {
					NpcManager.get().getNpcs().values().stream().forEach(npc -> {
						EpicNpcSpawnEvent event = new EpicNpcSpawnEvent(p, npc);
						Bukkit.getPluginManager().callEvent(event);
						if (event.isCancelled())
							return;
						ZUser user = new ZUser(p.getUniqueId());
						npc.getNpc().spawn(user);
					});
				});
				sender.sendMessage("§7["+Main.getInst().getPrefix()+"§7] §a§lPrzeladowales plugin EpicNPC");
				break;
			default:
				showCorrectUsage(sender);
				return false;
		}
		return true;
	}
	
	private void showCorrectUsage(CommandSender sender) {
		sender.sendMessage("§7["+Main.getInst().getPrefix()+"§7] §c§lPoprawne uzycie komendy §c§o/enpc");
		sender.sendMessage("    §4> §e§o/enpc reload");
	}

}
