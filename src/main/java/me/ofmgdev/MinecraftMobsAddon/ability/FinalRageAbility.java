package me.ofmgdev.MinecraftMobsAddon.ability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class FinalRageAbility {

    public static void startFinalRage(JavaPlugin plugin, LivingEntity entity, double healthThreshold,
                                      double speedMultiplier, double damageMultiplier, String rageMessage,
                                      List<LivingEntity> minionList) {

        new BukkitRunnable() {
            boolean rageActivated = false;

            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                double healthPercent = entity.getHealth() / Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();

                if (healthPercent <= healthThreshold && !rageActivated) {
                    activateRage(plugin, entity, speedMultiplier, damageMultiplier, rageMessage, minionList);
                    rageActivated = true;
                }
            }
        }.runTaskTimer(plugin, 20, 20); // כל שנייה
    }

    private static void activateRage(JavaPlugin plugin, LivingEntity entity, double speedMultiplier,
                                     double damageMultiplier, String rageMessage, List<LivingEntity> minionList) {
        Location loc = entity.getLocation();

        // הודעה לשחקנים
        for (org.bukkit.entity.Player player : loc.getWorld().getPlayers()) {
            player.sendMessage(Component.text(rageMessage, NamedTextColor.DARK_RED));
            player.sendTitle("§4FINAL RAGE", "§cThe end is near...", 10, 60, 20);
        }

        // אפקטים עוצמתיים
        loc.getWorld().spawnParticle(org.bukkit.Particle.EXPLOSION, loc, 50, 10, 5, 10, 0);
        loc.getWorld().spawnParticle(org.bukkit.Particle.SOUL_FIRE_FLAME, loc, 100, 8, 8, 8, 0.1);
        loc.getWorld().playSound(loc, org.bukkit.Sound.ENTITY_WITHER_SPAWN, 3.0f, 0.3f);

        // שדרוגי יכולות
        double currentSpeed = Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED)).getBaseValue();
        double currentDamage = Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.ATTACK_DAMAGE)).getBaseValue();

        Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED)).setBaseValue(currentSpeed * speedMultiplier);
        Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.ATTACK_DAMAGE)).setBaseValue(currentDamage * damageMultiplier);

        // יכולות Final Rage
        startRageAbilities(plugin, entity, minionList);
    }

    private static void startRageAbilities(JavaPlugin plugin, LivingEntity entity, List<LivingEntity> minionList) {
        // Soul Drain מהיר וחזק יותר
        SoulDrainAbility.startSoulDrain(plugin, entity, 6.0, 20.0, 12.0);

        // Death Beam מהיר יותר
        DeathBeamAbility.startDeathBeam(plugin, entity, 15.0, 35.0, 50.0);

        // זימון רציף של אנדד
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }
                NecromancyWaveAbility.startNecromancyWave(plugin, entity, 8.0, 4, 15.0, minionList);
            }
        }.runTaskTimer(plugin, 0, 160);

        // אורה של מוות מתמיד
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                Location loc = entity.getLocation();

                // אפקטי מוות קבועים
                loc.getWorld().spawnParticle(org.bukkit.Particle.SOUL_FIRE_FLAME, loc.add(0, 3, 0), 30, 6, 3, 6, 0.1);
                loc.getWorld().spawnParticle(org.bukkit.Particle.LARGE_SMOKE, loc, 20, 5, 2, 5, 0.05);

                // נזק קבוע לשחקנים קרובים
                for (org.bukkit.entity.Player player : loc.getWorld().getPlayers()) {
                    if (player.getLocation().distance(loc) <= 12.0) {
                        player.damage(2.0);
                        player.addPotionEffect(new org.bukkit.potion.PotionEffect(
                                org.bukkit.potion.PotionEffectType.WITHER, 60, 1));
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
