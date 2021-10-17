package net.hackersdontwin.deathshuffle.events;

import net.hackersdontwin.deathshuffle.DeathShuffle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private DeathShuffle plugin;

    public PlayerDeath(DeathShuffle plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        String cause = player.getLastDamageCause().getCause().name();
        if(plugin.getUtil().didPlayerDieCorrectly(player, cause)) {
            Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + player.getName() + " completed their death task!");
            plugin.getGameManager().cancelTimer(player);
            plugin.getGameManager().updatePlayerDeathCause(player);
            plugin.getUtil().sendPlayerCauseMission(player, plugin.getGameManager().getGetPlayerCause().get(player));
        } else {
            player.sendMessage(ChatColor.RED + "You didn't die correctly!");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                player.spigot().respawn();
            }
        }, 10L);
    }

}
