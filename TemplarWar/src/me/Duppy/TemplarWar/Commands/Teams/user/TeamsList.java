package me.Duppy.TemplarWar.Commands.Teams.user;

import org.bukkit.command.CommandSender;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsList implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			sender.sendMessage(ChatColor.GOLD +""+ChatColor.BOLD+"--Teams-- ");
			if(TeamManager.getTeams().size() == 0) {
				sender.sendMessage(ChatColor.RED + "No teams are present");
				return;
			}
			else {
				for(Team t : TeamManager.getTeams()) {
					if(t.getGuilds() != null)
						sender.sendMessage(ChatColor.YELLOW + t.getName() 
						+ ChatColor.GRAY+ " : "+ChatColor.GREEN + ""+t.getGuilds().size() + " "+ChatColor.YELLOW+"Guilds"+ ChatColor.GRAY+ " : "
						+ChatColor.GREEN + t.getPoints() + ChatColor.YELLOW + " Points");
					else
						sender.sendMessage(ChatColor.YELLOW + t.getName() 
						+ ChatColor.GRAY+ " : "+ChatColor.GREEN + ""+"0" + " "+ChatColor.YELLOW+"Guilds"+ ChatColor.GRAY+ " : "
						+ChatColor.GREEN + t.getPoints() + ChatColor.YELLOW + " Points");
				}
			}
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		return true;
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
