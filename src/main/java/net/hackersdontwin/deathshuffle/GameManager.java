package net.hackersdontwin.deathshuffle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private DeathShuffle plugin;

    private Map<Player, String> getPlayerCause = new HashMap<Player, String>();
    private List<Player> playerList = new ArrayList<>();
    private Map<Player, Integer> timerID = new HashMap<>();

    public GameManager(DeathShuffle plugin) {
        this.plugin = plugin;
    }

    public void startGame() {
        getPlayerCause.clear();
        playerList.clear();
        for(Map.Entry<Player, Integer> entry : timerID.entrySet()) {
            Bukkit.getScheduler().cancelTask(entry.getValue());
        }
        timerID.clear();

        for(Player player : Bukkit.getOnlinePlayers()) {
            playerList.add(player);
            getPlayerCause.put(player, plugin.getUtil().getRandomCause(""));
            plugin.getUtil().sendPlayerCauseMission(player, getPlayerCause.get(player));
            player.setPlayerListName(player.getName());
        }

        if(plugin.getConfigManager().getConfig().getAsJsonObject("general").get("forceNight").getAsBoolean()) {
            Bukkit.getWorld("world").setTime(18000);
            Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        }
    }

    public Map<Player, String> getGetPlayerCause() {
        return getPlayerCause;
    }

    public void removePlayerFromGame(Player player) {
        playerList.remove(player);
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " has been eliminated from the game!");
        cancelTimer(player);
        checkForWinner();
    }

    public void addTimerToPlayer(Player player, int ID) {
        timerID.put(player, ID);
    }

    public void cancelTimer(Player player) {
        Bukkit.getScheduler().cancelTask(timerID.get(player));
    }

    public void updatePlayerDeathCause(Player player) {
        getPlayerCause.put(player, plugin.getUtil().getRandomCause(getPlayerCause.get(player)));
    }

    public void checkForWinner() {
        if(playerList.size() == 1) {
            final Player player = playerList.get(0);

            Bukkit.getScheduler().cancelTask(timerID.get(player));

            ItemStack is = new ItemStack(Material.DIAMOND);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatColor.AQUA + "You are the winner!");
            is.setItemMeta(im);
            player.getInventory().clear();
            player.getInventory().setItemInMainHand(is);

            Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + player.getName() + " IS THE WINNER!");

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        cancelTimer(p);
                        p.teleport(player.getWorld().getSpawnLocation());
                        p.setGameMode(GameMode.SURVIVAL);
                        p.setPlayerListName(p.getName());
                    }

                    if(plugin.getConfigManager().getConfig().getAsJsonObject("general").get("forceNight").getAsBoolean()) {
                        Bukkit.getWorld("world").setTime(6000);
                        Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "true");
                    }
                }
            }, 20L);


        }
    }

}
