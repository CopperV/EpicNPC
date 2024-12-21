package me.Vark123.EpicNPC.Core;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import de.oliver.fancynpcs.api.utils.NpcEquipmentSlot;
import de.oliver.fancynpcs.api.utils.SkinFetcher;
import de.oliver.fancynpcs.api.utils.SkinFetcher.SkinData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.Vark123.EpicNPC.Tools.ELocation;
import me.Vark123.EpicNPC.Tools.MineSkinFetcher;
import net.md_5.bungee.api.ChatColor;

@Getter
@EqualsAndHashCode
public class EpicNPC {

	private int id;

	private List<String> display;
	private ELocation location;
	
	private Optional<String> showQuest = Optional.empty();
	private Optional<String> hideQuest = Optional.empty();

	private boolean lookAt;
	private List<String> commands;
	
	private float scale;
	
	private NpcData npcData;
	private Npc npc;
	
	private boolean isPlayer = true;
	private EntityType entityType;
	private Skin skin;
	
	private Map<NpcEquipmentSlot, ItemStack> equipment;
	
	public EpicNPC(ConfigurationSection section) {
		this.id = EpicNPCManager.get().getIdController().getAndIncrement();
		
		String strWorld = section.getString("location.world");
		double x = section.getDouble("location.x");
		double y = section.getDouble("location.y");
		double z = section.getDouble("location.z");
		float yaw = (float) section.getDouble("location.yaw");
		float pitch = (float) section.getDouble("location.pitch");
		this.location = ELocation.builder()
				.world(strWorld)
				.x(x)
				.y(y)
				.z(z)
				.yaw(yaw)
				.pitch(pitch)
				.build();
		
		if(section.contains("mob-type") && section.isString("mob-type")) {
			isPlayer = false;
			String mobType = section.getString("mob-type").toUpperCase();
			entityType = EntityType.valueOf(mobType);
		} else {
			if(section.contains("skin")) {
				String value = section.getString("skin.value");
				String signature = section.getString("skin.signature");
				this.skin = new Skin(signature, value);
			} else if (section.contains("skinid")) {
				String skinArr[] = MineSkinFetcher.fetchSkinFromIdAsync(section.getInt("skinid"));
				if(skinArr != null) {
					String value = skinArr[0];
					String signature = skinArr[1];
					this.skin = new Skin(signature, value);
				}
			}
		}
		
		this.display = new LinkedList<>();
		section.getStringList("display").stream().forEachOrdered(s -> {
			this.display.add(ChatColor.translateAlternateColorCodes('&', s));
		});

		if(section.contains("showquest"))
			this.showQuest = Optional.of(section.getString("showquest"));
		
		if(section.contains("hidequest"))
			this.hideQuest = Optional.of(section.getString("hidequest"));
		
		this.lookAt = section.getBoolean("lookat", true);
		this.commands = section.getStringList("commands");
		this.scale = (float) section.getDouble("scale", 1);
		
		equipment = new LinkedHashMap<>();
		if(section.contains("eq")) {
			ConfigurationSection eqSection = section.getConfigurationSection("eq");
			eqSection.getKeys(false).stream()
				.filter(eqSection::isConfigurationSection)
				.filter(key -> NpcEquipmentSlot.parse(key.toUpperCase()) != null)
				.map(eqSection::getConfigurationSection)
				.forEach(itSection -> {
					NpcEquipmentSlot slot = NpcEquipmentSlot.parse(itSection.getName().toUpperCase());
					Material m = Material.getMaterial(itSection.getString("item").toUpperCase());
					String itDisplay = itSection.getString("display", m.name());
					
					ItemStack it = new ItemStack(m);{
						ItemMeta im = it.getItemMeta();
						im.setDisplayName(itDisplay);
						it.setItemMeta(im);
					}
					
					equipment.put(slot, it);
				});
		}
	}
	
	public void initNpc() {
		UUID creator = UUID.randomUUID();
		npcData = new NpcData("§7[§8NPC§7] §8§o"+id, creator, location.toBukkitLocation());
		
		npcData.setDisplayName(display.get(0));
		npcData.setCollidable(false);
		npcData.setScale(scale);
		npcData.setShowInTab(false);
		npcData.setTurnToPlayer(lookAt);
		
		if(!isPlayer && entityType != null)
			npcData.setType(entityType);
		else if(isPlayer && skin != null) {
			SkinFetcher.SkinData skinData = new SkinData(creator.getMostSignificantBits()+"-"+id, skin.getValue(), skin.getSignature());
//			SkinFetcher fetcher = new SkinFetcher(creator.getMostSignificantBits()+"-"+id, skin.getValue(), skin.getSignature());
			npcData.setSkin(skinData);
		}
		
		npcData.setEquipment(equipment);
		
		npc = FancyNpcsPlugin.get().getNpcAdapter().apply(npcData);
		npc.setSaveToFile(false);
		FancyNpcsPlugin.get().getNpcManager().registerNpc(npc);
		
		npc.create();
		npc.spawnForAll();
	}
	
	public void lookAt(Player p) {
		lookAt(p, p.getLocation());
	}
	
	public void lookAt(Player p, Location loc) {
		npc.lookAt(p, loc);
	}
	
	public String getName() {
		return display.get(0);
	}
	
	@AllArgsConstructor
	@Getter
	private class Skin {
		private String signature;
		private String value;
	}
	
}
