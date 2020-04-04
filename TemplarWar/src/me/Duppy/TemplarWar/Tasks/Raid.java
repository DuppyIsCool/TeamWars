package me.Duppy.TemplarWar.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Main.Plugin;

public class Raid extends BukkitRunnable{
	private int counter;
    private Guild g;
    public Raid(int counter, Guild g) {
        if (counter < 1) {
            throw new IllegalArgumentException("Counter must be greater than 1");
        } else {
        	this.g = g;
            this.counter = counter;
            Bukkit.broadcastMessage(ChatColor.BLUE + "Raids> "+ChatColor.RED + ""+ChatColor.BOLD + g.toString() + " is now raidable!");
        }
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        if (counter > 0) { 
        	if(!GuildManager.getGuildList().contains(g))
        		this.cancel();
        	counter--;
        } else {
        	g.setLives(Plugin.plugin.getConfig().getInt("defaults.maxlives"));
        	Bukkit.broadcastMessage(ChatColor.BLUE + "Raids> "+ChatColor.GREEN +""+ ChatColor.BOLD+ "The raid on "+g.toString() + " has ended!");
            this.cancel();
        }
    }
}
