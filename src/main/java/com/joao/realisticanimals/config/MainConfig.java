package com.joao.realisticanimals.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;

public class MainConfig {

    public static String FRIENDSHIP_GUI_ITEM;
    public static Set<EntityType> ENABLED_ANIMALS;


    public static void loadConfig(){
        YamlConfiguration config = Config.getMainConfig().getConfig();

        FRIENDSHIP_GUI_ITEM = config.getString("friendship-gui-item");
        ENABLED_ANIMALS = new HashSet<>();
        for(String s : config.getStringList("enabled-animals")) ENABLED_ANIMALS.add(EntityType.valueOf(s));


    }

}
