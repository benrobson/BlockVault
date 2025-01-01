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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String worldName = config.getString("vault.location.world");
            int x = config.getInt("vault.location.x");
            int y = config.getInt("vault.location.y");
            int z = config.getInt("vault.location.z");
            String schematicPath = config.getString("vault.schematic.path");

            // Validate configuration fields
            if (worldName == null || schematicPath == null) {
                plugin.getLogger().severe("Configuration error: Missing required fields 'vault.location.world' or 'vault.schematic.path'.");
                return;
            }

            org.bukkit.World bukkitWorld = Bukkit.getWorld(worldName);
            if (bukkitWorld == null) {
                plugin.getLogger().severe("World '" + worldName + "' is not loaded or does not exist. Check if the world is properly loaded in the server.");
                return;
            }

            File schematicFile = new File(plugin.getDataFolder(), schematicPath);
            if (!schematicFile.exists()) {
                plugin.getLogger().severe("Schematic file '" + schematicFile.getAbsolutePath() + "' does not exist. Ensure the file is in the correct location.");
                return;
            }

            plugin.getLogger().info("Starting schematic loading...");
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

                Bukkit.getScheduler().runTask(plugin, () -> {
                    try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance()
                            .newEditSession(worldEditWorld)) {
                        ClipboardHolder holder = new ClipboardHolder(clipboard);
                        holder.createPaste(editSession).to(location).ignoreAirBlocks(true).build();
                        plugin.getLogger().info("Schematic pasted successfully at: " + x + ", " + y + ", " + z);
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error pasting schematic: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to load schematic file: " + e.getMessage());
                plugin.getLogger().severe("Ensure the file is not corrupted and is in a valid format.");
                e.printStackTrace();
            } catch (Exception e) {
                plugin.getLogger().severe("An unexpected error occurred while loading the schematic: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
