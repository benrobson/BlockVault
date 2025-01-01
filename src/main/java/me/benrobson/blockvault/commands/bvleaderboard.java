package me.benrobson.blockvault.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class bvleaderboard implements CommandExecutor {
    private final Plugin plugin;

    public bvleaderboard(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can view the leaderboard!");
            return true;
        }

        // Load the player data file
        File vaultDataFile = new File(plugin.getDataFolder(), "vault_data.yml");
        YamlConfiguration vaultData = YamlConfiguration.loadConfiguration(vaultDataFile);

        // Get all player points data
        Set<String> playerNames = vaultData.getConfigurationSection("vault_data").getKeys(false);
        Map<String, Integer> playerPoints = new HashMap<>();

        for (String playerName : playerNames) {
            int points = vaultData.getInt("vault_data." + playerName + ".points");
            playerPoints.put(playerName, points);
        }

        // Sort players by points in descending order
        List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(playerPoints.entrySet());
        sortedPlayers.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        // Display top 10 players
        sender.sendMessage("§aVault Leaderboard:");

        int topPlayersCount = Math.min(10, sortedPlayers.size());
        for (int i = 0; i < topPlayersCount; i++) {
            Map.Entry<String, Integer> entry = sortedPlayers.get(i);
            String playerName = entry.getKey();
            int points = entry.getValue();
            sender.sendMessage("§f" + (i + 1) + ". " + playerName + " - " + points + " points");
        }

        return true;
    }
}