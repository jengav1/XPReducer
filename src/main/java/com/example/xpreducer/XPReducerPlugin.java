package com.example.xpreducer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class XPReducerPlugin extends JavaPlugin implements Listener {

    private double xpMultiplier;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        xpMultiplier = getConfig().getDouble("xp-multiplier", 0.7);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("XPReducer enabled with multiplier " + xpMultiplier);
    }

    @Override
    public void onDisable() {
        getLogger().info("XPReducer disabled.");
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        int original = e.getAmount();
        int reduced = (int) Math.floor(original * xpMultiplier);
        e.setAmount(reduced);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("xpreload")) {
            if (!(sender instanceof Player) || sender.isOp() || sender.hasPermission("xpreducer.reload")) {
                reloadConfig();
                xpMultiplier = getConfig().getDouble("xp-multiplier", 0.7);
                sender.sendMessage("§a[XPReducer] Config reloaded. Multiplier = " + xpMultiplier);
                return true;
            } else {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return true;
            }
        }
        return false;
    }
}
