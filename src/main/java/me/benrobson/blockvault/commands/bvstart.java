package me.benrobson.blockvault.commands;

import me.benrobson.blockvault.util.VaultUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class bvstart implements CommandExecutor {
    private final Plugin plugin;
    private final VaultUtil vaultUtil;

    public bvstart(Plugin plugin) {
        this.plugin = plugin;
        this.vaultUtil = new VaultUtil(plugin.getConfig());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check for the necessary permission
        if (!sender.hasPermission("blockvault.start")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        // Check if the vault challenge has already been started
        if (vaultUtil.hasStarted()) {
            sender.sendMessage("§cThe vault challenge has already been started.");
            return true;
        }

        // Log the start date and time
        vaultUtil.logVaultStart();
        plugin.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "The vault challenge has started.");
        return true;
    }
}