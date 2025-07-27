package me.ofmgdev.MinecraftMobsAddon.mobs;

import me.ofmgdev.MinecraftMobsAddon.ability.FireTornadoAbility;
import me.ofmgdev.MinecraftMobsAddon.ability.MeteorRainAbility;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomBossBar;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomParticles;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BlazingPyromancer {

    private final JavaPlugin plugin;
    private LivingEntity blaze;

    public BlazingPyromancer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LivingEntity spawn(Location location) {
        // יצירת הבוס - בלייז ענק
        CustomMob blazeConfig = new CustomMob(EntityType.BLAZE, 180.0, 0.3, false, null, false);
        blaze = blazeConfig.spawn(location);

        // שם ולוק
        blaze.customName(Component.text("Blazing Pyromancer", NamedTextColor.GOLD));
        blaze.setCustomNameVisible(true);

        // הגדלת הבלייז פי 2.5
        Objects.requireNonNull(blaze.getAttribute(org.bukkit.attribute.Attribute.SCALE)).setBaseValue(2.5);

        // נזק חזק
        Objects.requireNonNull(blaze.getAttribute(org.bukkit.attribute.Attribute.ATTACK_DAMAGE)).setBaseValue(16.0);

        // יכולות קסם
        MeteorRainAbility.startMeteorRain(plugin, blaze, 12.0, 5, 12.0); // כל 12 שניות, 5 מטאורים, רדיוס 12
        FireTornadoAbility.startFireTornado(plugin, blaze, 15.0, 6.0, 8.0); // כל 15 שניות, רדיוס 6, משך 8 שניות

        // הגנה עצמית - קיר אש סביבו
        startFlameWallDefense(plugin, blaze);

        // אפקטים ובוס בר
        CustomParticles.startFireAura(plugin, blaze);
        CustomBossBar.createBossBar(plugin, blaze, "§6§lBlazing Pyromancer", BarColor.YELLOW);

        return blaze;
    }

    public LivingEntity getBlaze() {
        return blaze;
    }

    private void startFlameWallDefense(JavaPlugin plugin, LivingEntity blaze) {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                if (!blaze.isValid()) {
                    cancel();
                    return;
                }

                // יצירת קיר אש כשמותקף
                double healthPercent = blaze.getHealth() / Objects.requireNonNull(blaze.getAttribute(org.bukkit.attribute.Attribute.MAX_HEALTH)).getValue();

                if (healthPercent <= 0.5) { // מתחת ל-50% בריאות
                    Location loc = blaze.getLocation();

                    // יצירת עיגול אש
                    for (int angle = 0; angle < 360; angle += 30) {
                        double radians = Math.toRadians(angle);
                        double x = loc.getX() + 4 * Math.cos(radians);
                        double z = loc.getZ() + 4 * Math.sin(radians);
                        Location fireLoc = new Location(loc.getWorld(), x, loc.getY(), z);

                        if (fireLoc.getBlock().getType() == org.bukkit.Material.AIR) {
                            fireLoc.getBlock().setType(org.bukkit.Material.FIRE);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 60, 100); // כל 5 שניות
    }
}