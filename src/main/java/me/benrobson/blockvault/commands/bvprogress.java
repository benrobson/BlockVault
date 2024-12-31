package me.benrobson.blockvault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bvprogress implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Logic to display progress
        sender.sendMessage("§aVault Progress: 75% complete.");
        return true;
    }
}
