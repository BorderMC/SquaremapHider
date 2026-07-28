package net.bordermc.squaremapHider.command;

import net.bordermc.squaremapHider.service.ConfigManager;
import net.bordermc.squaremapHider.service.SquaremapHiderService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SquaremapHiderCommand implements CommandExecutor {
    private final ConfigManager config;
    private final SquaremapHiderService service;

    public SquaremapHiderCommand(@NotNull ConfigManager config, @NotNull SquaremapHiderService service) {
        this.config = config;
        this.service = service;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            config.reload();
            sender.sendMessage(Component.text("SquaremapHider configuration reloaded.", NamedTextColor.GREEN));
            return true;
        }

        sender.sendMessage(Component.text("Usage: /" + label + " reload", NamedTextColor.RED));
        return true;
    }
}
