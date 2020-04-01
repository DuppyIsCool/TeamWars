package me.Duppy.TemplarWar.Commands.Teams.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsLeave implements CMD {

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Team t = TeamManager.getTeam(p.getUniqueId());
		t.removePlayer(p);
		sender.sendMessage(ChatColor.BLUE +"Teams> "+ChatColor.GRAY+ "You have left "+ChatColor.YELLOW+""+t.getName());
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("teams.leave")) {
				if(TeamManager.getTeam(p.getUniqueId()) != null) {
					if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) == null)
						return true;
					else {
						MessageManager.sendMessage(p, "team.error.leaveinguild");
						return false;
					}
						
				}
				else {
					MessageManager.sendMessage(p, "team.error.notinteam");
					return false;
				}
			}
			else {
				MessageManager.sendMessage(p, "error.nopermission");
				return false;
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
