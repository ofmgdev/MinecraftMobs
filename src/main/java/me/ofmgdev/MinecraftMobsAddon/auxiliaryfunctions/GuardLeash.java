package me.ofmgdev.MinecraftMobsAddon.auxiliaryfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class GuardLeash {

    public static void startLeash(JavaPlugin plugin, LivingEntity boss, List<LivingEntity> guards, double maxDistance) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!boss.isValid()) {
                    cancel();
                    return;
                }

                guards.removeIf(guard -> !guard.isValid());

                for (LivingEntity guard : guards) {
                    double distance = guard.getLocation().distance(boss.getLocation());

                    if (distance > maxDistance) {
                        // טלפורט השומר חזרה לבוס
                        org.bukkit.Location returnLoc = boss.getLocation().clone().add(
                                (Math.random() - 0.5) * 6, 0, (Math.random() - 0.5) * 6);

                        // חפש סוס קרוב לרוכב לפני הטלפורט
                        org.bukkit.entity.AbstractHorse nearbyHorse = guard.getWorld().getNearbyEntities(guard.getLocation(), 3, 3, 3).stream()
                                .filter(entity -> entity instanceof org.bukkit.entity.AbstractHorse)
                                .map(entity -> (org.bukkit.entity.AbstractHorse) entity)
                                .findFirst()
                                .orElse(null);

                        // טלפורט את הרוכב
                        guard.teleport(returnLoc);

                        // אם יש סוס - טלפורט אותו גם
                        if (nearbyHorse != null) {
                            nearbyHorse.teleport(returnLoc);
                            plugin.getServer().getScheduler().runTaskLater(plugin, () ->
                                    nearbyHorse.addPassenger(guard), 2L);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 40); // כל 2 שניות
    }
}