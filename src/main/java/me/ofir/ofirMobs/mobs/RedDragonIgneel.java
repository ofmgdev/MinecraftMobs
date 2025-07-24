package me.ofir.ofirMobs.mobs;

import me.ofir.ofirMobs.CustomMob;
import me.ofir.ofirMobs.effects.CustomBossBar;
import me.ofir.ofirMobs.effects.CustomParticles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class RedDragonIgneel {

    private final JavaPlugin plugin;
    private final CustomMob dragonConfig;
    private LivingEntity dragon;

    public RedDragonIgneel(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dragonConfig = new CustomMob(EntityType.ENDER_DRAGON, 250.0, 0.45, false, null, false);
    }

    public void spawn(Location location) {
        dragon = dragonConfig.spawn(location);

        if (dragon != null) {
            dragon.customName(Component.text("Igneel Fire Dragon", NamedTextColor.RED));
            dragon.setCustomNameVisible(true);

            CustomParticles.startFireAura(plugin, dragon);
            // הוספת boss bar
            CustomBossBar.createBossBar(plugin, dragon, "§c§lIgneel the Fire Dragon", org.bukkit.boss.BarColor.RED);
            startFireballAttacks();
        }

    }

    public LivingEntity getDragon() {
        return dragon;
    }

    private void startFireballAttacks() {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                if (dragon == null || !dragon.isValid()) {
                    cancel();
                    return;
                }

                // יירוי fireball כל 3 שניות
                org.bukkit.entity.Fireball fireball = dragon.launchProjectile(org.bukkit.entity.Fireball.class);
                fireball.setYield(2.0f); // כח פיצוץ
            }
        }.runTaskTimer(plugin, 20, 60); // אחרי שנייה, כל 3 שניות
    }
}