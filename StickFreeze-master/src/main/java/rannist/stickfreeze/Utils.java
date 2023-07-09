package rannist.stickfreeze;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static rannist.stickfreeze.StickFreeze.*;

public class Utils {
    public static void lockEntity(Player controller, LivingEntity entity) {
        List<UUID> uuidList = lockedEntities.get(controller.getUniqueId());
        if (uuidList == null) uuidList = new ArrayList<>();
        uuidList.add(entity.getUniqueId());
        lockedEntities.put(controller.getUniqueId(), uuidList);
        if (controller != entity) controller.sendMessage("§a你锁定了实体，它将保持在原地，持续5秒钟！");
        entity.sendMessage("§a你被锁定在原地，持续5秒钟！");
        if (!(entity instanceof Player)) entity.setAI(false);

        // 创建环状粒子特效，显示剩余的锁定时间
        createParticleRing(entity.getLocation(), 5);

        if (entity instanceof Player) {
            // 创建一个 BossBar 来显示剩余的锁定时间
            BossBarTimer bossBarTimer = new BossBarTimer((Player) entity, 5);
            bossBarTimer.runTaskAsynchronously(instance); // 每秒更新一次 BossBar
            bossBarTimers.put((Player) entity, bossBarTimer);
        }

        // 创建一个计时器，在锁定持续时间结束后取消锁定
        BukkitRunnable lockTimer = new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> uuidList = lockedEntities.get(controller.getUniqueId());
                if (uuidList == null) uuidList = new ArrayList<>();
                uuidList.remove(entity.getUniqueId());
                lockedEntities.put(controller.getUniqueId(), uuidList);
                if (entity instanceof Player) {
                    BossBarTimer timer = bossBarTimers.get(entity);
                    if (timer != null) timer.cancel();
                } else {
                    entity.setAI(true);
                }
                if (controller != entity) controller.sendMessage("§a锁定时间已结束，实体已解除锁定！");
                entity.sendMessage("§a锁定时间已结束，你可以自由移动了！");
            }
        };
        lockTimer.runTaskLaterAsynchronously(instance, 100L); // 5秒后取消锁定
    }

    public static void createParticleRing(Location location, int duration) {
        double radius = 1.5; // 环的半径
        int particles = 100; // 粒子数量

        BukkitRunnable particleTask = new BukkitRunnable() {
            double angle = 0;
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= duration * 20) {
                    cancel();
                    return;
                }

                angle += Math.PI / particles;

                for (int i = 0; i < particles; i++) {
                    double x = location.getX() + radius * Math.cos(angle * i);
                    double z = location.getZ() + radius * Math.sin(angle * i);
                    Location particleLoc = new Location(location.getWorld(), x, location.getY(), z);

                    particleLoc.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 0, new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1));
                }

                ticks++;
            }
        };
        particleTasks.put(location, particleTask);
        particleTask.runTaskTimerAsynchronously(instance, 0, 1);
    }
}
