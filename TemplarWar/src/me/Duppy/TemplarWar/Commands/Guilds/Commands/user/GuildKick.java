package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.ConfigManager;
import net.md_5.bungee.api.ChatColor;

public class GuildKick implements CMD {

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
			
			g.removeMember(recipient);
			p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY+ "You have kicked "+ChatColor.YELLOW +rName);
			
			//Send message to player if they're online
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(player.getUniqueId().equals(recipient)) {
					player.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY+ "You have been kicked from "+ChatColor.YELLOW +g.getName());
					break;
				}
			}
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!=null) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				if(!(args[1].equalsIgnoreCase(p.getName()))) {
					for(Entry<UUID,String> e: g.getGuildMap().entrySet()) {
						if(ConfigManager.getPlayername(e.getKey()).equalsIgnoreCase(args[1])) {
							//Only allows kicking of a player if it's Leader kicking Admin, Leader Kicking Member, or Admin kicking Member
							if((e.getValue().equalsIgnoreCase("ADMIN") 
									&& g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER"))
									||e.getValue().equalsIgnoreCase("MEMBER") 
									&& g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("ADMIN")
									||(e.getValue().equalsIgnoreCase("MEMBER") 
											&& g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER"))) {
								return true;
							}
							else {
								MessageManager.sendMessage(p, "guild.error.lowrole");
								return false;
							}
						}
					}
					MessageManager.sendMessage(p, "error.invalidplayer");
				}
				else
					MessageManager.sendMessage(p, "guild.error.kickself");
			}
			else
				MessageManager.sendMessage(p, "guild.error.notinguild");
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
