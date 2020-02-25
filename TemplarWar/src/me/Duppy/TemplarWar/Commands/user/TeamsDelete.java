package me.Duppy.TemplarWar.Commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsDelete implements CMD{

	@Override
	public void Execute(CommandSender sender, String[] args) {
		Team t = TeamManager.getTeam(args[1]);
		TeamManager.removeTeam(t);
		sender.sendMessage(ChatColor.GREEN + "You have deleted "+t.getName());
	}

	@Override
	public boolean canExecute(CommandSender sender, String args[]) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("teams.delete")) {
				if(TeamManager.teamExists(args[1]))
					return true;
			}
		}
		return false;
	}
	

}