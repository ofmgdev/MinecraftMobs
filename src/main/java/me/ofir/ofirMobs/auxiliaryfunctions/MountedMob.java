package me.ofir.ofirMobs.auxiliaryfunctions;

import me.ofir.ofirMobs.rootfunctions.CustomHorse;
import me.ofir.ofirMobs.rootfunctions.CustomMob;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MountedMob {

    private final JavaPlugin plugin;
    private final CustomHorse horseConfig;
    private final CustomMob riderConfig;

    private AbstractHorse horse;
    private LivingEntity rider;

    public MountedMob(JavaPlugin plugin, CustomHorse horseConfig, CustomMob riderConfig) {
        this.plugin = plugin;
        this.horseConfig = horseConfig;
        this.riderConfig = riderConfig;
    }
    
    public LivingEntity spawn(Location loc) {
        // טוען את הצ'אנק לפי המיקום
        loadChunk(loc);

        // יוצרים קודם את הסוס
        horse = horseConfig.spawn(loc);

        // אחר כך יוצרים את הרוכב
        rider = riderConfig.spawn(loc);


        if (horse == null || rider == null) return null;

        // מחברים את הרוכב לסוס אחרי טיקט אחד
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            if (horse.isValid() && rider.isValid()) {
                horse.addPassenger(rider);
            }
        });

        startTracking();
        return rider;
    }

    private void loadChunk(Location loc) {
        Chunk chunk = loc.getChunk();
        if (!chunk.isLoaded()) chunk.load();
        chunk.setForceLoaded(true);
    }

    private void startTracking() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!horse.isValid() || !rider.isValid()) {
                    horse.getLocation().getChunk().setForceLoaded(false);
                    cancel();
                    return;
                }

                if (!horse.getPassengers().contains(rider)) {
                    horse.addPassenger(rider);
                }

                if (rider instanceof org.bukkit.entity.Monster monster) {
                    LivingEntity target = monster.getTarget();

                    if (target != null && target.isValid()) {
                        // תן לסוס לרדוף אחרי המטרה באופן טבעי
                        if (horse instanceof org.bukkit.entity.Creature creature) {
                            creature.setTarget(target);
                        }
                        horse.setAI(true);
                    } else {
                        // אין מטרה - תן לסוס לנדוד חופשי
                        if (horse instanceof org.bukkit.entity.Creature creature) {
                            creature.setTarget(null);
                        }
                        horse.setAI(true);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }

    public LivingEntity getRider() {
        return rider;
    }
}

