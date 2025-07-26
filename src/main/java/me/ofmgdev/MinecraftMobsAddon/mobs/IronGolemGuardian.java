package me.ofmgdev.MinecraftMobsAddon.mobs;

import me.ofmgdev.MinecraftMobsAddon.ability.GroundSlamAbility;
import me.ofmgdev.MinecraftMobsAddon.ability.SelfRepairAbility;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomBossBar;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomParticles;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static java.lang.Integer.MAX_VALUE;

public class IronGolemGuardian {

    private final JavaPlugin plugin;
    private LivingEntity ironGolem;

    public IronGolemGuardian(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LivingEntity spawn(Location location) {
        // יצירת הבוס - גולם ברזל ענק
        CustomMob golemConfig = new CustomMob(EntityType.IRON_GOLEM, 200.0, 0.15, false, null, false);
        ironGolem = golemConfig.spawn(location);

        if (ironGolem instanceof IronGolem golem) {
            // שם ולוק
            ironGolem.customName(Component.text("Iron Guardian", NamedTextColor.GRAY));
            ironGolem.setCustomNameVisible(true);

            // הגדלת הגולם פי 3
            Objects.requireNonNull(ironGolem.getAttribute(Attribute.SCALE)).setBaseValue(3.0);

            // הגדרת מצב זעם קבוע
            golem.setPlayerCreated(false); // לא יהיה friendly
            startAggressiveBehavior(plugin, golem); // התנהגות תוקפנית

            // יכולות
            GroundSlamAbility.startGroundSlam(plugin, ironGolem, 6.0, 8.0, 12.0); // כל 6 שניות, רדיוס 8, נזק 12
            SelfRepairAbility.startSelfRepair(plugin, ironGolem, 8.0, 25.0, 0.5); // כל 8 שניות, ריפוי 25, מתחת ל-50%

            // אפקטים ובוס בר
            CustomParticles.startLightningAura(plugin, ironGolem); // אפקטי מתכת
            CustomBossBar.createBossBar(plugin, ironGolem, "§7§lIron Guardian", BarColor.WHITE);
        }

        return ironGolem;
    }

    public LivingEntity getIronGolem() {
        return ironGolem;
    }

    private void startAggressiveBehavior(JavaPlugin plugin, LivingEntity golem) {
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                if (!golem.isValid()) {
                    cancel();
                    return;
                }

                // חפש שחקנים וvillagers ברדיוס 20 בלוקים
                for (LivingEntity nearby : golem.getWorld().getLivingEntities()) {
                    if (nearby.getLocation().distance(golem.getLocation()) <= 20) {
                        if (nearby instanceof org.bukkit.entity.Player || nearby instanceof org.bukkit.entity.Villager) {
                            if (golem instanceof org.bukkit.entity.Creature creature) {
                                creature.setTarget(nearby);
                                break;
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 10, 40); // כל 2 שניות
    }
}
