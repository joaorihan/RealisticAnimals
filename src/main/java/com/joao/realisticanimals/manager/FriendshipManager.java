package com.joao.realisticanimals.manager;

import com.joao.realisticanimals.RealisticAnimals;
import com.joao.realisticanimals.events.FriendshipIncreaseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FriendshipManager {

    private final RealisticAnimals plugin = RealisticAnimals.getInstance();

    private UUID playerUUID;
    private UUID animalUUID;
    private int friendship;

    public void increaseFriendship(Player player, Entity animal) throws SQLException {

        PreparedStatement select = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM friendships WHERE PLAYER_UUID = ? AND ANIMAL_UUID = ?;");
        select.setString(1, player.getUniqueId().toString());
        select.setString(2, animal.getUniqueId().toString());
        ResultSet rs = select.executeQuery();

        if (!rs.next()){
            playerUUID = player.getUniqueId();
            animalUUID = animal.getUniqueId();
            friendship = 1;
            PreparedStatement insert = plugin.getDatabase().getConnection().prepareStatement("INSERT INTO friendships (ID, PLAYER_UUID, ANIMAL_UUID, FRIENDSHIP) VALUES (" +
                    "default," +
                    "'" + playerUUID + "'," +
                    "'" + animalUUID + "'," +
                    friendship + ");");
            insert.executeUpdate();
        } else {
            playerUUID = UUID.fromString(rs.getString("PLAYER_UUID"));
            animalUUID = UUID.fromString(rs.getString("ANIMAL_UUID"));
            friendship = rs.getInt("FRIENDSHIP");
            if (friendship < 10) friendship += 1; //max
        }


        FriendshipIncreaseEvent e = new FriendshipIncreaseEvent(player, animal, friendship);
        Bukkit.getPluginManager().callEvent(e);

        if (!e.isCancelled()){
            PreparedStatement update = plugin.getDatabase().getConnection().prepareStatement("UPDATE friendships SET FRIENDSHIP = '" + friendship + "' WHERE PLAYER_UUID = '"+ playerUUID + "' AND ANIMAL_UUID = '" + animalUUID + "';");
            update.executeUpdate();
        }

    }


    public void decreaseFriendship(){
        // todo: implement LastInteraction in database
    }


    public void clearFriendships(Entity animal) throws SQLException {
        animalUUID = animal.getUniqueId();
        PreparedStatement delete = plugin.getDatabase().getConnection().prepareStatement("DELETE FROM friendships WHERE ANIMAL_UUID = '" + animalUUID +"';");
        delete.executeUpdate();
        // todo: check if works
    }

    public int getFriendship(Player player, Entity animal) throws SQLException {
        PreparedStatement select = plugin.getDatabase().getConnection().prepareStatement("SELECT FRIENDSHIP FROM friendships WHERE PLAYER_UUID = ? AND ANIMAL_UUID = ?;");
        select.setString(1, player.getUniqueId().toString());
        select.setString(2, animal.getUniqueId().toString());
        ResultSet rs = select.executeQuery();

        if(rs.next())
            friendship = rs.getInt("FRIENDSHIP");
        else
            friendship = 0;
        return friendship;
    }

    public boolean canDropItems(Entity animal) throws SQLException {
        PreparedStatement select = plugin.getDatabase().getConnection().prepareStatement("SELECT FRIENDSHIP FROM friendships WHERE ANIMAL_UUID = ?;");
        select.setString(1, animal.getUniqueId().toString());
        ResultSet rs = select.executeQuery();

        while (rs.next()){
            friendship = rs.getInt("FRIENDSHIP");
            if (friendship > 6) return true;
        }
        return false;
    }


}
