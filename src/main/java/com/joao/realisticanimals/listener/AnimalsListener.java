package com.joao.realisticanimals.listener;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.config.MainConfig;
import com.joao.realisticanimals.events.FriendshipIncreaseEvent;
import com.joao.realisticanimals.gui.FriendshipGUI;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.sql.SQLException;
import java.util.Random;
import java.util.Set;

public class AnimalsListener implements Listener {

    private static final RealisticAnimals plugin = RealisticAnimals.getInstance();

    Set<EntityType> enabledAnimals = MainConfig.ENABLED_ANIMALS;

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        Entity animal = e.getRightClicked();

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR
            && enabledAnimals.contains(animal.getType())) {
            if (!player.isSneaking()) {
                if (new Random().nextInt(10) >= 4) {
                    try {
                        plugin.getFriendshipManager().increaseFriendship(player, animal);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                animal.playEffect(EntityEffect.LOVE_HEARTS);
            } else {
                try {
                    new FriendshipGUI(player, animal);
                } catch (SQLException ex) {
                    player.sendMessage(ChatColor.RED + "Couldn't get your data from the database. Please contact an administrator");
                    throw new RuntimeException(ex);
                }
            }
        }

    }

    @EventHandler
    public void onFriendshipIncrease(FriendshipIncreaseEvent e){
        Player player = e.getPlayer();
        Entity animal = e.getAnimal();
        player.sendMessage(ChatColor.GRAY + "Seu nível de " + ChatColor.RED + "amizade" + ChatColor.GRAY+" com " + ChatColor.YELLOW + animal.getName() + ChatColor.GRAY+" aumentou para " + ChatColor.RED + e.getFriendship());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        Entity animal = e.getEntity();
        if(enabledAnimals.contains(animal.getType())) {
            try {
                plugin.getFriendshipManager().clearFriendships(animal);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
