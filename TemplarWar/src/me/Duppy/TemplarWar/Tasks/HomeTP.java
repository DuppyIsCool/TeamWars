package me.Duppy.TemplarWar.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;

public class HomeTP extends BukkitRunnable{
	private int counter;
    private Player p;
    private Location l;
    public HomeTP(int counter, Player p, Location l) {
        if (counter < 1) {
            throw new IllegalArgumentException("Counter must be greater than 1");
        } else {
        	this.p = p;
        	this.l = l;
            this.counter = counter;
        }
    }

    @Override
    public void run() {
        // What you want to schedule goes here
    	if(!Bukkit.getOnlinePlayers().contains(p)) {
    		GuildManager.teleportingPlayers.remove(p);
    		this.cancel();
    	}
    	if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) == null) {
    		MessageManager.sendMessage(p, "guild.error.cancelteleportnotinguild");
    		GuildManager.teleportingPlayers.remove(p);
    		this.cancel();
    	}
    		
        if (counter > 0) { 
        	p.sendTitle(ChatColor.WHITE + "Teleporting...",ChatColor.GOLD+""+counter--+"", 1, 20, 1);
        } else {
			p.teleport(l);
			p.sendMessage(ChatColor.BLUE+"Guilds> "+ChatColor.GRAY+ "You have been teleported home");
			GuildManager.teleportingPlayers.remove(p);
            this.cancel();
        }
    }
}
