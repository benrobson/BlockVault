package me.benrobson.blockvault;

import me.benrobson.blockvault.commands.*;
import me.benrobson.blockvault.util.VaultUtil;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getConsoleSender;

public final class BlockVault extends JavaPlugin {

    @Override
    public void onEnable() {
        getConsoleSender().sendMessage(getConfig().get("lang.prefix") + "§aPlugin is now enabled!");

        // Register commands
        getCommand("bvstart").setExecutor(new bvstart(this));
        getCommand("bvgenerate").setExecutor(new bvgenerate(this));
        getCommand("bvadd").setExecutor(new bvsubmit(this));
        getCommand("bvprogress").setExecutor(new bvprogress());
        getCommand("bvleaderboard").setExecutor(new bvleaderboard());

        // Register events

        saveDefaultConfig();
        VaultUtil.generateVaultItems(this);
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage(getConfig().get("lang.prefix") + "§cPlugin is now disabled.");
    }
}