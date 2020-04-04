package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.ConfigManager;
import net.md_5.bungee.api.ChatColor;

public class GuildDemote implements CMD {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
			UUID recipient = null;
			String rName = null;
			for(Entry<UUID,String> e: g.getGuildMap().entrySet()) {
				if(ConfigManager.getPlayername(e.getKey()).equalsIgnoreCase(args[1])) {
					recipient = e.getKey();
					rName = ConfigManager.getPlayername(e.getKey());
					break;
				}
			}
			g.demoteUser(recipient);
			p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY+"You have demoted "+ChatColor.YELLOW+rName);
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) != null) {
				if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER")) {
					if(!(args[1].equalsIgnoreCase(p.getName()))) {
						for(Entry<UUID,String> e: GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getGuildMap().entrySet()) {
							if(ConfigManager.getPlayername(e.getKey()).equalsIgnoreCase(args[1])) {
								if(!GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getGuildMap().get(e.getKey()).equalsIgnoreCase("MEMBER"))
									return true;
								else {
									MessageManager.sendMessage(p, "guild.error.demotemember");
									return false;
								}
							}
								else {
									MessageManager.sendMessage(p, "guild.error.lowrole");
									return false;
								}
							}
								MessageManager.sendMessage(p, "error.invalidplayer");
						}else {
							MessageManager.sendMessage(p, "guild.error.demoteself"); return false;}
					}
					else {
						MessageManager.sendMessage(p, "guild.error.lowrole");return false;}
				}
				else {
					MessageManager.sendMessage(p, "guild.error.notinguild");return false;}
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
