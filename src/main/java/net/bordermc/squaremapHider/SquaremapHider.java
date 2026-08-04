package net.bordermc.squaremapHider;

import net.bordermc.squaremapHider.command.SquaremapHiderCommand;
import net.bordermc.squaremapHider.listener.SquaremapHiderListener;
import net.bordermc.squaremapHider.service.ConfigManager;
import net.bordermc.squaremapHider.service.SquaremapHiderService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SquaremapHider extends JavaPlugin {
    private ConfigManager config;
    private SquaremapHiderService service;

    @Override
    public void onEnable() {
        this.config = new ConfigManager(this);
        saveDefaultConfig();
        config.reload();

        if (Bukkit.getPluginManager().getPlugin("squaremap") == null || !Bukkit.getPluginManager().isPluginEnabled("squaremap")) {
            getLogger().info("[Squaremap Hider] squaremap plugin not found; Plugin will stay idle.");
            return;
        }

        this.service = new SquaremapHiderService(config);

        Objects.requireNonNull(getCommand("squaremaphider"), "Command 'squaremaphider' is not defined in plugin.yml")
                .setExecutor(new SquaremapHiderCommand(config));
        getServer().getPluginManager().registerEvents(
                new SquaremapHiderListener(service), this
        );

        for (Player player : Bukkit.getOnlinePlayers()) {
            service.checkPlayer(player);
        }
    }

    @Override
    public void onDisable() {
        if (service == null) return;

        // Show all players on the map if this plugin gets disabled.
        for (Player player : Bukkit.getOnlinePlayers()) {
            service.show(player);
        }
    }
}
