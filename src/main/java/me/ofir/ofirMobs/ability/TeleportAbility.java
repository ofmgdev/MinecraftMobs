package me.ofir.ofirMobs.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportAbility {

    public static void startTeleport(JavaPlugin plugin, LivingEntity entity, int intervalSeconds, double radius) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                Location currentLoc = entity.getLocation();
                double x = currentLoc.getX() + (Math.random() - 0.5) * radius * 2;
                double z = currentLoc.getZ() + (Math.random() - 0.5) * radius * 2;
                Location newLoc = new Location(currentLoc.getWorld(), x, currentLoc.getY(), z);

                newLoc.setY(newLoc.getWorld().getHighestBlockYAt(newLoc) + 1);
                entity.teleport(newLoc);

                newLoc.getWorld().spawnParticle(Particle.PORTAL, newLoc, 30, 1, 1, 1, 0.1);
            }
        }.runTaskTimer(plugin, 40, intervalSeconds * 20L);
    }
}
