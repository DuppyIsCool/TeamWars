package me.Duppy.TemplarWar.Commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsCreate implements CMD{

	@Override
	public void Execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Team t = new Team(args[1]);
		t.setPoints(0);
		t.addPlayer(p);
		TeamManager.addTeam(t);
		sender.sendMessage(ChatColor.GREEN + "You have created "+t.getName());
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("teams.create")) {
				if(TeamManager.getTeam(p.getUniqueId()) == null) {
					return true;
				}
			}
		}
		return false;
	}

}
