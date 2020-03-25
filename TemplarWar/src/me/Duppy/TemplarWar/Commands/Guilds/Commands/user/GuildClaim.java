package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.Plugin;

public class GuildClaim implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender, args)) {
			Player p = (Player) sender;
			Chunk c = p.getLocation().getChunk();
			GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).addChunk(c);
			MessageManager.sendMessage(p, "guild.chunkclaimed");
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!= null) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				if(g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER") || g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("ADMIN")) {
					if(g.getClaimSize() < Plugin.plugin.getConfig().getInt("defaults.maxclaimcount"))
						return true;
				}
			}
					
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
