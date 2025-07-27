package me.ofmgdev.MinecraftMobsAddon;

import me.ofmgdev.MinecraftMobsAddon.command.*;
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
        Objects.requireNonNull(this.getCommand("spawnironguardian")).setExecutor(new IronGolemGuardianSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnwerewolfwarrior")).setExecutor(new WerewolfWarriorSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnblazingpyromancer")).setExecutor(new BlazingPyromancerSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnancientdestroyer")).setExecutor(new TheAncientDestroyerSpawner(this));
    }

    public static MinecraftMobsAddon getInstance() {
        return instance;
    }
}
