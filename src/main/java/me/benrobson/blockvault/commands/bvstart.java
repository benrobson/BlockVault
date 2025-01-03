package me.benrobson.blockvault.commands;

import me.benrobson.blockvault.util.FileUtil;
import me.benrobson.blockvault.util.VaultUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class bvstart implements CommandExecutor {
    private final Plugin plugin;
    private final VaultUtil vaultUtil;
    private final FileUtil fileUtil;

    public bvstart(Plugin plugin) {
        this.plugin = plugin;
        this.vaultUtil = new VaultUtil(plugin);
        this.fileUtil = new FileUtil(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check for the necessary permission
        if (!sender.hasPermission("blockvault.start")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }

        // Check if the vault has already started
        if (vaultUtil.hasStarted()) {
            sender.sendMessage(ChatColor.RED + "The Vault challenge has already started.");
            return true;
        }

        // Log the start date and time
        try {
            String currentDateTime = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Update the configuration
            fileUtil.getConfig().set("vault.start-time", currentDateTime);
            fileUtil.getConfig().set("vault.started", true);

            sender.sendMessage(ChatColor.GREEN + "The vault challenge has started, the vault is open!");
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error occurred while starting the vault.");
            e.printStackTrace();
        }

        return true;
    }
}