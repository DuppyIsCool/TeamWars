package me.Duppy.TemplarWar.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildClaim;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildCreate;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildDelete;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildInfo;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildInvite;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildJoin;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildLeave;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildUnclaim;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsCreate;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsDelete;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsJoin;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsLeave;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;

public class Commands implements CommandExecutor {
	//TEAMS
	private TeamsJoin join = new TeamsJoin();
	private TeamsLeave leave = new TeamsLeave();
	private TeamsCreate create = new TeamsCreate();
	private TeamsDelete delete = new TeamsDelete();
	
	//GUILDS
	private GuildCreate gcreate = new GuildCreate();
	private GuildDelete gdelete = new GuildDelete();
	private GuildInvite ginvite = new GuildInvite();
	private GuildJoin gjoin = new GuildJoin();
	private GuildLeave gleave = new GuildLeave();
	private GuildClaim gclaim = new GuildClaim();
	private GuildUnclaim gunclaim = new GuildUnclaim();
	private GuildInfo ginfo = new GuildInfo();
	
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String label, String[] args) {
		//Team Commands
		if(cmd.getName().equalsIgnoreCase("teams")) {
			
			if(args.length == 0) {
				MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			//Begin commands
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length == 2) {
					if(create.canExecute(sender, args)) {
						create.execute(sender, args);
						return true;
					}
					else
						return true;
				}
				else {
					MessageManager.sendMessage(sender, "error.invalidargs");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("delete")) {
				if(args.length == 2) {
					if(delete.canExecute(sender, args)) {
						delete.execute(sender, args);
						return true;
					}
					else
						return true;
				}
				else {
					MessageManager.sendMessage(sender, "error.invalidargs");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("join")) {
				if(args.length == 2) {
					if(join.canExecute(sender, args)) {
						join.execute(sender, args);
						return true;
					}
					else
						return true;
				}
				else {
					MessageManager.sendMessage(sender, "error.invalidargs");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("leave")) {
				if(args.length == 1) {
					if(leave.canExecute(sender, args)) {
						leave.execute(sender, args);
						return true;
					}
					else
						return true;
				}
				else {
					MessageManager.sendMessage(sender, "error.invalidargs");
					return true;
				}
			}
			//End commands
			
			MessageManager.sendMessage(sender, "error.nocommand");
			return true;
		}
		
		//Guilds Commands
		if(cmd.getName().equalsIgnoreCase("guilds")) {
			
			if(args.length == 0) {
				if(ginfo.canExecute(sender, args)) {
					ginfo.execute(sender, args);
					return true;
				}
				else
					return true;
			}
			
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length == 2) {
					if(gcreate.canExecute(sender, args))
						gcreate.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("delete")) {
				if(args.length == 1) {
					if(gdelete.canExecute(sender, args))
						gdelete.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			if(args[0].equalsIgnoreCase("invite")) {
				if(args.length == 2) {
					if(ginvite.canExecute(sender, args))
						ginvite.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("join")) {
				if(args.length == 2) {
					if(gjoin.canExecute(sender, args))
						gjoin.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("leave")) {
				if(args.length == 1) {
					if(gleave.canExecute(sender, args))
						gleave.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("claim")) {
				if(args.length == 1) {
					if(gclaim.canExecute(sender, args))
						gclaim.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("unclaim")) {
				if(args.length == 1) {
					if(gunclaim.canExecute(sender, args))
						gunclaim.execute(sender, args);
				}
				else
					MessageManager.sendMessage(sender, "error.invalidargs");
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("info")) {
				if(args.length == 1) {
					if(ginfo.canExecute(sender, args))
						ginfo.execute(sender, args);
				}
				else
					MessageManager.sendMessage(sender, "error.invalidargs");
				
				return true;
			}
			
			MessageManager.sendMessage(sender, "error.nocommand");
			return true;
			
		}
		return true;
	}
}