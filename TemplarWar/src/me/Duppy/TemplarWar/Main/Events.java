package me.Duppy.TemplarWar.Main;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;

public class Events implements Listener{

	@EventHandler
	public void playersConfigSetup(PlayerJoinEvent e) {
		if(!ConfigManager.getPlayers().contains(e.getPlayer().getUniqueId().toString())) {
			ConfigManager.getPlayers().set(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName());
		}
		else if(ConfigManager.getPlayers().contains(e.getPlayer().getUniqueId().toString())) {
			if(!ConfigManager.getPlayers().getString(e.getPlayer().getUniqueId().toString()).equalsIgnoreCase(e.getPlayer().getName()))
				ConfigManager.getPlayers().set(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void playerScoreboardSetup(PlayerJoinEvent e) {
		e.getPlayer().setScoreboard(GuildManager.mainScoreboard);
	}
	
	@EventHandler
	public void onFishEvent(PlayerFishEvent e) {
		Chunk c = e.getPlayer().getLocation().getChunk();
		if(GuildManager.getChunkOwner(c) != null) {
			e.getPlayer().sendMessage("This chunk is claimed by "+GuildManager.getChunkOwner(c).toString());
		}
	}
}
