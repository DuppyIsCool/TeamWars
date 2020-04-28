package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;

public class GuildLeave implements CMD {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			String guild = GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getName();
			GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).removeMember(p.getUniqueId());
			p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY + "You have left "+ChatColor.YELLOW + guild);
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!= null) {
				if(!GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER"))
					return true;
				else {MessageManager.sendMessage(p, "guild.error.leaderleave"); return false;}
			}
			else {MessageManager.sendMessage(p, "guild.error.notinguild"); return false;}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Leaves a guild";
	}

	@Override
	public String getUsage() {
		return "/g leave";
	}

}
