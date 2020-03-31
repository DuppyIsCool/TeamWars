package me.Duppy.TemplarWar.Main;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
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
	//Sets players to the scoreboard used for teams
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
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Chunk c = e.getBlock().getChunk();
		
		//Checks to see if the block is claimed
		if(GuildManager.getChunkOwner(c) != null) {
			//If the player is not apart of the guild the chunk is claimed by, cancel event
			if(!GuildManager.getChunkOwner(c).getGuildMap().containsKey(p.getUniqueId()))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Chunk c = e.getBlock().getChunk();
		
		//Checks to see if the block is claimed
		if(GuildManager.getChunkOwner(c) != null) {
			//If the player is not apart of the guild the chunk is claimed by, cancel event
			if(!GuildManager.getChunkOwner(c).getGuildMap().containsKey(p.getUniqueId()))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	//Prevents creepers and tnt from exploding guild claims if the guild is not raidable
	public void onEntityExplode(EntityExplodeEvent event) {
	    if (event.getEntity() instanceof Creeper || event.getEntity() instanceof TNTPrimed) {
	        for (Block block : new ArrayList<Block>(event.blockList()))
	            if(GuildManager.getChunkOwner(block.getChunk())!= null)
	            	if(!GuildManager.getChunkOwner(block.getChunk()).isRaidable())
	            		event.blockList().remove(block);
        }
    }
}
