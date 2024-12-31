package me.benrobson.blockvault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bvstart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blockvault.start")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        // Logic to start the vault challenge
        sender.sendMessage("§aVault challenge started!");
        return true;
    }
}
