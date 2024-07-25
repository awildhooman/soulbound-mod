package com.awildhooman.soulbound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SoulboundConfig {
    static FabricLoader loader = FabricLoader.getInstance();

    public static void initializeConfig() {
        try {
            File configFile = new File(loader.getConfigDir() + File.separator + "soulbound.json");
            if (configFile.createNewFile()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(new Configs());
                Files.write(configFile.toPath(), json.getBytes());
            }
        } catch (IOException e) {}
    }

    public static Configs readJson() {
        try {
            Gson gson = new Gson();
            File configFile = new File(loader.getConfigDir() + File.separator + "soulbound.json");
            return gson.fromJson(Files.readString(configFile.toPath()), Configs.class);
        } catch (IOException e) {return null;}
    }

    public static class Configs {
        public boolean damageSoulboundItems = false;
        public double minimumDamage = 0.1;
        public double maximumDamage = 0.2;

    }
}