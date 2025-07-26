package me.ofmgdev.MinecraftMobsAddon.command;

import me.ofmgdev.MinecraftMobsAddon.MinecraftMobsAddon;
import me.ofmgdev.MinecraftMobsAddon.mobs.WerewolfWarrior;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WerewolfWarriorSpawner implements CommandExecutor {

    private final MinecraftMobsAddon plugin;

    public WerewolfWarriorSpawner(MinecraftMobsAddon plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;

        new WerewolfWarrior(plugin).spawn(player.getLocation());

        return true;
    }
}
