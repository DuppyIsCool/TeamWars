package me.Duppy.TemplarWar.Main;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Tasks.Raid;

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
		
		//Prevent players from breaking chunk border blocks
		if(!e.getBlock().getMetadata("SPAWNED").isEmpty())
			e.setCancelled(true);
		
		
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
	//Used to prevent players from opening doors/chests in a guild
	@EventHandler
	public void blockInterract(PlayerInteractEvent event) {
	    if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    	if(GuildManager.getChunkOwner(event.getClickedBlock().getChunk())!= null) {
	    		if(!GuildManager.getChunkOwner(event.getClickedBlock().getLocation().getChunk()).
	    				getGuildMap().containsKey(event.getPlayer().getUniqueId())){
		    		if(!GuildManager.getChunkOwner(event.getClickedBlock().getChunk()).isRaidable()) {
		    				event.setCancelled(true);
		    		}
		    		
		    		else if(event.getClickedBlock().getType().toString().toLowerCase().contains("door") 
		    				|| event.getClickedBlock().getType().toString().toLowerCase().contains("gate")) {
		    			event.setCancelled(true);
		    		}
	    		}
	        }
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
	
	@EventHandler
	//This is for messaging players when they move between claims
	public void onPlayerMove(PlayerMoveEvent e) {
		Chunk leaveChunk = e.getFrom().getChunk();
		Chunk enterChunk = e.getTo().getChunk();
		Player p = e.getPlayer();
		
		Guild leftOwner = GuildManager.getChunkOwner(leaveChunk);
		Guild enterOwner = GuildManager.getChunkOwner(enterChunk);
		
		//Leaving a claimed chunk to wilderness
		if(enterOwner == null && leftOwner != null) {
			p.sendTitle("", ChatColor.GRAY + "Wilderness", 2, 15, 2);
			return;
		}
		
		//Entering between two claims
		if(leftOwner != null && enterOwner != null) {
			if(enterOwner.getName() != leftOwner.getName()) {
				p.sendTitle("", ChatColor.YELLOW + enterOwner.getName(), 2, 15, 2);
				p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY + "Entering territory owned by "+ChatColor.YELLOW+enterOwner.getName());
				return;
			}	
		}
		
		//Entering from wilderness
		if(leftOwner == null && enterOwner != null) {
			p.sendTitle("", ChatColor.YELLOW + enterOwner.getName(), 2, 15, 2);
			p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY + "Entering territory owned by "+ChatColor.YELLOW+enterOwner.getName());
			return;
		}
		
	}
	
	@EventHandler
	//Used to prevent players from taking items from their guild info screens.
	public void onPlayerClickOnItem(InventoryClickEvent e){
        if(e.getRawSlot() == e.getSlot() && e.getView().getTitle().equals(ChatColor.RED + "Guild Display")){
        	e.setCancelled(true);
    	} 
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		//This is used when a player dies, their guild loses one life.
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!=null) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				//If losing a life would make their lives 0
				if((g.getLives()-1) == 0) {
					//Set their lives to 0
					g.setLives(0);
					//Enable them to be raided
					g.setRaidable(true);
					@SuppressWarnings("unused")
					//Create a new timer for how long the raid should be (determined in config)
					BukkitTask task = new Raid(Plugin.plugin.getConfig().getInt("defaults.raidtime")+1,g).runTaskTimer(Plugin.plugin, 0, 20);
				}
				
				//If losing a life would not be 0, just decrease the life by 1
				else if ((g.getLives()-1) > 0)
					g.setLives(g.getLives()-1);
			}
			
		}
	}
}
