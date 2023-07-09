package rannist.stickfreeze.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rannist.stickfreeze.Utils;

public class LockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length == 1) {
                Player target = Bukkit.getPlayer(strings[0]);
                if (target != null) {
                    Utils.lockEntity((Player) commandSender, target);
                    commandSender.sendMessage("§a成功将玩家 " + target.getName() + " 锁定在原地，持续5秒钟！");
                } else {
                    commandSender.sendMessage("§c找不到玩家 " + strings[0]);
                }
            } else {
                commandSender.sendMessage("§c使用方法：/lock <玩家名>");
            }
            return true;
        } else {
            return false;
        }
    }




}
