package me.ofmgdev.MinecraftMobsAddon.command;

import me.ofmgdev.MinecraftMobsAddon.MinecraftMobsAddon;
import me.ofmgdev.MinecraftMobsAddon.mobs.TheAncientDestroyer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TheAncientDestroyerSpawner implements CommandExecutor {

    private final MinecraftMobsAddon plugin;

    public TheAncientDestroyerSpawner(MinecraftMobsAddon plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return false;

        // אזהרה לפני יצירה
        player.sendMessage("§4§l⚠ WARNING: You are about to spawn THE ANCIENT DESTROYER!");
        player.sendMessage("§c§lThis boss requires a full team to defeat. Prepare yourself!");

        new TheAncientDestroyer(plugin).spawn(player.getLocation());

        return true;
    }
}