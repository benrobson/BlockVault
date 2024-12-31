package me.benrobson.blockvault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bvleaderboard implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Logic to display leaderboard
        sender.sendMessage("§aVault Leaderboard:\n1. Player1 - 100 points\n2. Player2 - 80 points");
        return true;
    }
}
