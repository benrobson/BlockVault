package me.benrobson.blockvault;

import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getConsoleSender;

public final class BlockVault extends JavaPlugin {

    @Override
    public void onEnable() {
        getConsoleSender().sendMessage("§8[§6BlockVault§8] §aPlugin is now enabled!");

        // Register commands

        // Register events
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage("§8[§6BlockVault§8] §cPlugin is now disabled. Thanks for playing!");
    }
}