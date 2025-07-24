package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

public class RapidArrowAbility {

    public static void startRapidArrows(JavaPlugin plugin, LivingEntity entity, double intervalSeconds, double speedMultiplier) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                if (entity instanceof Monster monster && monster.getTarget() != null && monster.getTarget().isValid()) {
                    LivingEntity target = monster.getTarget();

                    // בדיקה שלא תוקף חברים (בעלי שם דומה)
                    if (target.customName() != null && entity.customName() != null &&
                            Objects.requireNonNull(target.customName()).toString().contains("Guard") && Objects.requireNonNull(entity.customName()).toString().contains("Guard")) {
                        return; // לא לתקוף חברים
                    }

                    Arrow arrow = entity.launchProjectile(Arrow.class);
                    Vector direction = target.getLocation().subtract(entity.getLocation()).toVector().normalize();
                    arrow.setVelocity(direction.multiply(speedMultiplier * 2));
                }
            }
        }.runTaskTimer(plugin, 20, (long)(intervalSeconds * 20));
    }
}