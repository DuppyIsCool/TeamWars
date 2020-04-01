package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

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
			GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).addChunk(c);
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
						if(GuildManager.getChunkOwner(p.getLocation().getChunk()) == null) {
							if(!GuildManager.chunkmap.containsKey(p.getLocation().getChunk())) {
								return true;
							}else{MessageManager.sendMessage(p, "guild.error.claimtoofast"); return false;}
						}
						else{MessageManager.sendMessage(p, "guild.error.claimingclaimedchunk"); return false;}
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
		HashMap<Block,Integer> blockmap = new HashMap<Block,Integer>();
		int x = c.getX() * 16;
		int z = c.getZ() * 16;
		org.bukkit.World w = c.getWorld();
		int time = 15;
		
		//All glowstone blocks are tagged with "SPAWNED" so that the program can tell if they are spawned.
		//Blocks are also added to the ChunkBorderManager so they can be queued for despawning.
		for(int i = 1; i < 16; i ++) {
			Block b = w.getHighestBlockAt(x+i,z).getLocation().add(0,-1,0).getBlock();
			if(!b.hasMetadata("SPAWNED"))
				b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
			b.setType(Material.GLOWSTONE);
			blockmap.put(b, time);
		}
		
		for(int i = 0; i < 16; i ++) {
			Block b = w.getHighestBlockAt(x,z+i).getLocation().add(0,-1,0).getBlock();
			if(!b.hasMetadata("SPAWNED"))
				b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
			b.setType(Material.GLOWSTONE);
			blockmap.put(b, time);
		}
		
		for(int i = 0; i < 15; i ++) {
			Block b = w.getHighestBlockAt(x-i+15,z+15).getLocation().add(0,-1,0).getBlock();
			if(!b.hasMetadata("SPAWNED"))
				b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
			b.setType(Material.GLOWSTONE);
			blockmap.put(b, time);
		}
		
		for(int i = 1; i < 15; i ++) {
			Block b = w.getHighestBlockAt(x+15,z-i+15).getLocation().add(0,-1,0).getBlock();
			if(!b.hasMetadata("SPAWNED"))
				b.setMetadata("SPAWNED", new FixedMetadataValue(Plugin.plugin, b.getType()));
			b.setType(Material.GLOWSTONE);
			blockmap.put(b, time);
		}
		
		BukkitTask task = new ChunkBorderTask(time+1,time * 5, c,blockmap).runTaskTimer(Plugin.plugin, 0, 20);
		GuildManager.chunkmap.put(c, task);
	}

}
