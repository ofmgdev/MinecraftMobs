package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PoisonAttackAbility {

    public static void startPoisonAttacks(JavaPlugin plugin, LivingEntity entity, double intervalSeconds) {
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

                    // תקיפת רעל במרחק עד 8 בלוקים
                    if (distance <= 8.0) {
                        target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1)); // 5 שניות רעל
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 0)); // 3 שניות איטיות
                    }
                }
            }
        }.runTaskTimer(plugin, 20, (long)(intervalSeconds * 20));
    }
}
