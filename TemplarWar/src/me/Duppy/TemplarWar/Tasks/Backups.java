package me.Duppy.TemplarWar.Tasks;

import org.bukkit.scheduler.BukkitRunnable;

import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Main.ConfigManager;
import me.Duppy.TemplarWar.Main.Plugin;
import me.Duppy.TemplarWar.Teams.TeamManager;

public class Backups extends BukkitRunnable {
	private int counter;
	private ConfigManager cm;
    public Backups(int counter, ConfigManager cm) {
        if (counter < 1) {
            throw new IllegalArgumentException("Counter must be greater than 1");
        } else if(cm == null){
        	throw new IllegalArgumentException("ConfigManager cannot be null");
        }
        else {
        	this.counter = counter;
        	this.cm = cm;
        }
    }

    @Override
    //This runs every second
    public void run() {
        if (counter > 0) { 
        	counter--;
        	
        } else {
        	counter = Plugin.plugin.getConfig().getInt("defaults.backuptime");
           	//Save teams and guilds to config
	   		TeamManager.saveTeams();
	   		GuildManager.saveGuilds();
	   		
	   		//Save configs
	   		cm.savePlayers();
	   		cm.saveTeams();
	   		cm.saveGuilds();
        }
    }

}
