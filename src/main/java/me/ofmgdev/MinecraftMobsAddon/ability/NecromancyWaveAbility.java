package me.ofmgdev.MinecraftMobsAddon.ability;

import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomArmor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NecromancyWaveAbility {

    public static void startNecromancyWave(JavaPlugin plugin, LivingEntity entity, double intervalSeconds,
                                           int undeadCount, double radius, List<LivingEntity> minionList) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                // נקה מתים
                minionList.removeIf(minion -> !minion.isValid());

                if (entity instanceof Monster monster && monster.getTarget() != null && monster.getTarget().isValid()) {
                    performNecromancyWave(plugin, entity.getLocation(), undeadCount, radius, minionList);
                }
            }
        }.runTaskTimer(plugin, 140, (long)(intervalSeconds * 20));
    }

    private static void performNecromancyWave(JavaPlugin plugin, Location center, int undeadCount,
                                              double radius, List<LivingEntity> minionList) {

        // אפקטי מוות וחשכה
        center.getWorld().spawnParticle(Particle.LARGE_SMOKE, center.add(0, 2, 0), 100,
                radius, 3, radius, 0.1);
        center.getWorld().spawnParticle(Particle.SOUL, center, 80, radius, 2, radius, 0.05);

        // צלילי מוות
        center.getWorld().playSound(center, Sound.ENTITY_WITHER_SPAWN, 1.5f, 0.3f);
        center.getWorld().playSound(center, Sound.BLOCK_GRAVEL_BREAK, 2.0f, 0.5f);

        // עיכוב ואז עלייה מהקרקע
        new BukkitRunnable() {
            int risen = 0;

            @Override
            public void run() {
                if (risen >= undeadCount) {
                    cancel();
                    return;
                }

                // מיקום רנדומלי
                double angle = Math.random() * 2 * Math.PI;
                double distance = Math.random() * radius;
                double x = center.getX() + distance * Math.cos(angle);
                double z = center.getZ() + distance * Math.sin(angle);
                Location spawnLoc = new Location(center.getWorld(), x,
                        center.getWorld().getHighestBlockYAt((int)x, (int)z) + 1, z);

                // יצירת אנדד
                EntityType undeadType = Math.random() < 0.7 ? EntityType.SKELETON : EntityType.ZOMBIE;
                LivingEntity undead = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, undeadType);

                // הגדרות אנדד
                Objects.requireNonNull(undead.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).setBaseValue(40.0);
                undead.setHealth(40.0);
                Objects.requireNonNull(undead.getAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED)).setBaseValue(0.3);

                // ציוד מפחיד
                if (undead.getEquipment() != null) {
                    if (undeadType == EntityType.SKELETON) {
                        CustomArmor.applyArmorSet(undead, "iron");
                        undead.getEquipment().setItemInMainHand(new org.bukkit.inventory.ItemStack(Material.BOW));
                    } else {
                        CustomArmor.applyArmorSet(undead, "chainmail");
                        undead.getEquipment().setItemInMainHand(new org.bukkit.inventory.ItemStack(Material.IRON_SWORD));
                    }
                }

                undead.customName(Component.text("Risen Undead", NamedTextColor.DARK_GRAY));
                undead.setCustomNameVisible(true);

                // אפקטי קימה מהקרקע
                spawnLoc.getWorld().spawnParticle(Particle.BLOCK, spawnLoc, 30, 1, 1, 1, 0.1,
                        Material.DIRT.createBlockData());
                spawnLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, spawnLoc, 20, 1, 1, 1, 0.05);

                minionList.add(undead);
                risen++;
            }
        }.runTaskTimer(plugin, 60, 15); // התחלה אחרי 3 שניות, כל 0.75 שניות
    }
}
