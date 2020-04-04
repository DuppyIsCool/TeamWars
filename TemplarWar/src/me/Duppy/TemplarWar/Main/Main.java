package me.Duppy.TemplarWar.Main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.Duppy.TemplarWar.Commands.Commands;
import me.Duppy.TemplarWar.Econ.VaultAPI;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Tasks.Backups;
import me.Duppy.TemplarWar.Tasks.InviteCleaner;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	private ConfigManager cfgm;
	private static VaultAPI vault;
	public void onEnable() {
		Plugin.plugin = this;
		vault = new VaultAPI(this);
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
		
		//This will occur if the plugin was being reloaded. It fixes any scoreboard issues
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(GuildManager.mainScoreboard);
		}
		
		//Start the backup task
		@SuppressWarnings("unused")
		BukkitTask backupTask = new Backups(Plugin.plugin.getConfig().getInt("defaults.backuptime")+1,cfgm).runTaskTimer(Plugin.plugin, 0, 20);
		//Start invite timer task
		@SuppressWarnings("unused")
		BukkitTask inviteTask = new InviteCleaner(Plugin.plugin.getConfig().getInt("defaults.invitetime")+1).runTaskTimer(Plugin.plugin, 0, 20);
		
		//Upkeep code
		Timer timer = new Timer();
        LocalDateTime tomorrowMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1);
        Date date = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GuildManager.applyUpkeep();
            }
        }, date);
	}
	
	public void onDisable() {
		//Save teams and guilds to config
		TeamManager.saveTeams();
		GuildManager.saveGuilds();
		
		//Cancel any active  chunk border tasks, reverting border blocks to normal blocks to prevent issues.
		//This will only occur to prevent reload glitching of border blocks, or upon crashes
		for(Entry<Chunk, BukkitTask> e : GuildManager.chunkmap.entrySet()) {
			e.getValue().cancel();
		}
		
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
	
	public static VaultAPI getVault() { 
		return vault; 
	}	
}