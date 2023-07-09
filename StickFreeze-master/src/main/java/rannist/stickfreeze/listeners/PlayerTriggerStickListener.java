package rannist.stickfreeze.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import rannist.stickfreeze.Utils;

import static rannist.stickfreeze.StickFreeze.coolSticks;

public class PlayerTriggerStickListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand().equals(EquipmentSlot.HAND)) {
            Player player = event.getPlayer(); // 控制的玩家
            Entity entity = event.getRightClicked(); // 被控的生物

            ItemStack weapon = player.getInventory().getItemInMainHand();
            if (coolSticks.contains(weapon)) {
                if (entity instanceof LivingEntity) {
                    Utils.lockEntity(player, (LivingEntity) entity);
                }
            }
        }
    }
}
