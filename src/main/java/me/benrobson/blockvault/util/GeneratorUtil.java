package me.benrobson.blockvault.util;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GeneratorUtil {

    public static void loadSchematic(FileConfiguration config, Plugin plugin) {
        // Load vault settings from config.yml
        String worldName = config.getString("vault.location.world");
        int x = config.getInt("vault.location.x");
        int y = config.getInt("vault.location.y");
        int z = config.getInt("vault.location.z");
        String schematicPath = config.getString("vault.schematic.path");

        org.bukkit.World bukkitWorld = Bukkit.getWorld(worldName);
        if (bukkitWorld == null) {
            plugin.getLogger().severe("World '" + worldName + "' is not loaded or does not exist.");
            return;
        }

        File schematicFile = new File(plugin.getDataFolder(), schematicPath);
        if (!schematicFile.exists()) {
            plugin.getLogger().severe("Schematic file '" + schematicPath + "' does not exist.");
            return;
        }

        // Load and paste the schematic
        try (FileInputStream fis = new FileInputStream(schematicFile)) {
            ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
            if (format == null) {
                plugin.getLogger().severe("Invalid schematic format for file: " + schematicPath);
                return;
            }

            ClipboardReader reader = format.getReader(fis);
            Clipboard clipboard = reader.read();
            World worldEditWorld = BukkitAdapter.adapt(bukkitWorld);
            BlockVector3 location = BlockVector3.at(x, y, z);

            try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance()
                    .newEditSession(worldEditWorld)) {
                ClipboardHolder holder = new ClipboardHolder(clipboard);
                holder.createPaste(editSession).to(location).build();
                plugin.getLogger().info("Schematic pasted successfully at: " + x + ", " + y + ", " + z);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load schematic: " + e.getMessage());
            e.printStackTrace();
        }
    }
}