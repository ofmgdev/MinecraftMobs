package me.ofir.ofirMobs.mobs;

import me.ofir.ofirMobs.rootfunctions.CustomHorse;
import me.ofir.ofirMobs.rootfunctions.CustomMob;
import me.ofir.ofirMobs.auxiliaryfunctions.MountedMob;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

import java.util.Objects;

public class ZombieKnight {

    private final MountedMob mountedMob;

    public ZombieKnight(JavaPlugin plugin) {
        CustomHorse horse = new CustomHorse(CustomHorse.HorseType.NORMAL, 30.0, 0.5, Material.DIAMOND_HORSE_ARMOR);

        CustomMob zombie = new CustomMob(EntityType.ZOMBIE, 80.0, 0.20, false, null);
        zombie.setGearByName("diamond");

        this.mountedMob = new MountedMob(plugin, horse, zombie);

        if (mountedMob.getRider() != null) {
            Objects.requireNonNull(mountedMob.getRider().getEquipment()).setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
        }
    }

    public void spawn(Location location) {
        mountedMob.spawn(location);

        // קבל את הזומבי שנוצר והגדר כותרת
        if (mountedMob.getRider() != null) {
            mountedMob.getRider().customName(net.kyori.adventure.text.Component.text("Zombie Knight", net.kyori.adventure.text.format.NamedTextColor.DARK_PURPLE));
            mountedMob.getRider().setCustomNameVisible(true);
            Objects.requireNonNull(mountedMob.getRider().getEquipment()).setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
        }
    }
}

