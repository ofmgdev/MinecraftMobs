package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DeathBeamAbility {

    public static void startDeathBeam(JavaPlugin plugin, LivingEntity entity, double intervalSeconds,
                                      double beamRange, double beamDamage) {
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

                    if (distance <= beamRange) {
                        fireDeathBeam(plugin, entity, target, beamDamage);
                    }
                }
            }
        }.runTaskTimer(plugin, 180, (long)(intervalSeconds * 20));
    }

    private static void fireDeathBeam(JavaPlugin plugin, LivingEntity shooter, LivingEntity target, double damage) {
        Location shooterLoc = shooter.getLocation().add(0, 1, 0);
        Location targetLoc = target.getLocation().add(0, 1, 0);

        // שלב 1: אזהרה (3 שניות)
        warningPhase(plugin, shooterLoc, targetLoc);

        // שלב 2: ירי הקרן (אחרי 3 שניות)
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!shooter.isValid() || !target.isValid()) return;

                executeDeathBeam(plugin, shooterLoc, targetLoc, target, damage);
            }
        }.runTaskLater(plugin, 60); // 3 שניות אזהרה
    }

    private static void warningPhase(JavaPlugin plugin, Location from, Location to) {
        from.getWorld().playSound(from, Sound.ENTITY_WITHER_SHOOT, 2.0f, 0.3f);

        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = 60; // 3 שניות

            @Override
            public void run() {
                if (ticks >= maxTicks) {
                    cancel();
                    return;
                }

                // אפקטי אזהרה אדומים
                createBeamLine(from, to, Particle.CRIMSON_SPORE, 2);
                createBeamLine(from, to, Particle.ANGRY_VILLAGER, 1);

                // צליל אזהרה הולך וגובר
                if (ticks % 20 == 0) {
                    float pitch = 0.5f + (ticks / 60.0f) * 0.8f;
                    from.getWorld().playSound(from, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, pitch);
                }

                ticks++;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private static void executeDeathBeam(JavaPlugin plugin, Location from, Location to, LivingEntity target, double damage) {
        // צליל ירי עוצמתי
        from.getWorld().playSound(from, Sound.ENTITY_WITHER_SHOOT, 3.0f, 1.5f);
        from.getWorld().playSound(from, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.5f, 1.8f);

        // יצירת הקרן הקטלנית
        new BukkitRunnable() {
            int intensity = 10;

            @Override
            public void run() {
                if (intensity <= 0) {
                    // פיצוץ סופי
                    to.getWorld().spawnParticle(Particle.EXPLOSION, to, 15, 2, 2, 2, 0);
                    to.getWorld().playSound(to, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.0f);
                    cancel();
                    return;
                }

                // קרן סגולה עוצמתית
                createBeamLine(from, to, Particle.SOUL_FIRE_FLAME, intensity);
                createBeamLine(from, to, Particle.END_ROD, intensity / 2);
                createBeamLine(from, to, Particle.ELECTRIC_SPARK, intensity / 3);

                intensity--;
            }
        }.runTaskTimer(plugin, 0, 2); // כל 2 טיקים לאפקט עוצמתי

        // נזק והשפעות
        applyBeamDamage(target, damage);
    }

    private static void createBeamLine(Location from, Location to, Particle particle, int density) {
        Vector direction = to.toVector().subtract(from.toVector()).normalize();
        double distance = from.distance(to);

        for (double i = 0; i <= distance; i += 0.5) {
            Location point = from.clone().add(direction.clone().multiply(i));
            point.getWorld().spawnParticle(particle, point, density, 0.1, 0.1, 0.1, 0);
        }
    }

    private static void applyBeamDamage(LivingEntity target, double damage) {
        // נזק עיקרי
        target.damage(damage);

        // דחיפה חזקה
        Vector knockback = target.getVelocity().clone();
        knockback.setY(0.8); // דחיפה למעלה
        knockback.multiply(2.0); // דחיפה חזקה הצידה
        target.setVelocity(knockback);

        // אפקטי מוות זמניים
        target.addPotionEffect(new org.bukkit.potion.PotionEffect(
                org.bukkit.potion.PotionEffectType.BLINDNESS, 60, 0)); // 3 שניות עיוורון
        target.addPotionEffect(new org.bukkit.potion.PotionEffect(
                org.bukkit.potion.PotionEffectType.SLOWNESS, 100, 2)); // 5 שניות איטיות חזקה
    }
}