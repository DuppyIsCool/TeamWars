package me.Duppy.TemplarWar.Commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsJoin implements CMD{

	@Override
	public void Execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Team t = TeamManager.getTeam(args[1]);
		t.addPlayer(p);
		sender.sendMessage(ChatColor.GREEN + "You have joined "+t.getName());
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("teams.join")) {
				if(TeamManager.getTeam(p.getUniqueId()) == null) {
					if(TeamManager.teamExists(args[1])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
