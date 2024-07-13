package com.awildhooman.soulbound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SoulboundConfig {

    public static void initializeConfig() {
        try {
            FabricLoader loader = FabricLoader.getInstance();
            File configFile = new File(loader.getConfigDir() + "/soulbound.json");
            if (configFile.createNewFile()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(new DefaultConfigs());
                Files.write(configFile.toPath(), json.getBytes());
            }
        } catch (IOException e) {}
    }

    public static class DefaultConfigs {
        boolean damageSoulboundItems = false;
        double minimumDamage = 0.1;
        double maximumDamage = 0.2;
    }
}