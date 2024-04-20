package com.joao.realisticanimals.gui;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.config.MainConfig;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class FriendshipGUI implements Listener {

    private static final RealisticAnimals plugin = RealisticAnimals.getInstance();

    private static Player player;
    private static Entity animal;

    public FriendshipGUI(Player player, Entity animal) throws SQLException {

        this.player = player;
        this.animal = animal;

        Inventory gui = Bukkit.createInventory(player, 36, animal.getName() + " Friendship");
        int friendship = plugin.getFriendshipManager().getFriendship(player, animal);
        ItemStack friendshipMeter;
        ItemMeta fsMeta;

        if (friendship == 0) {
            friendshipMeter = new ItemStack(Material.BARRIER);
            fsMeta = friendshipMeter.getItemMeta();
            fsMeta.setLore(Arrays.asList(
                    ChatColor.RED + "Você não possui uma amizade com " + animal.getName() + "!",
                    ChatColor.RED + "Interaja com ele(a) para aumentar sua amizade!"
            ));
        } else {

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

        //ItemStack animalDisplay = new ItemStack(Material.valueOf(animal.getType() + "_SPAWN_EGG"));
        ItemStack animalDisplay = new ItemStack(Material.NAME_TAG);
        ItemMeta adMeta = animalDisplay.getItemMeta();
        adMeta.setDisplayName(ChatColor.GREEN + "Renomear");
        adMeta.setLore(Arrays.asList(
                ChatColor.AQUA.toString() + ChatColor.BOLD + ChatColor.UNDERLINE + "Clique para renomear " + animal.getName()
        ));
        animalDisplay.setItemMeta(adMeta);

        gui.setItem(13, animalDisplay);


        player.openInventory(gui);
    }

    public static AnvilGUI.Builder renameGUI() {
        ItemStack stack = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(animal.getName());
        stack.setItemMeta(meta);

        return new AnvilGUI.Builder()
                .onClose(stateSnapshot -> {
                    stateSnapshot.getPlayer().sendMessage(ChatColor.GRAY + "Você renomeou " + ChatColor.YELLOW + animal.getName() + ChatColor.GRAY +  " com sucesso!");
                    stateSnapshot.getPlayer().playSound(stateSnapshot.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
                })
                .onClick((slot, stateSnapshot) -> {
                    if(slot == AnvilGUI.Slot.OUTPUT) {
                        animal.setCustomName(stateSnapshot.getText());
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    } else
                        return Collections.emptyList();
                })
                .preventClose()
                .text(animal.getName())
                .itemLeft(stack)
                .title("Renomear")
                .plugin(plugin);
    }


}