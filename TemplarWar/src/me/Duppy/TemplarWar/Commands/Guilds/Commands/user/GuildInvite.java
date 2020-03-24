package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Guilds.Invites.Invite;
import me.Duppy.TemplarWar.Guilds.Invites.InviteManager;
import net.md_5.bungee.api.ChatColor;

public class GuildInvite implements CMD {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender, args)) {
			Player p = (Player) sender;
			Player recipient = Bukkit.getPlayer(args[1]);
			
			Invite i = new Invite(recipient.getUniqueId(), GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getName(), new Date(System.currentTimeMillis()+60*1000));
			InviteManager.addInvite(i);
			MessageManager.sendMessage(p, "guild.invitesent");
			recipient.sendMessage(ChatColor.GREEN + "You recieved an invite from "+GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getName());
		}
		
	}

	@Override
	//Args layout:  0 = invite(the command name), 1 = recipient
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(Bukkit.getServer().getPlayer(args[1]) != null) {
				Player recipient = Bukkit.getServer().getPlayer(args[1]);
				if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) != null && GuildManager.getGuildFromPlayerUUID(recipient.getUniqueId()) == null) {
					
					String role = GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getGuildMap().get(p.getUniqueId());
					String guildname = GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getName();
					
					if(role.equalsIgnoreCase("LEADER") || role.equalsIgnoreCase("ADMIN")) {
						if(!InviteManager.hasInvite(recipient.getUniqueId(), guildname)) {
							return true;
						}
						else
							MessageManager.sendMessage(p, "guild.error.hasinvite");
					}
					else 
						MessageManager.sendMessage(p, "guild.error.lowrole");
				}
				else
					MessageManager.sendMessage(p, "guild.error.notinguild");
			}
			else
				MessageManager.sendMessage(p, "error.invalidplayer");
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
