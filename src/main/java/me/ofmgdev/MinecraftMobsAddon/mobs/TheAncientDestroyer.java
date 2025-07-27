package me.ofmgdev.MinecraftMobsAddon.mobs;

import me.ofmgdev.MinecraftMobsAddon.ability.*;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomBossBar;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TheAncientDestroyer {

    private final JavaPlugin plugin;
    private LivingEntity destroyer;
    private final List<LivingEntity> undeadMinions = new ArrayList<>();
    private final List<LivingEntity> shieldMinions = new ArrayList<>();

    public TheAncientDestroyer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LivingEntity spawn(Location location) {
        // יצירת הבוס - Wither Skeleton ענק
        CustomMob destroyerConfig = new CustomMob(EntityType.WITHER_SKELETON, 500.0, 0.2, false, null, false);
        destroyer = destroyerConfig.spawn(location);

        // שם ומראה מפחיד
        destroyer.customName(Component.text("The Ancient Destroyer", NamedTextColor.DARK_PURPLE));
        destroyer.setCustomNameVisible(true);

        // הגדלה אדירה פי 6!
        Objects.requireNonNull(destroyer.getAttribute(org.bukkit.attribute.Attribute.SCALE)).setBaseValue(6.0);

        // נזק הרסני
        Objects.requireNonNull(destroyer.getAttribute(org.bukkit.attribute.Attribute.ATTACK_DAMAGE)).setBaseValue(25.0);

        // ציוד מפחיד
        if (destroyer.getEquipment() != null) {
            destroyer.getEquipment().setItemInMainHand(new org.bukkit.inventory.ItemStack(Material.NETHERITE_SWORD));
            destroyer.getEquipment().setHelmet(new org.bukkit.inventory.ItemStack(Material.WITHER_SKELETON_SKULL));
        }

        // יכולות הרסניות
        NecromancyWaveAbility.startNecromancyWave(plugin, destroyer, 18.0, 6, 20.0, undeadMinions); // כל 18 שניות, 6 אנדד, רדיוס 20
        SoulDrainAbility.startSoulDrain(plugin, destroyer, 12.0, 15.0, 8.0); // כל 12 שניות, רדיוס 15, נזק 8
        DeathBeamAbility.startDeathBeam(plugin, destroyer, 25.0, 30.0, 35.0); // כל 25 שניות, רדיוס 30, נזק 35

        // מערכת הגנה עם עוזרים
        HelperSpawning.startHelperSpawning(plugin, destroyer, 15, 2, 4,
                EntityType.WITHER_SKELETON, Material.NETHERITE_SWORD, "Dark Guardian",
                HelperSpawning.SpawnMode.ALL_AT_ONCE, "netherite", false, null, 80.0, 0.25, 20.0);

        // Final Rage מודולרי
        FinalRageAbility.startFinalRage(plugin, destroyer, 0.2, 2.0, 1.6,
                "§4§l§n>>> THE ANCIENT DESTROYER ENTERS FINAL RAGE! <<<", undeadMinions);

        // Boss bar מפחיד
        CustomBossBar.createBossBar(plugin, destroyer, "§5§l§nTHE ANCIENT DESTROYER§r §4§l[INDESTRUCTIBLE]", BarColor.PURPLE);

        return destroyer;
    }

    public LivingEntity getDestroyer() {
        return destroyer;
    }
}


