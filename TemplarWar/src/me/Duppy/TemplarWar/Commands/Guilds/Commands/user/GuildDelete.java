package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;

public class GuildDelete implements CMD{

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		MessageManager.sendMessage(sender, "guild.guilddeleted");	
		GuildManager.removeGuild(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()));
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) != null) 
				if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()).getGuildMap().get(p.getUniqueId()).equalsIgnoreCase("LEADER"))
					return true;
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "/guild delete";
	}

	@Override
	public String getDescription() {
		return "Deletes the user's guild";
	}

}
