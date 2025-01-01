package me.benrobson.blockvault;

import me.benrobson.blockvault.commands.*;
import me.benrobson.blockvault.util.FileUtil;
import me.benrobson.blockvault.util.VaultUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getConsoleSender;

public final class BlockVault extends JavaPlugin {

    @Override
    public void onEnable() {
        getConsoleSender().sendMessage(getConfig().get("lang.prefix") + "§aPlugin is now enabled!");

        FileUtil fileUtil = new FileUtil(this);
        YamlConfiguration config = fileUtil.getConfig();

        // Register commands
        getCommand("bvstart").setExecutor(new bvstart(this));
        getCommand("bvsubmit").setExecutor(new bvsubmit(this));
        getCommand("bvprogress").setExecutor(new bvprogress(this));
        getCommand("bvleaderboard").setExecutor(new bvleaderboard(this));

        // Register events

        saveDefaultConfig();
        VaultUtil.generateVaultItems(this);
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage(getConfig().get("lang.prefix") + "§cPlugin is now disabled.");
    }
}