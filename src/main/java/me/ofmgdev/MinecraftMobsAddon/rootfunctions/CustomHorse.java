package me.ofmgdev.MinecraftMobsAddon.rootfunctions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

import java.util.Objects;

public record CustomHorse(CustomHorse.HorseType type, double health, double speed, Material armor) {

    public enum HorseType {
        NORMAL, ZOMBIE, SKELETON
    }

    public double getSpeed() {
        return speed;
    }

    // יצירת הסוס בהתאם לפרמטרים
    public AbstractHorse spawn(Location loc) {
        AbstractHorse horse;

        switch (type) {
            case ZOMBIE:
                horse = (ZombieHorse) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE_HORSE);
                break;
            case SKELETON:
                horse = (SkeletonHorse) loc.getWorld().spawnEntity(loc, EntityType.SKELETON_HORSE);
                break;
            case NORMAL:
            default:
                horse = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
                if (armor != null) {
                    ((Horse) horse).getInventory().setArmor(new org.bukkit.inventory.ItemStack(armor));
                }
                break;
        }

        horse.setTamed(true);
        horse.setOwner(null);
        Objects.requireNonNull(horse.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(health);
        horse.setHealth(health);
        Objects.requireNonNull(horse.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(speed);

        return horse;
    }
}