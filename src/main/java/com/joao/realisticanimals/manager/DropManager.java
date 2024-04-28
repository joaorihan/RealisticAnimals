package com.joao.realisticanimals.manager;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.config.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Random;

public class DropManager {

    private final RealisticAnimals plugin = RealisticAnimals.getInstance();

    private final ItemStack drop = new ItemStack(Material.AIR);


    public void dropItems() throws SQLException {

        for (Entity target : Bukkit.getWorld("world").getEntities()){
            if (MainConfig.ENABLED_ANIMALS.contains(target.getType())){
                if (plugin.getFriendshipManager().canDropItems(target)){

                    switch (target.getType()){
                        case COW:
                            drop.setType(Material.LEATHER);
                            break;
                        case SHEEP:
                            drop.setType(Material.valueOf(((Sheep) target).getColor() + "_WOOL"));
                            break;
                        case PIG:
                            drop.setType(Material.DIRT);
                            break;
                        case CHICKEN:
                            drop.setType(Material.FEATHER);
                            break;
                        case RABBIT:
                            drop.setType(Material.RABBIT_FOOT);
                            break;
                        case MUSHROOM_COW:
                            if (new Random().nextInt(10) > 5)
                                drop.setType(Material.RED_MUSHROOM);
                            else
                                drop.setType(Material.BROWN_MUSHROOM);
                            break;
                    }
                    target.getWorld().dropItem(target.getLocation(), drop);
                }
            }
        }


    }

}
