package net.bordermc.squaremapHider.service;

import net.bordermc.squaremapHider.SquaremapHider;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class ConfigManager {

    private final SquaremapHider plugin;

    private boolean enabled, shouldCarvedPumpkinHide, shouldJackOLanternHide, shouldInvisHide, shouldSneakingHide, shouldInVehicleHide;

    public ConfigManager(@NotNull SquaremapHider plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        enabled = config.getBoolean("enabled", true);
        shouldCarvedPumpkinHide = config.getBoolean("should_carved_pumkin_hide", false);
        shouldJackOLanternHide = config.getBoolean("should_jack_o_lantern_hide", false);
        shouldInvisHide = config.getBoolean("should_invis_hide", true);
        shouldSneakingHide = config.getBoolean("should_sneaking_hide", true);
        shouldInVehicleHide = config.getBoolean("should_in_vehicle_hide", false);
    }

    protected boolean enabled() {
        return enabled;
    }

    protected boolean shouldCarvedPumpkinHide() {
        return shouldCarvedPumpkinHide;
    }

    protected boolean shouldJackOLanternHide() {
        return shouldJackOLanternHide;
    }

    protected boolean shouldInvisHide() {
        return shouldInvisHide;
    }

    protected boolean shouldSneakingHide() {
        return shouldSneakingHide;
    }

    protected boolean shouldInVehicleHide() {
        return shouldInVehicleHide;
    }
}
