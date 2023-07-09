package rannist.stickfreeze;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

public class BossBarTimer extends BukkitRunnable {
    private final Player player;
    private final int duration;
    private int remainingTime;
    private final BossBar bossBar;
    private final NamespacedKey namespacedKey;

    public BossBarTimer(Player player, int duration) {
        this.player = player;
        this.remainingTime = duration;
        this.duration = duration;
        this.namespacedKey = Objects.requireNonNull(NamespacedKey.fromString(UUID.randomUUID().toString()));
        this.bossBar = Bukkit.createBossBar(namespacedKey, this.getTitle(), BarColor.RED, BarStyle.SOLID);
        this.bossBar.addPlayer(player);
    }

    private String getTitle() {
        return "§e锁定剩余时间：§c" + this.remainingTime + "s";
    }

    public NamespacedKey getNamespacedKey() {
        return this.namespacedKey;
    }

    @Override
    public void run() {
        try {
            // 更新剩余时间并更新 BossBar
            while (remainingTime > 0) {
                float progress = (float) remainingTime / duration;
                this.bossBar.setProgress(progress);
                this.bossBar.setTitle(this.getTitle());
                Thread.sleep(1000L);
                remainingTime--;
            }
            this.bossBar.removePlayer(this.player);
            this.bossBar.removeAll();
            Bukkit.removeBossBar(this.getNamespacedKey());
            this.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
