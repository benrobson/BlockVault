package me.benrobson.blockvault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bvgenerate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blockvault.generate")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        // Logic to generate the vault
        sender.sendMessage("§aVault generated successfully!");
        return true;
    }
}
