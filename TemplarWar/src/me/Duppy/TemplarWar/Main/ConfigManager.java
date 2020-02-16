package me.Duppy.TemplarWar.Main;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	//Variable Declaration
	public Main plugin = Plugin.plugin;
	private ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
	private static FileConfiguration teamcfg;
	private static File teamfile;
	private static FileConfiguration playerscfg;
	private static File playersfile;
	
	
	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		teamfile = new File(plugin.getDataFolder(), "teams.yml");
		playersfile = new File(plugin.getDataFolder(), "players.yml");
		
		if (!teamfile.exists()) {
			plugin.saveResource("teams.yml", false);
			sender.sendMessage(ChatColor.GREEN + "teams.yml has been created");

		}

		if (!playersfile.exists()) {
			plugin.saveResource("players.yml", false);
			sender.sendMessage(ChatColor.GREEN + "players.yml has been created");

		}
		
		teamcfg = YamlConfiguration.loadConfiguration(teamfile);
		playerscfg = YamlConfiguration.loadConfiguration(playersfile);
		
		}

	public FileConfiguration getTeams() {
		return teamcfg;
	}
	
	public FileConfiguration getPlayers() {
		return playerscfg;
	}
	

	public void saveTeams() {
		try {
			teamcfg.save(teamfile);
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "teams.yml has been saved");

		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "Could not save the teams.yml file");
		}
	}
	
	public void savePlayers() {
		try {
			playerscfg.save(playersfile);
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "players.yml has been saved");

		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "Could not save the players.yml file");
		}
	}

	public void reloadTeams() {
		teamcfg = YamlConfiguration.loadConfiguration(teamfile);
		sender.sendMessage(ChatColor.BLUE + "teams.yml has been reload");

	}
	
	public void reloadPlayers() {
			playerscfg = YamlConfiguration.loadConfiguration(playersfile);
			sender.sendMessage(ChatColor.BLUE + "players.yml has been reload");
	}
	
}

