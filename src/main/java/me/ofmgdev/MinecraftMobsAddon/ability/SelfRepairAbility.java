package me.ofmgdev.MinecraftMobsAddon.ability;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class SelfRepairAbility {

    public static void startSelfRepair(JavaPlugin plugin, LivingEntity entity, double intervalSeconds, double healAmount, double healthThreshold) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                double maxHealth = Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();
                double currentHealth = entity.getHealth();
                double healthPercentage = currentHealth / maxHealth;

                // רק אם הבריאות מתחת לסף
                if (healthPercentage <= healthThreshold) {
                    performSelfRepair(entity, healAmount);
                }
            }
        }.runTaskTimer(plugin, 100, (long)(intervalSeconds * 20));
    }

    private static void performSelfRepair(LivingEntity entity, double healAmount) {
        Location loc = entity.getLocation();

        // ריפוי
        double newHealth = Math.min(entity.getHealth() + healAmount,
                Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue());
        entity.setHealth(newHealth);

        // אפקטי ריפוי
        loc.getWorld().spawnParticle(Particle.HEART, loc.add(0, 2, 0), 15, 1, 1, 1, 0);
        loc.getWorld().spawnParticle(Particle.ENCHANT, loc, 30, 2, 2, 2, 1);
        loc.getWorld().spawnParticle(Particle.BLOCK, loc, 20, 1, 1, 1, 0.1,
                org.bukkit.Material.IRON_BLOCK.createBlockData());

        // צליל מתכת מתחזקת
        loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_USE, 1.0f, 1.2f);
        loc.getWorld().playSound(loc, Sound.ENTITY_IRON_GOLEM_REPAIR, 1.5f, 1.0f);
    }
}
