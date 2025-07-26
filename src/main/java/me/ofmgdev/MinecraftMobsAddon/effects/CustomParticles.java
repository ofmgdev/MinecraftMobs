package me.ofmgdev.MinecraftMobsAddon.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomParticles {

    public static void startFireAura(JavaPlugin plugin, LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }
                Location loc = entity.getLocation();

                // אפקטי אש חזקים
                loc.getWorld().spawnParticle(Particle.FLAME, loc, 25, 4, 4, 4, 0.02);
                loc.getWorld().spawnParticle(Particle.LAVA, loc, 15, 3, 3, 3, 0);
                loc.getWorld().spawnParticle(Particle.LARGE_SMOKE, loc, 10, 3, 3, 3, 0.01);

                // אפקטים מגניבים נוספים
                loc.getWorld().spawnParticle(Particle.ENCHANT, loc, 20, 5, 5, 5, 1);
                loc.getWorld().spawnParticle(Particle.CRIMSON_SPORE, loc, 15, 4, 4, 4, 0);
                // מדליק בלוקים סביבו
                if (Math.random() < 0.1) { // 10% סיכוי כל 3 טיקים
                    for (int x = -2; x <= 2; x++) {
                        for (int z = -2; z <= 2; z++) {
                            Location blockLoc = loc.clone().add(x, -1, z);
                            if (blockLoc.getBlock().getType().isSolid() &&
                                    blockLoc.clone().add(0, 1, 0).getBlock().getType() == org.bukkit.Material.AIR) {
                                blockLoc.clone().add(0, 1, 0).getBlock().setType(org.bukkit.Material.FIRE);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 3); // יותר מהיר
    }

    public static void startIceAura(JavaPlugin plugin, LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }
                Location loc = entity.getLocation();

                // אפקטי קרח מתקדמים
                loc.getWorld().spawnParticle(Particle.SNOWFLAKE, loc, 30, 4, 4, 4, 0);
                loc.getWorld().spawnParticle(Particle.CLOUD, loc, 15, 3, 3, 3, 0.01);
                loc.getWorld().spawnParticle(Particle.BLOCK, loc, 20, 3, 3, 3, 0.02, org.bukkit.Material.ICE.createBlockData());
                loc.getWorld().spawnParticle(Particle.FALLING_WATER, loc, 25, 4, 4, 4, 0);
            }
        }.runTaskTimer(plugin, 0, 4);
    }

    public static void startLightningAura(JavaPlugin plugin, LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }
                Location loc = entity.getLocation();

                // אפקטי חשמל מתקדמים
                loc.getWorld().spawnParticle(Particle.END_ROD, loc, 20, 4, 4, 4, 0.1);
                loc.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, loc, 25, 3, 3, 3, 0.05);
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 15, 3, 3, 3, 0.02);
                loc.getWorld().spawnParticle(Particle.GLOW, loc, 30, 4, 4, 4, 0);
            }
        }.runTaskTimer(plugin, 0, 2); // מהיר מאוד לאפקט חשמל
    }

    public static void startBlueFireTrail(JavaPlugin plugin, LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }

                Location loc = entity.getLocation();

                // אפקט אש כחולה סביב הישות
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 15, 1, 0.1, 1, 0.02);
                loc.getWorld().spawnParticle(Particle.SOUL, loc, 10, 1, 0.1, 1, 0.01);

                // אש כחולה על הקרקע מתחת
                Location groundLoc = loc.clone().subtract(0, 1, 0);
                if (groundLoc.getBlock().getType().isSolid() &&
                        loc.getBlock().getType() == org.bukkit.Material.AIR) {
                    loc.getBlock().setType(org.bukkit.Material.SOUL_FIRE);
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    public static void startFearAura(JavaPlugin plugin, LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!entity.isValid()) {
                    cancel();
                    return;
                }
                Location loc = entity.getLocation();

                // אפקטי פחד אדומים חזקים - יותר בולטים
                loc.getWorld().spawnParticle(Particle.FLAME, loc.add(0, 2, 0), 50, 5, 3, 5, 0.1);
                loc.getWorld().spawnParticle(Particle.CRIMSON_SPORE, loc, 60, 6, 4, 6, 0.05);
                loc.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, loc, 30, 4, 2, 4, 0);
                loc.getWorld().spawnParticle(Particle.LARGE_SMOKE, loc, 40, 4, 2, 4, 0.02);
                loc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 25, 3, 1, 3, 0.03);

                // אפקטי פחד מקבעים לשחקנים קרובים
                for (org.bukkit.entity.Player player : loc.getWorld().getPlayers()) {
                    double distance = player.getLocation().distance(loc);
                    if (distance <= 12.0) {
                        // איטיות חזקה
                        player.addPotionEffect(new org.bukkit.potion.PotionEffect(
                                org.bukkit.potion.PotionEffectType.SLOWNESS, 60, 2)); // 3 שניות איטיות חזקה

                        // עיוורון קצר במרחק קרוב
                        if (distance <= 6.0) {
                            player.addPotionEffect(new org.bukkit.potion.PotionEffect(
                                    org.bukkit.potion.PotionEffectType.BLINDNESS, 20, 0)); // שנייה עיוורון
                        }

                        // בחילה קלה לאפקט נוסף
                        if (distance <= 8.0) {
                            player.addPotionEffect(new org.bukkit.potion.PotionEffect(
                                    org.bukkit.potion.PotionEffectType.NAUSEA, 40, 0)); // 2 שניות בחילה
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 10); // כל חצי שנייה - יותר מהיר
    }
}
