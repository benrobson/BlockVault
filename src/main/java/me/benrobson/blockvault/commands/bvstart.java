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

        if (vaultUtil.hasStarted()) {
            sender.sendMessage("§cYou cannot submit items as the Vault has not been opened yet.");
            return true;
        }

        // Log the start date and time
        vaultUtil.logVaultStart();
        plugin.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "The vault challenge has started, the vault is open!.");
        return true;
    }
}