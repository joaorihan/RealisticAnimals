package com.joao.realisticanimals.debug;

import com.joao.realisticanimals.RealisticAnimals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class DebugCommand implements CommandExecutor {

    private RealisticAnimals plugin = RealisticAnimals.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        try {
            plugin.getDropManager().dropItems();
            player.sendMessage(ChatColor.GREEN + "[Debug] Você fez os animais droparem itens");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
