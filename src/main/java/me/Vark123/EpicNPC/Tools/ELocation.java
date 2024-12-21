package me.Vark123.EpicNPC.Tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ELocation {
	private String world;
	
	private double x;
	private double y;
	private double z;

	private float yaw;
	private float pitch;
	
	public Location toBukkitLocation() {
		World bukkitWorld = Bukkit.getWorld(world);
		Location loc = new Location(bukkitWorld, x, y, z, yaw, pitch);
		return loc;
	}
}
