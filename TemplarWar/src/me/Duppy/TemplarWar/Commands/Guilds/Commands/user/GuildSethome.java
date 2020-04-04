package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class GuildSethome implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			Location l = p.getLocation();
			GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).setHome(p.getLocation());
			p.sendMessage(ChatColor.BLUE + "Guilds> "+ChatColor.GRAY+"You have set your home to: "
			+ChatColor.YELLOW +l.getBlockX() + ChatColor.GRAY+","+ChatColor.YELLOW+
			l.getBlockY()+ChatColor.GRAY+","+ChatColor.YELLOW+l.getBlockZ());
		}
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) !=null ) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				
				if(g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER") 
						|| g.getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("ADMIN")) {
					if(g.getChunks().contains(p.getLocation().getChunk()))
						return true;
					else {MessageManager.sendMessage(p, "guild.error.sethomeinclaim");return false;}
				}else {MessageManager.sendMessage(p, "guild.error.lowrole");return false;}
			}else {MessageManager.sendMessage(p, "guild.error.notinguild");return false;}
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
