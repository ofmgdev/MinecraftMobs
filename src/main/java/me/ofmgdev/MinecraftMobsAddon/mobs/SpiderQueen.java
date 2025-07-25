package me.ofmgdev.MinecraftMobsAddon.mobs;

import me.ofmgdev.MinecraftMobsAddon.ability.PoisonAttackAbility;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomBossBar;
import me.ofmgdev.MinecraftMobsAddon.effects.CustomParticles;
import me.ofmgdev.MinecraftMobsAddon.rootfunctions.CustomMob;
import me.ofmgdev.MinecraftMobsAddon.ability.HelperSpawning;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SpiderQueen {

    private final JavaPlugin plugin;
    private LivingEntity spider;

    public SpiderQueen(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public LivingEntity spawn(Location location) {
        // יצירת הבוס - עכביש גדול
        CustomMob spiderConfig = new CustomMob(EntityType.SPIDER, 135.0, 0.35, false, null, false);
        spider = spiderConfig.spawn(location);

        // שם ולוק
        spider.customName(Component.text("Spider Queen", NamedTextColor.DARK_GREEN));
        spider.setCustomNameVisible(true);

        // הגדלת העכביש פי 2.5
        Objects.requireNonNull(spider.getAttribute(org.bukkit.attribute.Attribute.SCALE)).setBaseValue(2.5);

        // יכולות
        PoisonAttackAbility.startPoisonAttacks(plugin, spider, 2.0);

        // עוזרים - עכבישי רעל קטנים
        HelperSpawning.startHelperSpawning(plugin, spider, 5, 2, 10,
                EntityType.CAVE_SPIDER, Material.AIR, "Baby Spider", HelperSpawning.SpawnMode.SPREAD_OVER_TIME,
                null, false, null, 12.0, 0.55, 25.0);

        // אפקטים ובוס בר
        CustomParticles.startIceAura(plugin, spider); // אפקט ירוק
        CustomBossBar.createBossBar(plugin, spider, "§2§lSpider Queen", BarColor.GREEN);

        return spider;
    }

    public LivingEntity getSpider() {
        return spider;
    }
}