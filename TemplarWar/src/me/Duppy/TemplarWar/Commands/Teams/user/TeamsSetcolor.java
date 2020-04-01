package me.Duppy.TemplarWar.Commands.Teams.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Teams.TeamManager;

public class TeamsSetcolor implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			TeamManager.getTeam(p.getUniqueId()).setColor(args[1]);
			for(Guild g : GuildManager.getGuildList())
				g.updateColor();
			MessageManager.sendMessage(p, "team.colorset");
		}
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(TeamManager.getTeam(p.getUniqueId()) != null) {
				switch(args[1].toLowerCase()) {
					case("red"):
						return true;
					case("blue"):
						return true;
					case("green"):
						return true;
					case("yellow"):
						return true;
					default:
						MessageManager.sendMessage(p, "team.error.invalidcolor");
						return false;
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
