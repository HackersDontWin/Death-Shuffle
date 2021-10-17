package net.hackersdontwin.deathshuffle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hackersdontwin.deathshuffle.commands.StartCMD;
import net.hackersdontwin.deathshuffle.events.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class DeathShuffle extends JavaPlugin {

    private ConfigManager configManager;
    private GameManager gameManager;
    private Gson gson;
    private Util util;
    public static Random random = new Random();

    @Override
    public void onEnable() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        configManager = new ConfigManager(this);
        gameManager = new GameManager(this);
        util = new Util(this);

        getCommand("start").setExecutor(new StartCMD(this));

        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Util getUtil() {
        return util;
    }

    public Gson getGson() {
        return gson;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
