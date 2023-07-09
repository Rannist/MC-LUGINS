package rannist.stickfreeze.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static rannist.stickfreeze.StickFreeze.generateCoolStick;

public class WeaponCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // 给予特殊武器
            ItemStack coolStick = generateCoolStick();
            player.getInventory().addItem(coolStick);
            player.sendMessage("§a你获得了炫酷木棍！");
            return true;
        }
        return false;
    }
}
