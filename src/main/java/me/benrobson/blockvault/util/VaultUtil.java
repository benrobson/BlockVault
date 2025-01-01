package me.benrobson.blockvault.util;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaultUtil {
    private final Plugin plugin;
    private final FileUtil fileUtil;

    public VaultUtil(Plugin plugin) {
        this.plugin = plugin;
        this.fileUtil = new FileUtil(plugin); // Pass the plugin instance to FileUtil
    }

    // Method to check if the vault has started
    public boolean hasStarted() {
        return fileUtil.getConfig().getBoolean("vault.started", false); // Use fileUtil to access the config
    }

    // Method to log the start time of the vault challenge
    public void logVaultStart() {
        String currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        fileUtil.getConfig().set("vault.start-time", currentDateTime);
        fileUtil.getConfig().set("vault.started", true);
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

    public List<String> getTopPlayers() {
        // Load vault data to get the collected items count per player
        File vaultDataFile = new File(plugin.getDataFolder(), "vault_data.yml");
        YamlConfiguration vaultData = YamlConfiguration.loadConfiguration(vaultDataFile);

        Map<String, Integer> playerProgress = new HashMap<>();

        // Loop through each player and count their collected items
        for (String playerName : vaultData.getConfigurationSection("vault_data").getKeys(false)) {
            List<String> collectedItems = vaultData.getStringList("vault_data." + playerName + ".collected_items");
            playerProgress.put(playerName, collectedItems.size());
        }

        // Sort players by their collected items (highest to lowest)
        List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(playerProgress.entrySet());
        sortedPlayers.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));  // Sort in descending order

        // Get the top 5 players
        List<String> topPlayers = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedPlayers.size()); i++) {
            topPlayers.add(sortedPlayers.get(i).getKey());
        }

        return topPlayers;
    }

    /**
     * Gets the total number of items in the vault.
     *
     * @return The total number of items in the vault.
     */
    private int getTotalVaultItems() {
        File vaultItemsFile = new File(plugin.getDataFolder(), "vault_items.yml");
        YamlConfiguration vaultItems = YamlConfiguration.loadConfiguration(vaultItemsFile);
        return vaultItems.getConfigurationSection("items").getKeys(false).size();
    }

    /**
     * Gets the number of collected items by a player.
     *
     * @return The total number of collected items.
     */
    private int getCollectedItemsCount() {
        // Load vault data and calculate total collected items
        File vaultDataFile = new File(plugin.getDataFolder(), "vault_data.yml");
        YamlConfiguration vaultData = YamlConfiguration.loadConfiguration(vaultDataFile);

        int collectedItems = 0;
        for (String playerName : vaultData.getConfigurationSection("vault_data").getKeys(false)) {
            collectedItems += vaultData.getStringList("vault_data." + playerName + ".collected_items").size();
        }
        return collectedItems;
    }

    /**
     * Get the initial countdown time in seconds.
     *
     * @return The initial countdown time in seconds.
     */
    private int getInitialCountdownTime() {
        // Assuming that the duration is set somewhere in your config or hardcoded
        return plugin.getConfig().getInt("challenge.duration_in_seconds", 600);  // Default to 10 minutes
    }
}
