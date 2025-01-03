package me.benrobson.blockvault.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ScheduleUtil {
    private final Plugin plugin;
    private final VaultUtil vaultUtil;
    private final FileUtil fileUtil;

    public ScheduleUtil(Plugin plugin) {
        this.plugin = plugin;
        this.vaultUtil = new VaultUtil(plugin);
        this.fileUtil = new FileUtil(plugin);
    }

    public static void scheduleVaultStateTask(Plugin plugin, VaultUtil vaultUtil) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            vaultUtil.updateVaultState(null);
        }, 0L, 20L * 60L * 5L); // Run every 5 minutes
    }
}
