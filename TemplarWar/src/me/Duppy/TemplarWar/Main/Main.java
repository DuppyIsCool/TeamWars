package me.Duppy.TemplarWar.Main;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		Plugin.plugin = this;
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "TemplarWar enabled.");
	}
	
	public void onDisable() {
		
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "TemplarWar enabled.");
	}

}
