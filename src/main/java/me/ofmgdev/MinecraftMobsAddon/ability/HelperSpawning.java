package me.ofmgdev.MinecraftMobsAddon.ability;

import me.ofmgdev.MinecraftMobsAddon.auxiliaryfunctions.GuardLeash;
import me.ofmgdev.MinecraftMobsAddon.auxiliaryfunctions.MountedMob;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomArmor;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomHorse;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HelperSpawning {

    public enum SpawnMode {
        ALL_AT_ONCE,
        SPREAD_OVER_TIME
    }

    public static void startHelperSpawning(JavaPlugin plugin, LivingEntity boss,
                                           int intervalSeconds, int helpersPerWave, int maxAliveHelpers,
                                           EntityType helperType, Material weapon, String helperName,
                                           SpawnMode mode, String armorType, boolean mounted,
                                           CustomHorse horseConfig, double helperHealth, double helperSpeed) {

        List<LivingEntity> aliveHelpers = new ArrayList<>();
        GuardLeash.startLeash(plugin, boss, aliveHelpers, 15.0);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!boss.isValid()) {
                    cancel();
                    return;
                }

                aliveHelpers.removeIf(helper -> !helper.isValid());

                int canSpawn = maxAliveHelpers - aliveHelpers.size();
                if (canSpawn <= 0) return;

                int toSpawn = Math.min(helpersPerWave, canSpawn);

                if (mode == SpawnMode.ALL_AT_ONCE) {
                    spawnHelpersAtOnce(plugin, boss, toSpawn, helperType, weapon,
                            helperName, aliveHelpers, armorType, mounted, horseConfig, helperHealth, helperSpeed);
                } else {
                    spawnHelpersOverTime(plugin, boss, toSpawn, helperType, weapon,
                            helperName, aliveHelpers, armorType, mounted, horseConfig, helperHealth, helperSpeed);
                }
            }
        }.runTaskTimer(plugin, 60, intervalSeconds * 20L);
    }

    private static void spawnHelpersAtOnce(JavaPlugin plugin, LivingEntity boss, int count,
                                           EntityType type, Material weapon, String name,
                                           List<LivingEntity> aliveHelpers, String armorType,
                                           boolean mounted, CustomHorse horseConfig, double helperHealth, double helperSpeed) {

        for (int i = 0; i < count; i++) {
            LivingEntity helper = createSingleHelper(plugin, boss, type, weapon, name,
                    armorType, mounted, horseConfig, helperHealth, helperSpeed);
            if (helper != null) {
                aliveHelpers.add(helper);
            }
        }
    }

    private static void spawnHelpersOverTime(JavaPlugin plugin, LivingEntity boss, int count,
                                             EntityType type, Material weapon, String name,
                                             List<LivingEntity> aliveHelpers, String armorType,
                                             boolean mounted, CustomHorse horseConfig, double helperHealth, double helperSpeed) {

        new BukkitRunnable() {
            int spawned = 0;

            @Override
            public void run() {
                if (!boss.isValid() || spawned >= count) {
                    cancel();
                    return;
                }

                LivingEntity helper = createSingleHelper(plugin, boss, type, weapon, name,
                        armorType, mounted, horseConfig, helperHealth, helperSpeed);
                if (helper != null) {
                    aliveHelpers.add(helper);
                    spawned++;
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private static LivingEntity createSingleHelper(JavaPlugin plugin, LivingEntity boss,
                                                   EntityType type, Material weapon, String name,
                                                   String armorType, boolean mounted,
                                                   CustomHorse horseConfig, double helperHealth, double helperSpeed) {

        Location helperLoc = boss.getLocation().clone();
        helperLoc.add((Math.random() - 0.5) * 8, 0, (Math.random() - 0.5) * 8);

        LivingEntity helper;

        if (mounted && horseConfig != null) {
            helper = createMountedHelper(plugin, helperLoc, type, weapon, name, armorType, horseConfig, helperHealth, helperSpeed);
        } else {
            helper = createRegularHelper(helperLoc, type, weapon, name, armorType, helperHealth, helperSpeed);
        }

        if (helper != null) {
            helperLoc.getWorld().spawnParticle(Particle.SMOKE, helperLoc, 30, 1, 1, 1, 0.1);
        }

        return helper;
    }

    private static LivingEntity createRegularHelper(Location location, EntityType type,
                                                    Material weapon, String name, String armorType, double helperHealth, double helperSpeed) {

        CustomMob helperConfig = new CustomMob(type, helperHealth, helperSpeed, false, null, false);
        LivingEntity helper = helperConfig.spawn(location);

        if (helper != null && helper.getEquipment() != null) {
            helper.getEquipment().setItemInMainHand(new ItemStack(weapon));
            CustomArmor.applyArmorSet(helper, armorType);
            helper.customName(Component.text(name, NamedTextColor.GRAY));
            helper.setCustomNameVisible(true);
        }

        return helper;
    }

    private static LivingEntity createMountedHelper(JavaPlugin plugin, Location location,
                                                    EntityType type, Material weapon, String name,
                                                    String armorType, CustomHorse horseConfig, double helperHealth, double helperSpeed) {

        CustomMob riderConfig = new CustomMob(type, helperHealth, helperSpeed, false, null, false);
        MountedMob mountedUnit = new MountedMob(plugin, horseConfig, riderConfig);
        LivingEntity rider = mountedUnit.spawn(location);

        if (rider != null && rider.getEquipment() != null) {
            rider.getEquipment().setItemInMainHand(new ItemStack(weapon));
            CustomArmor.applyArmorSet(rider, armorType);
            rider.customName(Component.text(name, NamedTextColor.GOLD));
            rider.setCustomNameVisible(true);
        }

        return rider;
    }
}