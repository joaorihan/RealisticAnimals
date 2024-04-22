package com.joao.realisticanimals;

import com.joao.realisticanimals.config.Config;
import com.joao.realisticanimals.config.MainConfig;
import com.joao.realisticanimals.database.Database;
import com.joao.realisticanimals.listener.AnimalsListener;
import com.joao.realisticanimals.listener.BreedingListener;
import com.joao.realisticanimals.listener.GuiListener;
import com.joao.realisticanimals.manager.FriendshipManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class RealisticAnimals extends JavaPlugin {

    private static RealisticAnimals plugin;
    private Database database;
    private FriendshipManager friendshipManager;

    @Override
    public void onEnable() {
        plugin = this;

        Config.getMainConfig().saveDefaultConfig();

        MainConfig.loadConfig();

        database = new Database();
        try {
            database.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getPluginManager().registerEvents(new AnimalsListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new BreedingListener(), plugin);

        friendshipManager = new FriendshipManager();
    }

    @Override
    public void onDisable() {
        Config.getMainConfig().saveConfigs();
        database.disconnect();

        plugin = null;
    }


    public static RealisticAnimals getInstance() { return plugin; }
    public Database getDatabase() { return database; }
    public FriendshipManager getFriendshipManager() { return friendshipManager; }

}
