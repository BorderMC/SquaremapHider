package net.bordermc.squaremapHider.service;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.jpenilla.squaremap.api.Squaremap;
import xyz.jpenilla.squaremap.api.SquaremapProvider;

public class SquaremapHiderService {
    private final ConfigManager config;
    private final @Nullable Squaremap map;

    public SquaremapHiderService(@NotNull ConfigManager config) {
        this.config = config;
        Squaremap squaremap;
        try {
            squaremap = SquaremapProvider.get();
        } catch (IllegalStateException exception) {
            squaremap = null;
        }
        this.map = squaremap;
    }

    private boolean shouldBeHidden(@NotNull Player player) {
        if (!config.enabled()) return false;
        if (player.hasPermission("squaremaphider.always_show")) return false;
        if (player.hasPermission("squaremaphider.always_hide")) return true;

        // This can be configured in SquareMap itself, but I added it as extra option.
        if (config.shouldInvisHide() && player.isInvisible()) return true;

        // Check if they wear a carved pumpkin or jack o lantern if enabled in config
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null) {
            Material helmetType = helmet.getType();

            if (config.shouldCarvedPumpkinHide() && helmetType == Material.CARVED_PUMPKIN) {
                return true;
            }
        }
        if (config.shouldInVehicleHide() && player.isInsideVehicle()) return true;
        if (config.shouldSneakingHide() && player.isSneaking()) return true;

        // Folia-safe when called from the player's region thread: scan the column above eye level up to max block.
        Location eyeLocation = player.getEyeLocation();
        World world = player.getWorld();
        int blockX = eyeLocation.getBlockX();
        int blockZ = eyeLocation.getBlockZ();
        int startY = Math.max(world.getMinHeight(), eyeLocation.getBlockY());
        int maxY = world.getHighestBlockAt(blockX, blockZ, HeightMap.WORLD_SURFACE).getY();

        for (int y = startY; y <= maxY; y++) {
            Block block = world.getBlockAt(blockX, y, blockZ);
            if (isPassThroughBlock(block.getType())) {
                continue;
            }

            return true;
        }

        return false;
    }

    private boolean isPassThroughBlock(@NotNull Material material) {
        if (material.isAir()) return true;
        if (Tag.IMPERMEABLE.isTagged(material) || material.equals(Material.TINTED_GLASS)) return true;
        return material == Material.COBWEB;
    }

    public void checkPlayer(@NotNull Player player) {
        if (map == null) return;
        if (!config.enabled()) return;

        if (shouldBeHidden(player)) {
            hide(player);
        } else {
            show(player);
        }
    }

    public void show(@NotNull Player player) {
        if (map == null) return;

        map.playerManager().show(player.getUniqueId());
    }

    public void hide(@NotNull Player player) {
        if (map == null) return;
        if (!config.enabled()) return;

        map.playerManager().hide(player.getUniqueId());
    }
}
