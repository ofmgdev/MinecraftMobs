package me.ofir.ofirMobs.command;

import me.ofir.ofirMobs.OfirMobs;
import me.ofir.ofirMobs.mobs.SkeletonArcherLord;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SkeletonArcherLordSpawner implements CommandExecutor {

    private final OfirMobs plugin;

    public SkeletonArcherLordSpawner(OfirMobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;

        new SkeletonArcherLord(plugin).spawn(player.getLocation());

        return true;
    }
}