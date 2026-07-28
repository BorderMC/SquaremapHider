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
    private final SquaremapHiderService service;
    private final ConfigManager config;

    public SquaremapHider() {
        this.config = new ConfigManager(this);
        this.service = new SquaremapHiderService(config);
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("squaremap") == null || !Bukkit.getPluginManager().isPluginEnabled("squaremap")) {
            getLogger().info("[Squaremap Hider] squaremap plugin not found; Plugin will stay idle.");
            return;
        }

        Objects.requireNonNull(getCommand("endportal"), "Command 'endportal' is not defined in plugin.yml")
                .setExecutor(new SquaremapHiderCommand(config, service));
        getServer().getPluginManager().registerEvents(
                new SquaremapHiderListener(service), this
        );
    }

    @Override
    public void onDisable() {
        // Show all players on the map if this plugin gets disabled.
        for (Player player : Bukkit.getOnlinePlayers()) {
            service.show(player);
        }
    }
}
