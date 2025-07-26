package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GroundSlamAbility {

    public static void startGroundSlam(JavaPlugin plugin, LivingEntity entity, double intervalSeconds, double radius, double damage) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                if (entity instanceof Monster monster && monster.getTarget() != null && monster.getTarget().isValid()) {
                    LivingEntity target = monster.getTarget();
                    double distance = entity.getLocation().distance(target.getLocation());

                    // Ground slam אם המטרה קרובה מספיק
                    if (distance <= radius * 1.5) {
                        performGroundSlam(entity, radius, damage);
                    }
                }
            }
        }.runTaskTimer(plugin, 60, (long)(intervalSeconds * 20));
    }

    private static void performGroundSlam(LivingEntity entity, double radius, double damage) {
        Location loc = entity.getLocation();

        // אפקטי רעידת אדמה
        loc.getWorld().spawnParticle(Particle.BLOCK, loc, 100, radius, 1, radius, 0.1,
                org.bukkit.Material.STONE.createBlockData());
        loc.getWorld().spawnParticle(Particle.LARGE_SMOKE, loc, 50, radius, 2, radius, 0.05);
        loc.getWorld().spawnParticle(Particle.EXPLOSION, loc, 10, radius/2, 1, radius/2, 0);

        // צליל רעידת אדמה
        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.5f);
        loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_LAND, 2.0f, 0.3f);

        // נזק לכל הישויות בסביבה
        for (LivingEntity nearby : loc.getWorld().getLivingEntities()) {
            if (nearby != entity && nearby.getLocation().distance(loc) <= radius) {
                // רק לשחקנים וvillagers
                if (nearby instanceof org.bukkit.entity.Player || nearby instanceof org.bukkit.entity.Villager) {
                    nearby.damage(damage);
                    // דחיפה החוצה
                    org.bukkit.util.Vector knockback = nearby.getLocation().subtract(loc).toVector().normalize().multiply(1.5);
                    nearby.setVelocity(knockback);
                }
            }
        }
    }
}
