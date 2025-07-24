package me.ofir.ofirMobs;

import me.ofir.ofirMobs.command.RedDragonIgneelSpawner;
import me.ofir.ofirMobs.command.SkeletonArcherLordSpawner;
import me.ofir.ofirMobs.command.ZombieKnightSpawner;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class OfirMobs extends JavaPlugin {

    private static OfirMobs instance;

    @Override
    public void onEnable() {
        getLogger().info("MonsterPlugin enabled!");
        instance = this;

        Objects.requireNonNull(this.getCommand("spawnzombieknight")).setExecutor(new ZombieKnightSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnigneel")).setExecutor(new RedDragonIgneelSpawner(this));
        Objects.requireNonNull(this.getCommand("spawnskeletonlord")).setExecutor(new SkeletonArcherLordSpawner(this));
    }

    public static OfirMobs getInstance() {
        return instance;
    }
}

