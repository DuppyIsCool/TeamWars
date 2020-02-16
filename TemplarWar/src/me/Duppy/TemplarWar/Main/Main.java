package me.Duppy.TemplarWar.Main;
import org.bukkit.plugin.java.JavaPlugin;

import me.Duppy.TemplarWar.Commands.Commands;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	private ConfigManager cfgm;
	public void onEnable() {
		Plugin.plugin = this;
		this.getCommand("teams").setExecutor(new Commands());
		getConfig().options().copyDefaults(true);
		loadConfigManager();
		loadConfig(); 
		TeamManager.setupTeams();
		saveConfig();
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "TemplarWar enabled.");
	}
	
	public void onDisable() {
		TeamManager.saveTeams();
		cfgm.savePlayers();
		cfgm.saveTeams();
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "TemplarWar enabled.");
	}
	public void loadConfigManager() {
		cfgm = new ConfigManager();
		cfgm.setup();
	}
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

}
