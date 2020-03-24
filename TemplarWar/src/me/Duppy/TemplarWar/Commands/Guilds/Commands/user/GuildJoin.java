package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Guilds.Invites.InviteManager;
import net.md_5.bungee.api.ChatColor;

public class GuildJoin implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender, args)) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromGuildName(args[1]) != null) {
				GuildManager.getGuildFromGuildName(args[1]).addMember(p.getUniqueId());
				InviteManager.deleteInvite(p.getUniqueId(), args[1]);
				p.sendMessage(ChatColor.GREEN + "You've joined "+ args[1]);
			}
			else
				MessageManager.sendMessage(p, "guild.error.invalidguild");
			
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) == null) {
				if(InviteManager.hasInvite(p.getUniqueId(), args[1])) {
					return true;
				}
				else
					MessageManager.sendMessage(p, "guild.error.noinvite");
			}
			else
				MessageManager.sendMessage(p, "guild.error.inguild");
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
