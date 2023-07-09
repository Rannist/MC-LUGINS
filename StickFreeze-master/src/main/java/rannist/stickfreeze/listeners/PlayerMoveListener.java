package rannist.stickfreeze.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.UUID;

import static rannist.stickfreeze.StickFreeze.lockedEntities;

public class PlayerMoveListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        for (List<UUID> uuidList : lockedEntities.values()) {
            if (uuidList.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
