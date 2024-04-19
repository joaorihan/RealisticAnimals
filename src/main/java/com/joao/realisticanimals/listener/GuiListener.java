package com.joao.realisticanimals.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {


    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).contains("Friendship") &&
                e.getCurrentItem() != null) {
            e.setCancelled(true);

        }
    }

}
