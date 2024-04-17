package com.joao.realisticanimals.listener;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.events.FriendshipIncreaseEvent;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.sql.SQLException;
import java.util.Random;

public class AnimalsListener implements Listener {

    private static final RealisticAnimals plugin = RealisticAnimals.getInstance();

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();
        // make list for possible entities
        if (!player.isSneaking() && entity.getType() == EntityType.COW) {
            // interact
            if (new Random().nextInt(10) >= 3){
                try {
                    plugin.getFriendshipManager().increaseFriendship(player, entity);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            entity.playEffect(EntityEffect.LOVE_HEARTS);
        }
    }

    @EventHandler
    public void onFriendshipIncrease(FriendshipIncreaseEvent e){
        Player player = e.getPlayer();
        Entity animal = e.getAnimal();
        player.sendMessage(ChatColor.GREEN + "Seu nível de amizade com " + animal.getName() + " aumentou para " + e.getFriendship());
    }


}
