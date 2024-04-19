package com.joao.realisticanimals.config;

import com.joao.realisticanimals.RealisticAnimals;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Config {

    private static final RealisticAnimals plugin = RealisticAnimals.getInstance();


    public static Config main = new Config("config.yml");


    public static Config getMainConfig() { return main; }


    private File configFile;

    private YamlConfiguration YMLConfig;

    private final String filename;



    public Config(String filename) {
        this.filename = filename;
        configFile = new File(plugin.getDataFolder(), filename);
    }


    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), filename);
        }
        YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        Reader reader = new InputStreamReader(
                plugin.getResource(filename), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
        YMLConfig.setDefaults(defConfig);
        YMLConfig.options().copyDefaults(true);
        saveConfigs();
    }


    public YamlConfiguration getConfig() {
        if (YMLConfig == null) {
            reloadConfig();
        }
        return YMLConfig;
    }

    public void saveConfigs() {
        if (YMLConfig == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Houve um erro ao salvar uma configuração", e);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), filename);
        }
        if (!configFile.exists()) {
            plugin.saveResource(filename, false);
            YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        }
    }


}
