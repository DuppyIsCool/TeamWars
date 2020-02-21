package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Teams.TeamManager;

public class GuildCreate implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Guild g = new Guild(p.getUniqueId());
		g.setName(args[1]);
		GuildManager.addGuild(g);
		MessageManager.sendMessage(sender, "guild.guildcreated");	
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) == null)
				if(TeamManager.getTeam(p.getUniqueId())!= null)
					return true;
		}
		return false;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
