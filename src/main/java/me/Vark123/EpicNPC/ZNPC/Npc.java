package me.Vark123.EpicNPC.ZNPC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.gonalez.znpcs.npc.ItemSlot;
import io.github.gonalez.znpcs.npc.NPC;
import io.github.gonalez.znpcs.npc.NPCModel;
import io.github.gonalez.znpcs.npc.NPCType;
import io.github.gonalez.znpcs.user.ZUser;
import io.github.gonalez.znpcs.utility.location.ZLocation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.Vark123.EpicNPC.Tools.MineSkinFetcher;
import net.md_5.bungee.api.ChatColor;

@EqualsAndHashCode
@Getter
public class Npc {

	private int id;
	
	private Optional<String> showQuest = Optional.empty();
	private Optional<String> hideQuest = Optional.empty();
	private boolean lookAt;
	private Optional<List<String>> commands = Optional.empty();
	
	private NPC npc;
	private NPCModel model;
	
	public Npc(ConfigurationSection section) {
		this.id = NpcManager.get().getIdController().getAndIncrement();
		
		this.model = new NPCModel(id);
		
		String strWorld = section.getString("location.world");
		double x = section.getDouble("location.x");
		double y = section.getDouble("location.y");
		double z = section.getDouble("location.z");
		float yaw = (float) section.getDouble("location.yaw");
		float pitch = (float) section.getDouble("location.pitch");
		model.setLocation(new ZLocation(
				strWorld, 
				x, y, z, 
				yaw, pitch));
		
		List<String> tmpDisplay = new LinkedList<>();
		section.getStringList("display").stream().forEachOrdered(s -> {
			tmpDisplay.add(ChatColor.translateAlternateColorCodes('&', s));
		});
		model.setHologramLines(tmpDisplay);
		
		boolean skin = false;
		String value = null;
		String signature = null;
		if(section.contains("skin")) {
			skin = true;
			value = section.getString("skin.value");
			signature = section.getString("skin.signature");
		} else if(section.contains("skinid")) {
			String skinArr[] = MineSkinFetcher.fetchSkinFromIdAsync(section.getInt("skinid"));
			if(skinArr != null) {
				skin = true;
				value = skinArr[0];
				signature = skinArr[1];
			}
		}
		if(skin) {
			model.setSkin(value);
			model.setSignature(signature);
		}
		
		if(section.contains("mob-type")) {
			model.setNpcType(NPCType.valueOf(section.getString("mob-type").toUpperCase()));
		}
		
		if(section.contains("eq")) {
			ConfigurationSection eqSection = section.getConfigurationSection("eq");
			Map<ItemSlot, ItemStack> eq = new ConcurrentHashMap<>();
			eqSection.getKeys(false).forEach(key -> {
				String id = eqSection.getString(key+".item");
				ItemStack item = new ItemStack(Material.getMaterial(id.toUpperCase()));{
					ItemMeta im = item.getItemMeta();
					if(eqSection.contains(key+".display")) {
						im.setDisplayName(eqSection.getString(key+".display"));
					}
					item.setItemMeta(im);
				}
				eq.put(ItemSlot.valueOf(key.toUpperCase()), item);
			});
			model.setNpcEquip(eq);
		}
		
		if(section.contains("showquest"))
			this.showQuest = Optional.of(section.getString("showquest"));
		
		if(section.contains("hidequest"))
			this.hideQuest = Optional.of(section.getString("hidequest"));
	
		if(section.contains("lookat"))
			this.lookAt = section.getBoolean("lookat");
		
		if(section.contains("commands"))
			this.commands = Optional.of(section.getStringList("commands"));
	}
	
	public void initNpc() {
		if(npc == null)
			npc = new NPC(model, true);
	}
	
	public void lookAt(Player p) {
		ZUser user = new ZUser(p.getUniqueId());
		npc.lookAt(user, p.getLocation(), false);
	}
	
	public String getName() {
		return model.getHologramLines().get(0);
	}
	
}
