package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class SoulDrainAbility {

    public static void startSoulDrain(JavaPlugin plugin, LivingEntity entity, double intervalSeconds,
                                      double drainRadius, double drainAmount) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                if (entity instanceof Monster monster && monster.getTarget() != null && monster.getTarget().isValid()) {
                    performSoulDrain(entity, drainRadius, drainAmount);
                }
            }
        }.runTaskTimer(plugin, 100, (long)(intervalSeconds * 20));
    }

    private static void performSoulDrain(LivingEntity drainer, double radius, double drainAmount) {
        Location center = drainer.getLocation();
        double totalDrained = 0;

        // אפקטי התחלה
        center.getWorld().spawnParticle(Particle.SOUL, center.add(0, 1, 0), 50,
                radius, 2, radius, 0.1);
        center.getWorld().playSound(center, Sound.ENTITY_PHANTOM_AMBIENT, 1.5f, 0.7f);

        // גזול נשמות משחקנים
        for (org.bukkit.entity.Player player : center.getWorld().getPlayers()) {
            double distance = player.getLocation().distance(center);

            if (distance <= radius) {
                // חישוב נזק לפי מרחק
                double actualDrain = drainAmount * (1.0 - distance / radius); // פחות נזק ככל שרחוק יותר

                // גזול בריאות
                if (player.getHealth() > actualDrain) {
                    player.damage(actualDrain);
                    totalDrained += actualDrain;
                } else {
                    totalDrained += player.getHealth() - 1; // השאר אותו עם 1 HP
                    player.setHealth(1);
                }

                // אפקטי גזילה על השחקן
                Location playerLoc = player.getLocation();
                playerLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, playerLoc.add(0, 1, 0),
                        15, 0.5, 1, 0.5, 0.05);

                // חולשה זמנית
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1)); // 5 שניות
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 0)); // 4 שניות

                // קו של נשמות בין השחקן לבוס
                createSoulBeam(playerLoc, center);
            }
        }

        // הבוס מקבל את הבריאות שנגזלה
        if (totalDrained > 0) {
            double maxHealth = Objects.requireNonNull(drainer.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();
            double newHealth = Math.min(drainer.getHealth() + totalDrained * 0.8, maxHealth); // 80% מהנזק חוזר כריפוי
            drainer.setHealth(newHealth);

            // אפקטי ריפוי על הבוס
            center.getWorld().spawnParticle(Particle.HEART, center.add(0, 2, 0),
                    (int)totalDrained, 1, 1, 1, 0);
            center.getWorld().playSound(center, Sound.ENTITY_WITCH_DRINK, 1.0f, 1.2f);
        }
    }

    private static void createSoulBeam(Location from, Location to) {
        new BukkitRunnable() {
            double progress = 0;
            final int steps = 20;

            @Override
            public void run() {
                if (progress >= 1.0) {
                    cancel();
                    return;
                }

                // חישוב נקודה ביניים
                double x = from.getX() + (to.getX() - from.getX()) * progress;
                double y = from.getY() + (to.getY() - from.getY()) * progress + 1;
                double z = from.getZ() + (to.getZ() - from.getZ()) * progress;
                Location beamPoint = new Location(from.getWorld(), x, y, z);

                // אפקטי קרן נשמות
                beamPoint.getWorld().spawnParticle(Particle.SOUL, beamPoint, 3, 0.1, 0.1, 0.1, 0);
                beamPoint.getWorld().spawnParticle(Particle.ENCHANT, beamPoint, 2, 0.1, 0.1, 0.1, 0.5);

                progress += 1.0 / steps;
            }
        }.runTaskTimer(org.bukkit.Bukkit.getPluginManager().getPlugin("MinecraftMobsAddon"), 0, 1);
    }
}
