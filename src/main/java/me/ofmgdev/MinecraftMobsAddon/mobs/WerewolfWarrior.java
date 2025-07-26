package me.ofmgdev.MinecraftMobsAddon.mobs;

import me.ofmgdev.MinecraftMobsAddon.ability.HowlSummonAbility;
import me.ofmgdev.MinecraftMobsAddon.ability.PackHuntAbility;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomBossBar;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomParticles;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WerewolfWarrior {

    private final JavaPlugin plugin;
    private LivingEntity werewolf;
    private final List<LivingEntity> packWolves = new ArrayList<>();

    public WerewolfWarrior(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LivingEntity spawn(Location location) {
        // יצירת הבוס - זומבי עם ראש זאב
        CustomMob werewolfConfig = new CustomMob(EntityType.WOLF, 120.0, 0.4, false, null, false);
        werewolf = werewolfConfig.spawn(location);

        // שם ולוק
        werewolf.customName(Component.text("Alpha Werewolf", NamedTextColor.DARK_RED));
        werewolf.setCustomNameVisible(true);

        // הגדלת הזאב פי 2
        Objects.requireNonNull(werewolf.getAttribute(org.bukkit.attribute.Attribute.SCALE)).setBaseValue(4.0);

        // הגדרת זאב כועס ותוקפני
        if (werewolf instanceof org.bukkit.entity.Wolf wolf) {
            wolf.setAngry(true);
            wolf.setTamed(false);
            startAlphaAggression(plugin, wolf);
        }

        // נזק פיזי חזק
        Objects.requireNonNull(werewolf.getAttribute(org.bukkit.attribute.Attribute.ATTACK_DAMAGE)).setBaseValue(10.0);

        // יכולות
        HowlSummonAbility.startHowlSummon(plugin, werewolf, 8.0, 2, 6, packWolves); // כל 8 שניות, 2 זאבים, מקסימום 6
        PackHuntAbility.startPackHunt(plugin, werewolf, packWolves);

        // אפקטים ובוס בר
        CustomParticles.startFearAura(plugin, werewolf);
        CustomBossBar.createBossBar(plugin, werewolf, "§4§lAlpha Werewolf", BarColor.RED);

        return werewolf;
    }

    public LivingEntity getWerewolf() {
        return werewolf;
    }

    public List<LivingEntity> getPackWolves() {
        return packWolves;
    }

    private void startAlphaAggression(JavaPlugin plugin, org.bukkit.entity.Wolf alpha) {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                if (!alpha.isValid()) {
                    cancel();
                    return;
                }
                // תקיפה אוטומטית של שחקנים קרובים
                if (alpha.getTarget() == null) {
                    for (org.bukkit.entity.Player player : alpha.getWorld().getPlayers()) {
                        if (player.getLocation().distance(alpha.getLocation()) <= 20) {
                            alpha.setTarget(player);
                            shareTargetWithPack(alpha, player);
                            break;
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 40);
    }

    private void shareTargetWithPack(org.bukkit.entity.Wolf alpha, org.bukkit.entity.Player target) {
        for (LivingEntity wolf : packWolves) {
            if (wolf instanceof org.bukkit.entity.Wolf packWolf && wolf.isValid()) {
                packWolf.setTarget(target);
            }
        }
    }
}