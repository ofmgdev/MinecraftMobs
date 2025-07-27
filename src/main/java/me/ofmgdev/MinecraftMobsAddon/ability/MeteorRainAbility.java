package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MeteorRainAbility {

    public static void startMeteorRain(JavaPlugin plugin, LivingEntity entity, double intervalSeconds, int meteorCount, double radius) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                if (entity instanceof Monster monster && monster.getTarget() != null && monster.getTarget().isValid()) {
                    Location targetArea = monster.getTarget().getLocation();
                    performMeteorRain(plugin, targetArea, meteorCount, radius);
                }
            }
        }.runTaskTimer(plugin, 100, (long)(intervalSeconds * 20));
    }

    private static void performMeteorRain(JavaPlugin plugin, Location center, int meteorCount, double radius) {
        // אפקט אזהרה
        center.getWorld().spawnParticle(Particle.LARGE_SMOKE, center.add(0, 10, 0), 50, radius, 5, radius, 0.1);
        center.getWorld().playSound(center, Sound.ENTITY_BLAZE_AMBIENT, 2.0f, 0.5f);

        // השהיה ואז מטאורים
        new BukkitRunnable() {
            int meteorsDropped = 0;

            @Override
            public void run() {
                if (meteorsDropped >= meteorCount) {
                    cancel();
                    return;
                }

                // מיקום רנדומלי באזור
                double x = center.getX() + (Math.random() - 0.5) * radius * 2;
                double z = center.getZ() + (Math.random() - 0.5) * radius * 2;
                Location meteorLoc = new Location(center.getWorld(), x, center.getY() + 20, z);

                // יצירת מטאור
                createMeteor(plugin, meteorLoc);
                meteorsDropped++;
            }
        }.runTaskTimer(plugin, 40, 10); // התחלה אחרי 2 שניות, מטאור כל חצי שנייה
    }

    private static void createMeteor(JavaPlugin plugin, Location startLoc) {
        new BukkitRunnable() {
            Location currentLoc = startLoc.clone();
            final Location groundLoc = new Location(startLoc.getWorld(), startLoc.getX(),
                    startLoc.getWorld().getHighestBlockYAt(startLoc), startLoc.getZ());

            @Override
            public void run() {
                if (currentLoc.getY() <= groundLoc.getY()) {
                    // פגיעה בקרקע
                    meteorImpact(groundLoc);
                    cancel();
                    return;
                }

                // אפקטי נפילה
                currentLoc.getWorld().spawnParticle(Particle.FLAME, currentLoc, 15, 0.5, 0.5, 0.5, 0.1);
                currentLoc.getWorld().spawnParticle(Particle.LAVA, currentLoc, 5, 0.3, 0.3, 0.3, 0);

                currentLoc.subtract(0, 2, 0); // נפילה
            }
        }.runTaskTimer(plugin, 0, 1); // כל טיק
    }

    private static void meteorImpact(Location impactLoc) {
        // פיצוץ אש
        impactLoc.getWorld().spawnParticle(Particle.EXPLOSION, impactLoc, 10, 2, 1, 2, 0);
        impactLoc.getWorld().spawnParticle(Particle.FLAME, impactLoc, 50, 3, 2, 3, 0.1);
        impactLoc.getWorld().spawnParticle(Particle.LAVA, impactLoc, 30, 2, 1, 2, 0);

        impactLoc.getWorld().playSound(impactLoc, Sound.ENTITY_GENERIC_EXPLODE, 1.5f, 0.8f);

        // נזק לישויות קרובות
        for (LivingEntity nearby : impactLoc.getWorld().getLivingEntities()) {
            if (nearby.getLocation().distance(impactLoc) <= 4.0) {
                if (nearby instanceof org.bukkit.entity.Player || nearby instanceof org.bukkit.entity.Villager) {
                    nearby.damage(15.0);
                    nearby.setFireTicks(100); // 5 שניות אש
                }
            }
        }

        // יצירת אש על הקרקע
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                Location fireLoc = impactLoc.clone().add(x, 0, z);
                if (fireLoc.getBlock().getType() == Material.AIR) {
                    fireLoc.getBlock().setType(Material.FIRE);
                }
            }
        }
    }
}