package me.Duppy.TemplarWar.Commands.Teams.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsCreate implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
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
					if(args[1].length() <= 16)
						return true;
					else {
						sender.sendMessage(ChatColor.RED + "Error: Team names must be less than 16 characters");
						return false;
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Error: You are already in a team");
					return false;
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "Error: You do not have permission to execute this command");
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
