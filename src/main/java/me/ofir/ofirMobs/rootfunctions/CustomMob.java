package me.ofir.ofirMobs.rootfunctions;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

public class CustomMob {

    private final EntityType mobType;
    private final double health;
    private final double speed;
    private final boolean mounted;   // האם הרוכב צריך סוס
    private final CustomHorse horse; // פרטי הסוס אם צריך

    private CustomArmor.ArmorSet gear; // ערכת שריון ונשק
    private boolean useGear = true; // האם להשתמש בציוד
    // שדה לשמור את הסוס שנוצר אם צריך
    private AbstractHorse spawnedHorse;

    public CustomMob(EntityType mobType, double health, double speed, boolean mounted, CustomHorse horse) {
        this.mobType = mobType;
        this.health = health;
        this.speed = speed;
        this.mounted = mounted;
        this.horse = horse;
    }

    // קונסטרקטור נוסף עם בחירת ציוד
    public CustomMob(EntityType mobType, double health, double speed, boolean mounted, CustomHorse horse, boolean useGear) {
        this(mobType, health, speed, mounted, horse);
        this.useGear = useGear;
    }

    public void setGearByName(String gearName) {
        this.gear = CustomArmor.getSetByName(gearName);
    }

    // יצירת המוביל, ואם צריך גם הסוס שמחובר אליו
    public LivingEntity spawn(Location location) {
        if (location == null || mobType == null) return null;

        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, mobType);

        Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(health);
        entity.setHealth(health);
        Objects.requireNonNull(entity.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(speed);

        // טיפול מיוחד לדרקונים
        if (mobType == EntityType.ENDER_DRAGON) {
            entity.setAI(true);
            org.bukkit.entity.EnderDragon dragon = (org.bukkit.entity.EnderDragon) entity;
            dragon.setPhase(EnderDragon.Phase.CHARGE_PLAYER);
        }

        if (gear != null && useGear) {
            Objects.requireNonNull(entity.getEquipment()).setHelmet(gear.helmet());
            entity.getEquipment().setChestplate(gear.chestplate());
            entity.getEquipment().setLeggings(gear.leggings());
            entity.getEquipment().setBoots(gear.boots());
        }

        if (mounted && horse != null) {
            spawnedHorse = horse.spawn(location);
            spawnedHorse.addPassenger(entity);
        }

        return entity;
    }

    // אם תרצה לגשת לסוס שנוצר
    public AbstractHorse getSpawnedHorse() {
        return spawnedHorse;
    }
}

