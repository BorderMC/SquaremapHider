package net.bordermc.squaremapHider.listener;

import net.bordermc.squaremapHider.service.SquaremapHiderService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class SquaremapHiderListener implements Listener {
    private final SquaremapHiderService service;

    public SquaremapHiderListener(@NotNull SquaremapHiderService service) {
        this.service = service;
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        service.checkPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        service.show(event.getPlayer());
    }

    @EventHandler
    public void onMove(@NotNull PlayerMoveEvent event) {
        if (event.hasChangedPosition()) service.checkPlayer(event.getPlayer());
    }
}
