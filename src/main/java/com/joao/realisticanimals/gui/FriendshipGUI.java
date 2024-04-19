package com.joao.realisticanimals.gui;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.config.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.Arrays;

public class FriendshipGUI {

    private static final RealisticAnimals plugin = RealisticAnimals.getInstance();

    public FriendshipGUI(Player player, Entity animal) throws SQLException {
        Inventory gui = Bukkit.createInventory(player, 36, animal.getName() + " Friendship");
        int friendship = plugin.getFriendshipManager().getFriendship(player, animal);
        ItemStack friendshipMeter;
        ItemMeta fsMeta;

        if (friendship == 0) {
            friendshipMeter = new ItemStack(Material.BARRIER);
            fsMeta = friendshipMeter.getItemMeta();
            fsMeta.setLore(Arrays.asList(
                    ChatColor.RED + "Você não possui uma amizade com " + animal.getName() +"!" ,
                    ChatColor.RED + "Interaja com ele(a) para aumentar sua amizade!"
            ));
        }
        else {

            friendshipMeter = new ItemStack(Material.valueOf(MainConfig.FRIENDSHIP_GUI_ITEM.toUpperCase()), friendship);
            fsMeta = friendshipMeter.getItemMeta();
            fsMeta.setLore(Arrays.asList(
                    ChatColor.RED + "Interaja frequentemente com " + animal.getName(),
                    ChatColor.RED + "para aumentar sua amizade!"
            ));
        }
        fsMeta.setDisplayName(ChatColor.DARK_RED + String.valueOf(friendship) + " Friendship");
        friendshipMeter.setItemMeta(fsMeta);

        gui.setItem(22, friendshipMeter);

        ItemStack animalDisplay = new ItemStack(Material.valueOf(animal.getType() + "_SPAWN_EGG"));
        ItemMeta adMeta = animalDisplay.getItemMeta();
        adMeta.setDisplayName(animal.getName());
        animalDisplay.setItemMeta(adMeta);

        gui.setItem(13, animalDisplay);

        player.openInventory(gui);
    }
}