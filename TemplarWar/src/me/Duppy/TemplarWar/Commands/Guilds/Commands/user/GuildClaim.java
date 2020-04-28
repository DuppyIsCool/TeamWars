package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.FixedMetadataValue;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.Plugin;
import me.Duppy.TemplarWar.Tasks.ChunkBorderTask;

public class GuildClaim implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender, args)) {
			Player p = (Player) sender;
			Chunk c = p.getLocation().getChunk();
			Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
			g.addChunk(c);
			g.setBalance(g.getBalance()-Plugin.plugin.getConfig().getDouble("defaults.claimcost"));
			MessageManager.sendMessage(p, "guild.chunkclaimed");
			createBorder(c);
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!= null) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				if(g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER") 
						|| g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("ADMIN")){
					if(g.getClaimSize() < Plugin.plugin.getConfig().getInt("defaults.maxclaimcount")) {
						if(isBorderingSelf(p.getLocation().getChunk(),g) || g.getClaimSize() == 0) {
							if(!isBorderingOtherGuild(p.getLocation().getChunk(),g)) {
								if(GuildManager.getChunkOwner(p.getLocation().getChunk()) == null) {
									if(!GuildManager.chunkmap.containsKey(p.getLocation().getChunk())) {
										if((g.getBalance()-Plugin.plugin.getConfig().getDouble("defaults.claimcost")) >= 0) {
											return true;
										}else{p.sendMessage(ChatColor.BLUE +"Guilds> "+ChatColor.GRAY
												+"Your guild is lacking the "
												+ChatColor.YELLOW+"$"+
												Plugin.plugin.getConfig().getDouble("defaults.claimcost") +ChatColor.GRAY
												+" to purchase this chunk"); return false;}
									}else{MessageManager.sendMessage(p, "guild.error.claimtoofast"); return false;}
									
								}else{MessageManager.sendMessage(p, "guild.error.claimingclaimedchunk"); return false;}
								
							}else {MessageManager.sendMessage(p, "guild.error.borderothers");}
							
						}else {MessageManager.sendMessage(p, "guild.error.notborderself");}
					}
					else {
						MessageManager.sendMessage(p, "guild.error.maxclaims"); return false;}
				}
				else {MessageManager.sendMessage(p, "guild.error.lowrole"); return false;}
			}
			else {MessageManager.sendMessage(p, "guild.error.notinguild"); return false;}		
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void createBorder(Chunk c) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		int x = c.getX() * 16;
		int z = c.getZ() * 16;
		int time = 15;
		org.bukkit.World w = c.getWorld();
		
		//All glowstone blocks are tagged with "SPAWNED" so that the program can tell if they are spawned.
		//Blocks are also added to the ChunkBorderManager so they can be queued for despawning.
		for(int i = 1; i < 16; i ++) {
			Block b = w.getHighestBlockAt(x+i,z);
			if(!(b.getState() instanceof InventoryHolder)) {
				if(!b.hasMetadata("SPAWNED"))
					b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
				b.setType(Material.GLOWSTONE);
				blocks.add(b);
			}
		}
		
		for(int i = 0; i < 16; i ++) {
			Block b = w.getHighestBlockAt(x,z+i);
			if(!b.hasMetadata("SPAWNED"))
				if(!(b.getState() instanceof InventoryHolder)) {
					if(!b.hasMetadata("SPAWNED"))
						b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
					b.setType(Material.GLOWSTONE);
					blocks.add(b);
				}
		}
		
		for(int i = 0; i < 15; i ++) {
			Block b = w.getHighestBlockAt(x-i+15,z+15);
			if(!b.hasMetadata("SPAWNED"))
				if(!(b.getState() instanceof InventoryHolder)) {
					if(!b.hasMetadata("SPAWNED"))
						b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
					b.setType(Material.GLOWSTONE);
					blocks.add(b);
				}
		}
		
		for(int i = 1; i < 15; i ++) {
			Block b = w.getHighestBlockAt(x+15,z-i+15);
			if(!(b.getState() instanceof InventoryHolder)) {
				if(!b.hasMetadata("SPAWNED"))
					b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
				b.setType(Material.GLOWSTONE);
				blocks.add(b);
			}
		}
		GuildManager.chunkmap.put(c, new ChunkBorderTask(time+1,time * 5, c,blocks).runTaskTimer(Plugin.plugin, 0, 20));
	}
	
	
	private boolean isBorderingSelf(Chunk c, Guild g) {
		int x = c.getX(); 
		int z = c.getZ();
		World w = c.getWorld();
		Chunk testChunk;
		
		//North
		testChunk = w.getChunkAt(x, z+1);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		//East
		testChunk = w.getChunkAt(x+1, z);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		//South
		testChunk = w.getChunkAt(x, z-1);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		//West
		testChunk = w.getChunkAt(x-1, z);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		return false;
	}
	
	private boolean isBorderingOtherGuild(Chunk c, Guild g) {
		int x = c.getX(); 
		int z = c.getZ();
		World w = c.getWorld();
		Chunk testChunk;
		//North
		testChunk = w.getChunkAt(x, z+1);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(!GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		//East
		testChunk = w.getChunkAt(x+1, z);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(!GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		//South
		testChunk = w.getChunkAt(x, z-1);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(!GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		//West
		testChunk = w.getChunkAt(x-1, z);
		if(GuildManager.getChunkOwner(testChunk) != null)
			if(!GuildManager.getChunkOwner(testChunk).getName().equalsIgnoreCase(g.getName()))
				return true;
		return false;
	}

}
