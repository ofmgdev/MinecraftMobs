package me.ofmgdev.MinecraftMobsAddon;

import me.ofmgdev.MinecraftMobsAddon.command.RedDragonIgneelSpawner;
import me.ofmgdev.MinecraftMobsAddon.command.SkeletonArcherLordSpawner;
import me.ofmgdev.MinecraftMobsAddon.command.SpiderQueenSpawner;
import me.ofmgdev.MinecraftMobsAddon.command.ZombieKnightSpawner;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MinecraftMobsAddon extends JavaPlugin {

    private static MinecraftMobsAddon instance;

    @Override
    public void onEnable() {
        getLogger().info("MonsterPlugin enabled!");
        instance = this;

        Objects.requireNonNull(this.getCommand("spawnzombieknight")).setExecutor(new ZombieKnightSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnigneel")).setExecutor(new RedDragonIgneelSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnskeletonlord")).setExecutor(new SkeletonArcherLordSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnspiderqueen")).setExecutor(new SpiderQueenSpawner(this));
    }

    public static MinecraftMobsAddon getInstance() {
        return instance;
    }
}
