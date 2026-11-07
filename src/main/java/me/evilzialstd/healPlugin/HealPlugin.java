package me.evilzialstd.healPlugin;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

public final class HealPlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("heal").setExecutor(this);
        getLogger().info("enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled.");
    }

    private String message(String key) {
        return getConfig().getString("messages." + key, key);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("heal")) {
            return true;
        }

        if (sender instanceof Player player) {
            if (!player.hasPermission("healplugin.heal")) {
                player.sendMessage(message("no-permission"));
                return true;
            }

            AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);

            if (maxHealth == null) {
                player.sendMessage(message("health-unavailable"));
                return true;
            }

            player.setHealth(maxHealth.getValue());
            player.sendMessage(message("healed"));
        } else {
            sender.sendMessage(message("players-only"));
        }

        return true;
    }
}
