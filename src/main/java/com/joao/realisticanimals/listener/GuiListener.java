package com.joao.realisticanimals.listener;

import com.joao.realisticanimals.gui.FriendshipGUI;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).contains("Friendship") &&
                e.getCurrentItem() != null) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            switch (e.getRawSlot()) {
                case 13:
                    player.playSound(player, Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                    player.closeInventory();

                    AnvilGUI.Builder builder = FriendshipGUI.renameGUI();
                    builder.open(player);

                    break;
            }
        }

    }


}
