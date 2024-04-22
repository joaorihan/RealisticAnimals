package com.joao.realisticanimals.listener;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.config.MainConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import java.sql.SQLException;

public class BreedingListener implements Listener {

    private final RealisticAnimals plugin = RealisticAnimals.getInstance();

    @EventHandler
    public void onBreed(EntityBreedEvent e) throws SQLException {

        Player player = (Player) e.getBreeder();
        Animals target = (Animals) e.getEntity();
        Animals target2 = (Animals) e.getFather();

        if (plugin.getFriendshipManager().getFriendship(player, target) < 5 &&
            plugin.getFriendshipManager().getFriendship(player, target2) < 5 &&
            MainConfig.ENABLED_ANIMALS.contains(target.getType())) {

            target.setLoveModeTicks(0);
            target2.setLoveModeTicks(0);

            e.setCancelled(true);

            player.sendMessage(ChatColor.RED + "Você ainda não pode reproduzir esses dois animais juntos!");

        }
    }

}
