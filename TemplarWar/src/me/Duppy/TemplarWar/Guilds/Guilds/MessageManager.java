package me.Duppy.TemplarWar.Guilds.Guilds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Main.Plugin;
import net.md_5.bungee.api.ChatColor;

public class MessageManager {
	public static void sendMessage(CommandSender sender,String path) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Plugin.plugin.getConfig().getString("messages." + path)));
	}
	
	public static void sendMessage(Player p, String path) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&',Plugin.plugin.getConfig().getString("messages." + path)));
	}
}
