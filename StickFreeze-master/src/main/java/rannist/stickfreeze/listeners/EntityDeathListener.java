package rannist.stickfreeze.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

import static rannist.stickfreeze.StickFreeze.lockedEntities;
import static rannist.stickfreeze.StickFreeze.particleTasks;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        for (List<UUID> uuidList : lockedEntities.values()) {
            if (uuidList.contains(entity.getUniqueId())) {
                BukkitRunnable task = particleTasks.get(entity.getLocation());
                if (task != null) {
                    task.cancel();
                }
            }
        }
    }
}
