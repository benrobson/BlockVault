package me.benrobson.blockvault.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;

public class FileUtil {

    private final Plugin plugin; // Make it an instance variable

    public FileUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the plugin's configuration.
     *
     * @return The plugin's configuration.
     */
    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }
}