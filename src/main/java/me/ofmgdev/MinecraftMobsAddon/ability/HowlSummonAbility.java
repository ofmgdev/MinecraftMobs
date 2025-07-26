package me.ofmgdev.MinecraftMobsAddon.ability;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HowlSummonAbility {

    public static void startHowlSummon(JavaPlugin plugin, LivingEntity entity, double intervalSeconds,
                                       int wolvesPerHowl, int maxWolves, List<LivingEntity> packList) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                // נקה זאבים מתים
                packList.removeIf(wolf -> !wolf.isValid());

                // בדוק אם צריך לזמן עוד זאבים
                if (packList.size() < maxWolves) {
                    performHowl(entity, wolvesPerHowl, maxWolves - packList.size(), packList);
                }
            }
        }.runTaskTimer(plugin, 80, (long)(intervalSeconds * 20));
    }

    private static void performHowl(LivingEntity alpha, int wolvesToSummon, int canSummon, List<LivingEntity> packList) {
        Location loc = alpha.getLocation();

        // אפקטי יללה
        loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc.add(0, 1, 0), 30, 2, 1, 2, 0.1);
        loc.getWorld().spawnParticle(Particle.SMOKE, loc, 50, 3, 2, 3, 0.05);

        // צליל יללה חזק
        loc.getWorld().playSound(loc, Sound.ENTITY_WOLF_AMBIENT, 2.0f, 0.6f);

        // זימון זאבים
        int toSummon = Math.min(wolvesToSummon, canSummon);

        for (int i = 0; i < toSummon; i++) {
            Location wolfLoc = loc.clone().add((Math.random() - 0.5) * 8, 0, (Math.random() - 0.5) * 8);
            wolfLoc.setY(wolfLoc.getWorld().getHighestBlockYAt(wolfLoc) + 1);

            Wolf wolf = (Wolf) wolfLoc.getWorld().spawnEntity(wolfLoc, EntityType.WOLF);

            // הגדרות זאב
            wolf.setAngry(true);
            wolf.setCollarColor(DyeColor.RED);
            Objects.requireNonNull(wolf.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).setBaseValue(25.0);
            wolf.setHealth(25.0);
            Objects.requireNonNull(wolf.getAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED)).setBaseValue(0.35);

            wolf.customName(Component.text("Pack Wolf", NamedTextColor.DARK_RED));
            wolf.setCustomNameVisible(true);

            // העבר target מהאלפא לזאב
            if (alpha instanceof Monster alphaMonster && alphaMonster.getTarget() != null) {
                if (wolf instanceof Monster wolfMonster) {
                    wolfMonster.setTarget(alphaMonster.getTarget());
                }
            }

            packList.add(wolf);

            // אפקט זימון
            wolfLoc.getWorld().spawnParticle(Particle.PORTAL, wolfLoc, 20, 1, 1, 1, 0.1);
        }
    }
}
