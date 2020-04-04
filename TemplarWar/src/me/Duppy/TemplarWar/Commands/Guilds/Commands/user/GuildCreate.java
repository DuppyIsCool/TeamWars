package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.Main;
import me.Duppy.TemplarWar.Main.Plugin;
import me.Duppy.TemplarWar.Teams.TeamManager;
import net.md_5.bungee.api.ChatColor;

public class GuildCreate implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Main.getVault().getEconomy().withdrawPlayer(p, Plugin.plugin.getConfig().getDouble("defaults.guildcreateprice"));
		Guild g = new Guild(p.getUniqueId(),args[1]);
		GuildManager.addGuild(g);
		p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY + "You have created "+ChatColor.YELLOW +g.getName());
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) == null) {
				if(TeamManager.getTeam(p.getUniqueId())!= null) {
					if(check(args[1]) && args[1].length() <= 12) {
						if(Main.getVault().getEconomy().getBalance(p) >= Plugin.plugin.getConfig().getDouble("defaults.guildcreateprice")) {
							if(!GuildManager.isTeamName(args[1])) {
								return true;
							}else {MessageManager.sendMessage(p, "guild.error.takingteamname"); return false;}
						}else {MessageManager.sendMessage(p, "guild.error.notenoughfunds"); return false;}
					}else {MessageManager.sendMessage(p, "guild.error.invalidname"); return false;}
				}else {MessageManager.sendMessage(p, "team.error.notinteam"); return false;}
			}else {MessageManager.sendMessage(p, "guild.error.inguild"); return false;}
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
	
	public boolean check(String str)
	{
		String regex = "^[a-zA-Z0-9]+$"; 
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(str);
	    
	    return matcher.matches();
	}
}
