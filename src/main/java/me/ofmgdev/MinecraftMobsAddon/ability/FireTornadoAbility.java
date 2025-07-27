package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FireTornadoAbility {

    public static void startFireTornado(JavaPlugin plugin, LivingEntity entity, double intervalSeconds, double radius, double duration) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                if (entity instanceof Monster monster && monster.getTarget() != null && monster.getTarget().isValid()) {
                    Location tornadoCenter = monster.getTarget().getLocation();
                    createFireTornado(plugin, tornadoCenter, radius, duration);
                }
            }
        }.runTaskTimer(plugin, 120, (long)(intervalSeconds * 20));
    }

    private static void createFireTornado(JavaPlugin plugin, Location center, double radius, double duration) {
        center.getWorld().playSound(center, Sound.ENTITY_BLAZE_SHOOT, 2.0f, 0.3f);

        new BukkitRunnable() {
            double angle = 0;
            int ticks = 0;
            final int maxTicks = (int)(duration * 20);

            @Override
            public void run() {
                if (ticks >= maxTicks) {
                    cancel();
                    return;
                }

                // יצירת ספירלת אש
                for (int height = 0; height <= 8; height++) {
                    double currentRadius = radius * (1.0 - height * 0.1);

                    for (int i = 0; i < 8; i++) {
                        double currentAngle = angle + (i * Math.PI / 4);
                        double x = center.getX() + currentRadius * Math.cos(currentAngle);
                        double z = center.getZ() + currentRadius * Math.sin(currentAngle);
                        Location particleLoc = new Location(center.getWorld(), x, center.getY() + height, z);

                        // אפקטי אש מסתובבים
                        particleLoc.getWorld().spawnParticle(Particle.FLAME, particleLoc, 3, 0.1, 0.1, 0.1, 0.05);
                        particleLoc.getWorld().spawnParticle(Particle.LARGE_SMOKE, particleLoc, 2, 0.1, 0.1, 0.1, 0.02);

                        if (height <= 2) {
                            particleLoc.getWorld().spawnParticle(Particle.LAVA, particleLoc, 1, 0.1, 0.1, 0.1, 0);
                        }
                    }
                }

                // נזק לישויות בתוך הטורנדו
                if (ticks % 10 == 0) { // כל חצי שנייה
                    for (LivingEntity nearby : center.getWorld().getLivingEntities()) {
                        double distance = nearby.getLocation().distance(center);
                        if (distance <= radius) {
                            if (nearby instanceof org.bukkit.entity.Player || nearby instanceof org.bukkit.entity.Villager) {
                                nearby.damage(8.0);
                                nearby.setFireTicks(60); // 3 שניות אש

                                // משיכה לתוך הטורנדו
                                org.bukkit.util.Vector pullVector = center.subtract(nearby.getLocation()).toVector().normalize().multiply(0.3);
                                pullVector.setY(0.1); // הרמה קלה
                                nearby.setVelocity(pullVector);
                            }
                        }
                    }
                }

                angle += 0.3; // מהירות סיבוב
                ticks++;
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
