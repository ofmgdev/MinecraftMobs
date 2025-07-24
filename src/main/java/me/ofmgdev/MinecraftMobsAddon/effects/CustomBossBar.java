package me.ofmgdev.MinecraftMobsAddon.effects;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class CustomBossBar {

    public static void createBossBar(JavaPlugin plugin, LivingEntity entity, String title, BarColor color) {
        if (entity instanceof EnderDragon dragon) {
            // דרקון - בדוק אם יש boss bar טבעי
            if (dragon.getBossBar() != null) {
                dragon.getBossBar().setVisible(true);
                dragon.getBossBar().setTitle(title);
                dragon.getBossBar().setProgress(1.0);
            } else {
                // צור boss bar חדש אם אין טבעי
                BossBar bossBar = Bukkit.createBossBar(title, color, BarStyle.SOLID);
                addPlayersInRange(plugin, entity, bossBar);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!dragon.isValid()) {
                        cancel();
                        return;
                    }
                    double healthPercent = dragon.getHealth() / Objects.requireNonNull(dragon.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();
                    if (dragon.getBossBar() != null) {
                        dragon.getBossBar().setProgress(healthPercent);
                    }
                }
            }.runTaskTimer(plugin, 0, 10);
        } else {
            // מפלצות אחרות - צור boss bar חדש
            BossBar bossBar = Bukkit.createBossBar(title, color, BarStyle.SOLID);

            // הוסף שחקנים קרובים
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!entity.isValid()) {
                        bossBar.removeAll();
                        cancel();
                        return;
                    }

                    // עדכן בריאות
                    double healthPercent = entity.getHealth() / Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();
                    bossBar.setProgress(healthPercent);

                    // הוסף שחקנים ברדיוס 50 בלוקים
                    for (Player player : entity.getWorld().getPlayers()) {
                        if (player.getLocation().distance(entity.getLocation()) <= 50) {
                            bossBar.addPlayer(player);
                        } else {
                            bossBar.removePlayer(player);
                        }
                    }
                }
            }.runTaskTimer(plugin, 0, 20); // כל שנייה
        }
    }

    private static void addPlayersInRange(JavaPlugin plugin, LivingEntity entity, BossBar bossBar) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    bossBar.removeAll();
                    cancel();
                    return;
                }
                double healthPercent = entity.getHealth() / Objects.requireNonNull(entity.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();
                bossBar.setProgress(healthPercent);

                for (org.bukkit.entity.Player player : entity.getWorld().getPlayers()) {
                    if (player.getLocation().distance(entity.getLocation()) <= 50) {
                        bossBar.addPlayer(player);
                    } else {
                        bossBar.removePlayer(player);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}