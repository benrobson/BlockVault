package me.benrobson.blockvault.commands;

import me.benrobson.blockvault.BlockVault;
import me.benrobson.blockvault.util.GeneratorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class bvgenerate implements CommandExecutor {
    private final BlockVault plugin;

    public bvgenerate(BlockVault plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command must be run by a player.");
            return true;
        }

        if (!sender.hasPermission("blockvault.generate")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        FileConfiguration config = plugin.getConfig();
        GeneratorUtil.loadSchematic(config, plugin);
        sender.sendMessage("Schematic is being generated. Check the console for details.");
        return true;
    }
}
