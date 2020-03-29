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
		Guild g = new Guild(p.getUniqueId(),args[1]);
		GuildManager.addGuild(g);
		MessageManager.sendMessage(sender, "guild.guildcreated");	
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) == null) {
				if(TeamManager.getTeam(p.getUniqueId())!= null) {
					if(check(args[1]) && args[1].length() <= 12) {
						return true;
					}else {MessageManager.sendMessage(p, "guild.error.invalidname"); return false;}
				}else {MessageManager.sendMessage(p, "team.error.notinteam"); return false;}
			}else {MessageManager.sendMessage(p, "guild.error.notinguild"); return false;}
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
	
	private boolean check(String s) {
	      if (s == null) // checks if the String is null {
	         return false;
	      int len = s.length();
	      for (int i = 0; i < len; i++) {
	         // checks whether the character is neither a letter nor a digit
	         // if it is neither a letter nor a digit then it will return false
	         if ((Character.isLetterOrDigit(s.charAt(i)) == false)) {
	            return false;
	         }
	      }
	      return true;
	}
}
