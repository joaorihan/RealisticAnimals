package com.joao.realisticanimals;

import com.joao.realisticanimals.database.Database;
import com.joao.realisticanimals.listener.AnimalsListener;
import com.joao.realisticanimals.manager.FriendshipManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class RealisticAnimals extends JavaPlugin {

    private static RealisticAnimals plugin;
    private Database database;
    private FriendshipManager friendshipManager;


    @Override
    public void onEnable() {
        plugin = this;

        database = new Database();

        try {
            database.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getPluginManager().registerEvents(new AnimalsListener(), plugin);

        friendshipManager = new FriendshipManager();

    }

    @Override
    public void onDisable() {
        plugin = null;
        database.disconnect();
    }


    public static RealisticAnimals getInstance() { return plugin; }
    public Database getDatabase() { return database; }
    public FriendshipManager getFriendshipManager() { return friendshipManager; }


}
