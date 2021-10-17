package net.hackersdontwin.deathshuffle;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigManager {

    private DeathShuffle plugin;

    JsonObject config;

    public ConfigManager(DeathShuffle plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.json");

        if(!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                config = new JsonObject();

                JsonObject tablist = new JsonObject();
                tablist.addProperty("enabled", true);
                config.add("tablist", tablist);

                JsonObject deathCauses = new JsonObject();
                JsonObject FIRE = new JsonObject();
                FIRE.addProperty("enabled", true);
                FIRE.addProperty("time", 300);
                deathCauses.add("FIRE", FIRE);
                JsonObject PROJECTILE = new JsonObject();
                PROJECTILE.addProperty("enabled", true);
                PROJECTILE.addProperty("time", 60);
                deathCauses.add("PROJECTILE", PROJECTILE);
                JsonObject BLOCK_EXPOSION = new JsonObject();
                BLOCK_EXPOSION.addProperty("enabled", true);
                BLOCK_EXPOSION.addProperty("time", 300);
                deathCauses.add("BLOCK_EXPOSION", BLOCK_EXPOSION);
                JsonObject DROWNING = new JsonObject();
                DROWNING.addProperty("enabled", true);
                DROWNING.addProperty("time", 300);
                deathCauses.add("DROWNING", DROWNING);
                JsonObject ENTITY_ATTACK = new JsonObject();
                ENTITY_ATTACK.addProperty("enabled", true);
                ENTITY_ATTACK.addProperty("time", 60);
                deathCauses.add("ENTITY_ATTACK", ENTITY_ATTACK);
                JsonObject ENTITY_EXPLOSION = new JsonObject();
                ENTITY_EXPLOSION.addProperty("enabled", true);
                ENTITY_EXPLOSION.addProperty("time", 75);
                deathCauses.add("ENTITY_EXPLOSION", ENTITY_EXPLOSION);
                JsonObject FALL = new JsonObject();
                FALL.addProperty("enabled", true);
                FALL.addProperty("time", 60);
                deathCauses.add("FALL", FALL);
                JsonObject FALLING_BLOCK = new JsonObject();
                FALLING_BLOCK.addProperty("enabled", true);
                FALLING_BLOCK.addProperty("time", 300);
                deathCauses.add("FALLING_BLOCK", FALLING_BLOCK);
                JsonObject FIRE_TICK = new JsonObject();
                FIRE_TICK.addProperty("enabled", true);
                FIRE_TICK.addProperty("time", 75);
                deathCauses.add("FIRE_TICK", FIRE_TICK);
                JsonObject LAVA = new JsonObject();
                LAVA.addProperty("enabled", true);
                LAVA.addProperty("time", 200);
                deathCauses.add("LAVA", LAVA);
                JsonObject STARVATION = new JsonObject();
                STARVATION.addProperty("enabled", true);
                STARVATION.addProperty("time", 240);
                deathCauses.add("STARVATION", STARVATION);
                JsonObject SUFFOCATION = new JsonObject();
                SUFFOCATION.addProperty("enabled", true);
                SUFFOCATION.addProperty("time", 60);
                deathCauses.add("SUFFOCATION", SUFFOCATION);
                JsonObject THORNS = new JsonObject();
                THORNS.addProperty("enabled", true);
                THORNS.addProperty("time", 300);
                deathCauses.add("THORNS", THORNS);
                config.add("deathCauses", deathCauses);

                FileWriter fileWriter = new FileWriter(configFile);
                fileWriter.write(plugin.getGson().toJson(config));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Scanner scanner = new Scanner(configFile);
                String fileContent = "";
                while(scanner.hasNextLine()) {
                    fileContent += scanner.nextLine();
                }
                config = plugin.getGson().fromJson(fileContent, JsonObject.class);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public JsonObject getConfig() {
        return config;
    }
}
