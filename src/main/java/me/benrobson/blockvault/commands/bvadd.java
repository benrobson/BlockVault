package me.benrobson.blockvault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bvadd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blockvault.add")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUsage: /vault add <item/block>");
            return true;
        }

        // Logic to add the specified item/block
        String block = args[0];
        sender.sendMessage("§aAdded " + block + " to the vault!");
        return true;
    }
}
