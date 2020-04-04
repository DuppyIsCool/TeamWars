package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;

public class GuildUnclaim implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender, args)) {
			Player p = (Player) sender;
			Chunk c = p.getLocation().getChunk();
			GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).removeChunk(c);
			MessageManager.sendMessage(p, "guild.chunkunclaimed");
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!= null) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				if(g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER") || g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("ADMIN")) {
					if(GuildManager.getChunkOwner(p.getWorld().getChunkAt(p.getLocation())) != null && GuildManager.getChunkOwner(p.getWorld().getChunkAt(p.getLocation())).getName().equalsIgnoreCase(g.getName())) {
						if(!g.isRaidable() || p.hasPermission("guilds.bypass")) {
							return true;
						}else {MessageManager.sendMessage(p, "guild.error.unclaimduringraid"); return false;}
					}else {MessageManager.sendMessage(p, "guild.error.notownchunk"); return false;}
				}else {MessageManager.sendMessage(p, "guild.error.lowrole"); return false;}
			}else {MessageManager.sendMessage(p, "guild.error.notinguild"); return false;}
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

}
