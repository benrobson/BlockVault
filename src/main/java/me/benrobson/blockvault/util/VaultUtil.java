package me.benrobson.blockvault.util;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaultUtil {

    private final FileConfiguration config;

    public VaultUtil(FileConfiguration config) {
        this.config = config;
    }

    // Method to check if the vault has started
    public boolean hasStarted() {
        return config.getBoolean("vault.started", false);
    }

    // Method to log the start time of the vault challenge
    public void logVaultStart() {
        String currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        config.set("vault.start-time", currentDateTime);
        config.set("vault.started", true);
    }

    public static void generateVaultItems(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "vault_items.yml");

        if (file.exists()) {
            plugin.getLogger().info("vault_items.yml already exists. Skipping generation.");
            return;
        }

        Map<String, String> itemData = new HashMap<>();

        for (Material material : Material.values()) {
            // Add materials that are either blocks or items
            if (material.isBlock() || material.isItem()) {
                itemData.put(material.name().toLowerCase(), "COMMON"); // Default category
            }
        }

        YamlConfiguration yaml = new YamlConfiguration();
        yaml.createSection("items", itemData);

        try {
            yaml.save(file);
            plugin.getLogger().info("Generated vault_items.yml with all blocks and items set to default category COMMON.");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save vault_items.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Formats a Material name to a user-friendly display format.
     *
     * @param material The Material to format.
     * @return The formatted name.
     */
    public static String formatMaterialName(Material material) {
        String[] words = material.name().toLowerCase().split("_");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return formattedName.toString().trim();
    }

    public static void updateRecentContributions(Player player, String itemName, Plugin plugin) {
        // Load the vault data file
        File vaultDataFile = new File(plugin.getDataFolder(), "vault_data.yml");
        YamlConfiguration vaultData = YamlConfiguration.loadConfiguration(vaultDataFile);

        // Get the current list of recent contributions for the player
        String playerPath = "vault_data." + player.getName() + ".recent_contributions";
        List<String> recentContributions = vaultData.getStringList(playerPath);

        // Ensure the list has no more than 5 entries
        if (recentContributions.size() >= 5) {
            recentContributions.remove(0); // Remove the oldest contribution
        }

        // Add the new contribution to the list
        recentContributions.add(player.getName() + " collected " + itemName);

        // Save the updated list back into the YML file
        vaultData.set(playerPath, recentContributions);
        try {
            vaultData.save(vaultDataFile);
        } catch (IOException e) {
            player.sendMessage("§cFailed to save recent contributions.");
            e.printStackTrace();
        }
    }
}