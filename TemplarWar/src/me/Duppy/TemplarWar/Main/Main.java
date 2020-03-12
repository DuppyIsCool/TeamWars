package me.Duppy.TemplarWar.Main;
import org.bukkit.plugin.java.JavaPlugin;

import me.Duppy.TemplarWar.Commands.Commands;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	private ConfigManager cfgm;
	public void onEnable() {
		Plugin.plugin = this;
		//Register Events
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		//Setup command executors
		this.getCommand("teams").setExecutor(new Commands());
		this.getCommand("guilds").setExecutor(new Commands());
		
		//Setting up configs (must be done before Team and Guild setup!)
		getConfig().options().copyDefaults(true);
		loadConfigManager();
		loadConfig(); 
		
		//Guild and team setup
		TeamManager.setupTeams();
		GuildManager.setupGuilds();
		
		//Saving config
		saveConfig();
		
		//Finished
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "TemplarWar enabled.");
	}
	
	public void onDisable() {
		//Save teams and guilds to config
		TeamManager.saveTeams();
		GuildManager.saveGuilds();
		
		//Save configs
		cfgm.savePlayers();
		cfgm.saveTeams();
		cfgm.saveGuilds();
		
		//Finished
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "TemplarWar disabled.");
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
