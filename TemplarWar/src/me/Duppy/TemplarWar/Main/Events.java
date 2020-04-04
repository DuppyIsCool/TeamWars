package me.Duppy.TemplarWar.Main;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;

import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Tasks.Raid;
import me.Duppy.TemplarWar.Teams.TeamManager;

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
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Chunk c = e.getBlock().getChunk();
		
		//Prevent players from breaking chunk border blocks
		if(!e.getBlock().getMetadata("SPAWNED").isEmpty()) {
			e.getBlock().setType((Material) e.getBlock().getMetadata("SPAWNED").get(0).value());
			e.getBlock().removeMetadata("SPAWNED", Plugin.plugin);
			e.setCancelled(true);
		}	
		
		//Checks to see if the block is claimed
		if(GuildManager.getChunkOwner(c) != null) {
			//If the player is not apart of the guild the chunk is claimed by, cancel event
			if(!GuildManager.getChunkOwner(c).getGuildMap().containsKey(p.getUniqueId()) && !p.hasPermission("guilds.bypass"))
				e.setCancelled(true);
		}
	}
	
	//Prevent Player damage
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) { // Triggered whenever an entity damages another entity
	    if(!(e.getEntity() instanceof Player)) {
	        // Victim is not a player
	        return;
	    }
	 
	    // Cast victim
	    Player victim = (Player) e.getEntity();
	 
	    // Create an empty player object to store attacker
	    Player attacker = null;
	 
	    if(e.getDamager() instanceof Player) {
	        // Attacker is a player (melee damage)
	        attacker = (Player) e.getDamager();
	    } else if(e.getDamager() instanceof Arrow) {
	        // Attacker is an arrow (projectile damage)
	        Arrow arrow = (Arrow) e.getDamager();
	        if(!(arrow.getShooter() instanceof Player)) {
	            // Arrow was not fired by a player
	            return;
	        }
	        // Cast attacker
	        attacker = (Player) arrow.getShooter();
	    } else if(e.getDamager() instanceof ThrownPotion) {
	        /* Splash potion of harming triggers this event because it deals direct damage,
	        but we will deal with that kind of stuff in PotionSplashEvent instead */
	        return;
	    }
	 
	    // It's possible to shoot yourself
	    if(victim == attacker) {
	        return;
	    }
	    // Just a quick null check for the attacker, in case I missed something
	    if(attacker == null) {
	        return;
	    }
	 
	    // Check the teams
	    if(TeamManager.getTeam(attacker.getUniqueId())!= null) {
	    	if(TeamManager.getTeam(victim.getUniqueId()) != null)
	    		if(TeamManager.getTeam(attacker.getUniqueId()).getName().equalsIgnoreCase(
	    				TeamManager.getTeam(victim.getUniqueId()).getName()))
	        e.setCancelled(true);
	    }
	}
	
	//Prevent Team potion Damage
	@EventHandler
	public void onPotionSplash(PotionSplashEvent e) {
	    // Is this a dangerous potion? (Probably not the most efficient way to check this. Tips?)
	    boolean cancel = true;
	    for(PotionEffect effect : e.getEntity().getEffects()) {
	        if(effect.getType().getName().equalsIgnoreCase("harm") || // Splash potion of harming
	        effect.getType().getName().equalsIgnoreCase("poison")) { // Splash potion of poison
	            cancel = false;
	        }
	    }
	    if(cancel) return;
	 
	    // Figure out who threw it
	    if(!(e.getPotion().getShooter() instanceof Player)) {
	        // The potion was not thrown by a player. Probably just some crazy witch again.
	        return;
	    }
	 
	    // Cast attacker
	    Player attacker = (Player) e.getPotion().getShooter();
	 
	    // Check each entity that was hit by this potion
	    Player victim = null;
	    for(LivingEntity entity : e.getAffectedEntities()) {
	        if(entity instanceof Player) {
	            // This victim is a player, cast him/her
	            victim = (Player) entity;
	 
	            // You can easily hit yourself with a splash potion.
	            if(victim == attacker) {
	                // Yeah, this is the same player. Let him burn! Next!
	                continue;
	            }
	 
	            // Check teams
	            if(TeamManager.getTeam(attacker.getUniqueId())!= null) {
	    	    	if(TeamManager.getTeam(victim.getUniqueId()) != null)
	    	    		if(TeamManager.getTeam(attacker.getUniqueId()).getName().equalsIgnoreCase(
	    	    				TeamManager.getTeam(victim.getUniqueId()).getName()))
	    	    			// Reduce the effect of this potion to zero (victim only)
	    					e.setIntensity(victim, 0);
	            }
	        }
	    }
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Chunk c = e.getBlockPlaced().getChunk();
		if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) != null) {
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).isRaidable() && !p.hasPermission("guilds.bypass") 
					&& GuildManager.getChunkOwner(c) != null) {
				//Placing in own territory
				if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getName().equalsIgnoreCase(GuildManager.getChunkOwner(c).getName()))
					p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY+"You cannot place blocks during a raid!");
					e.setCancelled(true);
			}
		}
		
		//Checks to see if the block is claimed
		if(GuildManager.getChunkOwner(c) != null && !p.hasPermission("guilds.bypass")) {
			//If the player is not apart of the guild the chunk is claimed by, cancel event
			if(!GuildManager.getChunkOwner(c).getGuildMap().containsKey(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}
	//Used to prevent players from opening doors/chests in a guild
	@EventHandler
	public void blockInterract(PlayerInteractEvent event) {
	    if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    	if(GuildManager.getChunkOwner(event.getClickedBlock().getChunk())!= null && !event.getPlayer().hasPermission("guilds.bypass")) {
	    		//Checks for container blocks
	    		if(event.getClickedBlock().getState() instanceof InventoryHolder) {
		    		if(!GuildManager.getChunkOwner(event.getClickedBlock().getLocation().getChunk()).
		    				getGuildMap().containsKey(event.getPlayer().getUniqueId())){
		    			//If the guild is not raidable , deny
			    		if(!GuildManager.getChunkOwner(event.getClickedBlock().getChunk()).isRaidable()) {
			    				event.setCancelled(true);
			    		}
		    		}
	    		}
	    		//If not a container block, if door and not raidable deny
	    		else if(event.getClickedBlock().getType().toString().toLowerCase().contains("door") 
	    				|| event.getClickedBlock().getType().toString().toLowerCase().contains("gate")) {
	    			event.setCancelled(true);
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
	//This checks if they moved during 'guild home' teleport
	public void onTeleportMove(PlayerMoveEvent e) {
		Location movedFrom = e.getFrom();
        Location movedTo = e.getTo();
        //Checks if the player moved, ignoring yaw and pitch
        if (movedFrom.getBlockX() != movedTo.getBlockX() || 
        		movedFrom.getBlockY() != movedTo.getBlockY() || 
        		movedFrom.getBlockZ() != movedTo.getBlockZ()) {
        	
        	if(GuildManager.teleportingPlayers.containsKey(e.getPlayer())) {
    			MessageManager.sendMessage(e.getPlayer(), "guild.error.movedduringteleport");
    			GuildManager.teleportingPlayers.get(e.getPlayer()).cancel();
    			GuildManager.teleportingPlayers.remove(e.getPlayer());
    		}
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
