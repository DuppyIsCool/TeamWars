package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.Plugin;
import me.Duppy.TemplarWar.Tasks.HomeTP;

public class GuildHome implements CMD {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			Location l = GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getHome();
			
			p.sendMessage(ChatColor.BLUE+"Guilds> "+ChatColor.GRAY+ "You are teleporting, do not move");
			GuildManager.teleportingPlayers.put(p, 
					new HomeTP(Plugin.plugin.getConfig().getInt("defaults.tptime"),p,l)
					.runTaskTimer(Plugin.plugin, 0,20));
		}
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!= null){
				if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getHome() != null) {
					if(!GuildManager.teleportingPlayers.containsKey(p))
						return true;
					else {MessageManager.sendMessage(p, "guild.error.alreadyteleporting");return false;}
				} else {MessageManager.sendMessage(p, "guild.error.nohomeset");return false;}
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
