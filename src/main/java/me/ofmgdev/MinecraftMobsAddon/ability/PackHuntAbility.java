package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class PackHuntAbility {

    private static final double BASE_DAMAGE = 12.0;
    private static final double BASE_SPEED = 0.4;
    private static final double DAMAGE_BONUS_PER_WOLF = 2.0;
    private static final double SPEED_BONUS_PER_WOLF = 0.05;

    public static void startPackHunt(JavaPlugin plugin, LivingEntity alpha, List<LivingEntity> packList) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!alpha.isValid()) {
                    cancel();
                    return;
                }

                // נקה זאבים מתים
                packList.removeIf(wolf -> !wolf.isValid());

                // חשב כמה זאבים חיים יש ברדיוס 15 בלוקים
                int nearbyWolves = countNearbyPackWolves(alpha, packList, 15.0);

                // עדכן יכולות לפי גודל החבורה
                updatePackBonuses(alpha, nearbyWolves);
            }
        }.runTaskTimer(plugin, 0, 20); // כל שנייה
    }

    private static int countNearbyPackWolves(LivingEntity alpha, List<LivingEntity> packList, double radius) {
        int count = 0;
        for (LivingEntity wolf : packList) {
            if (wolf.isValid() && wolf.getLocation().distance(alpha.getLocation()) <= radius) {
                count++;
            }
        }
        return count;
    }

    private static void updatePackBonuses(LivingEntity alpha, int nearbyWolves) {
        // חשב bonuses
        double newDamage = BASE_DAMAGE + (nearbyWolves * DAMAGE_BONUS_PER_WOLF);
        double newSpeed = BASE_SPEED + (nearbyWolves * SPEED_BONUS_PER_WOLF);

        // הגבל מקסימום
        newDamage = Math.min(newDamage, 24.0); // מקסימום 24 נזק
        newSpeed = Math.min(newSpeed, 0.8); // מקסימום 0.8 מהירות

        // עדכן attributes
        Objects.requireNonNull(alpha.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(newDamage);
        Objects.requireNonNull(alpha.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(newSpeed);

        // אפקט ויזואלי אם יש חבורה גדולה
        if (nearbyWolves >= 3) {
            alpha.getWorld().spawnParticle(org.bukkit.Particle.ANGRY_VILLAGER,
                    alpha.getLocation().add(0, 2, 0), 5, 0.5, 0.5, 0.5, 0);
        }
    }
}