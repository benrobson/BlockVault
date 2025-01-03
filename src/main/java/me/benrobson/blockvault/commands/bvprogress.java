package me.benrobson.blockvault.commands;

import me.benrobson.blockvault.util.VaultUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class bvprogress implements CommandExecutor {
    private final Plugin plugin;
    private final VaultUtil vaultUtil;

    public bvprogress(Plugin plugin) {
        this.plugin = plugin;
        this.vaultUtil = new VaultUtil(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        if (!vaultUtil.hasStarted()) {
            sender.sendMessage("§cYou cannot check progress as the Vault has not been opened yet.");
            return true;
        }

        Player player = (Player) sender;

        // Load vault items and vault data
        File vaultItemsFile = new File(plugin.getDataFolder(), "vault_items.yml");
        File vaultDataFile = new File(plugin.getDataFolder(), "vault_data.yml");

        YamlConfiguration vaultItems = YamlConfiguration.loadConfiguration(vaultItemsFile);
        YamlConfiguration vaultData = YamlConfiguration.loadConfiguration(vaultDataFile);

        // Get the total number of items in the vault
        int totalItems = vaultItems.getConfigurationSection("items").getKeys(false).size();

        // Get the total number of collected items for this player
        List<String> playerCollectedItems = vaultData.getStringList("vault_data." + player.getName() + ".collected_items");
        int collectedItemCount = playerCollectedItems.size();

        // Calculate the percentage of progress
        int progressPercentage = (int) ((double) collectedItemCount / totalItems * 100);

        // Create a progress bar
        StringBuilder progressBar = new StringBuilder("§a[");
        int barLength = 20;
        int progressLength = (int) ((progressPercentage / 100.0) * barLength);
        for (int i = 0; i < barLength; i++) {
            if (i < progressLength) {
                progressBar.append("§a#");
            } else {
                progressBar.append("§7#");
            }
        }
        progressBar.append("§a]");

        // Display progress
        player.sendMessage("§aVault Progress: " + progressPercentage + "% complete.");
        player.sendMessage(progressBar.toString());

        // Display the last 5 contributions
        List<String> recentContributions = vaultData.getStringList("vault_data." + player.getName() + ".recent_contributions");
        if (recentContributions.isEmpty()) {
            player.sendMessage("§cNo recent contributions.");
        } else {
            player.sendMessage("§eLast 5 Contributions:");
            for (int i = Math.max(0, recentContributions.size() - 5); i < recentContributions.size(); i++) {
                player.sendMessage("§7- " + recentContributions.get(i));
            }
        }

        return true;
    }
}
