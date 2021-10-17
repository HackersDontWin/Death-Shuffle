package net.hackersdontwin.deathshuffle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private DeathShuffle plugin;

    public Util(DeathShuffle plugin) {
        this.plugin = plugin;
    }

    public String getRandomCause(String prevCause) {
        List<String> causes = new ArrayList<String>();

        causes.add("FIRE");
        causes.add("PROJECTILE");
        causes.add("BLOCK_EXPLOSION");
        causes.add("DROWNING");
        causes.add("ENTITY_ATTACK");
        causes.add("ENTITY_EXPLOSION");
        causes.add("FALL");
        causes.add("FALLING_BLOCK");
        causes.add("FIRE_TICK");
        causes.add("LAVA");
        causes.add("STARVATION");
        causes.add("SUFFOCATION");
        causes.add("THORNS");
//        causes.add("POISON");
//        causes.add(EntityDamageEvent.DamageCause.SUICIDE);
//        causes.add(EntityDamageEvent.DamageCause.CONTACT);
//        causes.add(EntityDamageEvent.DamageCause.DRAGON_BREATH);
//        causes.add(EntityDamageEvent.DamageCause.FLY_INTO_WALL);
//        causes.add(EntityDamageEvent.DamageCause.LIGHTNING);
//        causes.add(EntityDamageEvent.DamageCause.MAGIC);
//        causes.add(EntityDamageEvent.DamageCause.MELTING);
//        causes.add(EntityDamageEvent.DamageCause.VOID);
//        causes.add(EntityDamageEvent.DamageCause.WITHER);

        causes.remove(prevCause);

        return causes.get(DeathShuffle.random.nextInt(causes.size()));
    }

    public void sendPlayerCauseMission(Player player, String cause) {
        String causeMsg = "";
        switch (cause) {
            case "FIRE":
                causeMsg = "Fire Block";
                break;
            case "FIRE_TICK":
                causeMsg = "Fire Tick";
                break;
            case "BLOCK_EXPOSION":
                causeMsg = "TNT";
                break;
            case "ENTITY_ATTACK":
                causeMsg = "Entity Attack";
                break;
            default:
                causeMsg = cause.charAt(0) + cause.substring(1).toLowerCase();
        }
        player.sendMessage(ChatColor.GREEN + "You must complete the death:\n➜ " + ChatColor.YELLOW + causeMsg);
        if(plugin.getConfigManager().getConfig().getAsJsonObject("tablist").get("enabled").getAsBoolean()) {
            player.setPlayerListName(ChatColor.DARK_PURPLE + "[" + causeMsg + "] " + player.getName());
        }
        givePlayerATimer(player, cause, causeMsg);
    }

    public boolean didPlayerDieCorrectly(Player player, String cause) {
        if(cause.equalsIgnoreCase(plugin.getGameManager().getGetPlayerCause().get(player))) {
            return true;
        }
        return false;
    }

    public void givePlayerATimer(final Player player, final String cause, final String causeMsg) {
        int ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            int seconds = plugin.getConfigManager().getConfig().getAsJsonObject("deathCauses").getAsJsonObject(cause).get("time").getAsInt();
            @Override
            public void run() {
                player.setPlayerListName(ChatColor.GOLD + "[" + seconds + "] " + ChatColor.DARK_PURPLE + "[" + causeMsg + "] " + player.getName());
                if(seconds >= 60 && seconds%60 == 0) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "You have " + seconds + " seconds left!");
                } else if(seconds >= 10 && seconds%10 == 0) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "You have " + seconds + " seconds left!");
                } else if(seconds < 10 && seconds > 0) {
                    if(seconds == 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "You have " + seconds + " seconds left!");
                    } else {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "You have " + seconds + " second left!");
                    }
                } else if(seconds == 0) {
                    player.sendTitle(ChatColor.RED + "YOU LOST!", "You didn't die correctly in time!");
                    plugin.getGameManager().removePlayerFromGame(player);
                    player.setGameMode(GameMode.SPECTATOR);
                }
                seconds--;
            }
        }, 0, 20);
        plugin.getGameManager().addTimerToPlayer(player, ID);
    }

}
