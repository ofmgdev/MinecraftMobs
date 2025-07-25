package me.ofmgdev.MinecraftMobsAddon.mobs;

import me.ofmgdev.MinecraftMobsAddon.effects.CustomBossBar;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomParticles;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomArmor;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomHorse;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import me.ofmgdev.MinecraftMobsAddon.ability.HelperSpawning;
import me.ofmgdev.MinecraftMobsAddon.ability.RapidArrowAbility;
import me.ofmgdev.MinecraftMobsAddon.ability.TeleportAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SkeletonArcherLord {

    private final JavaPlugin plugin;
    private LivingEntity skeleton;

    public SkeletonArcherLord(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LivingEntity spawn(Location location) {
        // יצירת הבוס
        CustomMob skeletonConfig = new CustomMob(EntityType.SKELETON, 100.0, 0.35, false, null, false);
        skeleton = skeletonConfig.spawn(location);

        // שם ולוק
        skeleton.customName(Component.text("Skeleton Archer Lord", NamedTextColor.DARK_PURPLE));
        skeleton.setCustomNameVisible(true);

        // הגדלת הבוס פי 2
        Objects.requireNonNull(skeleton.getAttribute(org.bukkit.attribute.Attribute.SCALE)).setBaseValue(1.35);

        // ציוד - ארמור זהב + קשת
        CustomArmor.applyArmorSet(skeleton, "gold");
        Objects.requireNonNull(skeleton.getEquipment()).setItemInMainHand(new org.bukkit.inventory.ItemStack(Material.BOW));

        // יכולות
        TeleportAbility.startTeleport(plugin, skeleton, 8, 10);
        RapidArrowAbility.startRapidArrows(plugin, skeleton, 0.75, 1.5);

        // 3 עוזרים רגילים - ציין + חרבות ברזל
        HelperSpawning.startHelperSpawning(plugin, skeleton, 2, 3, 3,
                EntityType.SKELETON, Material.IRON_SWORD, "Guard Warrior", HelperSpawning.SpawnMode.ALL_AT_ONCE,
                "chainmail", false, null, 30.0, 0.3, 15.0);

        // עוזר רכוב על סוס סקלטון - ארמור ברזל + חרב דיאמונד
        CustomHorse skeletonHorse = new CustomHorse(CustomHorse.HorseType.SKELETON, 45.0, 0.4, null);
        HelperSpawning.startHelperSpawning(plugin, skeleton, 10, 1, 1,
                EntityType.SKELETON, Material.DIAMOND_SWORD, "Elite Mounted Guard", HelperSpawning.SpawnMode.ALL_AT_ONCE,
                "iron", true, skeletonHorse, 60.0, 0.1, 15.0);

        // אפקט אש כחולה לסוס הרכוב
        plugin.getServer().getScheduler().runTaskLater(plugin, () ->
                        skeleton.getWorld().getLivingEntities().stream()
                                .filter(entity -> entity instanceof org.bukkit.entity.SkeletonHorse)
                                .filter(horse -> horse.getLocation().distance(skeleton.getLocation()) < 20)
                                .findFirst()
                                .ifPresent(horse -> CustomParticles.startBlueFireTrail(plugin, horse))
                , 100L);

        // אפקטים ובוס בר
        CustomParticles.startLightningAura(plugin, skeleton);
        CustomBossBar.createBossBar(plugin, skeleton, "§5§lSkeleton Archer Lord", BarColor.PURPLE);

        return skeleton;
    }

    public LivingEntity getSkeleton() {
        return skeleton;
    }
}