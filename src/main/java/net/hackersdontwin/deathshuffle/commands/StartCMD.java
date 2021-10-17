package net.hackersdontwin.deathshuffle.commands;

import net.hackersdontwin.deathshuffle.DeathShuffle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCMD implements CommandExecutor {

    private DeathShuffle plugin;

    public StartCMD(DeathShuffle plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Bukkit.broadcastMessage(ChatColor.GREEN + "Death Shuffle started!");
        plugin.getGameManager().startGame();
        return true;
    }
}
