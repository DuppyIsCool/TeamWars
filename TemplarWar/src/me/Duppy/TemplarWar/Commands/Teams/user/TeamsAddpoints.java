package me.Duppy.TemplarWar.Commands.Teams.user;

import org.bukkit.command.CommandSender;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class TeamsAddpoints implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		Team t = TeamManager.getTeam(args[1]);
		t.setPoints(t.getPoints() + Integer.parseInt(args[2]));
		sender.sendMessage(ChatColor.BLUE + "Teams> "+ChatColor.GRAY+"Added "+ChatColor.YELLOW+Integer.parseInt(args[2])+
				ChatColor.GRAY + " points to team "+t.getName());
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender.hasPermission("teams.addpoints"))
			if(args.length == 3) {
				if(TeamManager.teamExists(args[1])) {
					if(isInteger(args[2])) {
						return true;
					}else {MessageManager.sendMessage(sender, "error.invalidargs"); return false;}
				}else {MessageManager.sendMessage(sender, "team.error.invalidteam"); return false;}
			}else {MessageManager.sendMessage(sender, "error.invalidargs"); return false;}
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
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}
	
	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
