package rannist.stickfreeze;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import rannist.stickfreeze.commands.LockCommand;
import rannist.stickfreeze.commands.WeaponCommand;
import rannist.stickfreeze.listeners.EntityDeathListener;
import rannist.stickfreeze.listeners.PlayerMoveListener;
import rannist.stickfreeze.listeners.PlayerTriggerStickListener;

import java.util.*;

public class StickFreeze extends JavaPlugin {

    public static Map<UUID, List<UUID>> lockedEntities = new HashMap<>(); // K, V => 控制玩家, 被控生物列表
    public static StickFreeze instance;
    public static Map<Location, BukkitRunnable> particleTasks = new HashMap<>();
    public static List<ItemStack> coolSticks = new ArrayList<>();
    public static Map<Player, BossBarTimer> bossBarTimers = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pluginManager = Bukkit.getPluginManager();
        Server server = Bukkit.getServer();
        // 注册事件监听器
        pluginManager.registerEvents(new PlayerTriggerStickListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        // 注册命令
        Objects.requireNonNull(server.getPluginCommand("weapon")).setExecutor(new WeaponCommand());
        Objects.requireNonNull(server.getPluginCommand("lock")).setExecutor(new LockCommand());
        //
        Bukkit.getConsoleSender().sendMessage("§a傻鸟插件已加载");
    }

    @Override
    public void onDisable() {
        // 卸载事件监听器
        HandlerList.unregisterAll(this);
        // 删除所有的 BossBar
        for (BossBarTimer bossBarTimer : bossBarTimers.values()) {
            NamespacedKey namespacedKey = bossBarTimer.getNamespacedKey();
            Bukkit.getBossBar(namespacedKey).setVisible(false);
            Bukkit.removeBossBar(namespacedKey);
        }
    }

    public static ItemStack generateCoolStick() {
        ItemStack weapon = new ItemStack(Material.STICK);
        ItemMeta meta = weapon.getItemMeta();
        UUID uuid = UUID.randomUUID(); // 生成一个随机 UUID
        meta.setDisplayName("§6炫酷木棍");
        meta.setLore(Arrays.asList("§7一个炫酷的武器！", "", "§8" + uuid));
        weapon.setItemMeta(meta);
        coolSticks.add(weapon);
        return weapon;
    }
}
