package me.benrobson.blockvault;

import me.benrobson.blockvault.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getConsoleSender;

public final class BlockVault extends JavaPlugin {

    @Override
    public void onEnable() {
        getConsoleSender().sendMessage(getConfig().get("lang.prefix") + "§aPlugin is now enabled!");

        // Register commands
        getCommand("bvstart").setExecutor(new bvstart());
        getCommand("bvgenerate").setExecutor(new bvgenerate());
        getCommand("bvadd").setExecutor(new bvadd());
        getCommand("bvprogress").setExecutor(new bvprogress());
        getCommand("bvleaderboard").setExecutor(new bvleaderboard());

        // Register events
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage(getConfig().get("lang.prefix") + "§cPlugin is now disabled.");
    }
}