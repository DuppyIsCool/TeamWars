package me.Duppy.TemplarWar.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildClaim;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildCreate;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildDelete;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildDemote;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildDeposit;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildHome;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildInfo;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildInvite;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildJoin;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildKick;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildLeave;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildPromote;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildSethome;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildUnclaim;
import me.Duppy.TemplarWar.Commands.Guilds.Commands.user.GuildWithdraw;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsCreate;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsDelete;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsJoin;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsLeave;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsList;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsSetcolor;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;

public class Commands implements CommandExecutor {
	//TEAMS
	private TeamsJoin join = new TeamsJoin();
	private TeamsLeave leave = new TeamsLeave();
	private TeamsCreate create = new TeamsCreate();
	private TeamsDelete delete = new TeamsDelete();
	private TeamsSetcolor setcolor = new TeamsSetcolor();
	private TeamsList list = new TeamsList();
	
	//GUILDS
	private GuildCreate gcreate = new GuildCreate();
	private GuildDelete gdelete = new GuildDelete();
	private GuildInvite ginvite = new GuildInvite();
	private GuildJoin gjoin = new GuildJoin();
	private GuildLeave gleave = new GuildLeave();
	private GuildClaim gclaim = new GuildClaim();
	private GuildUnclaim gunclaim = new GuildUnclaim();
	private GuildInfo ginfo = new GuildInfo();
	private GuildKick gkick = new GuildKick();
	private GuildDemote gdemote = new GuildDemote();
	private GuildPromote gpromote = new GuildPromote();
	private GuildSethome gsethome = new GuildSethome();
	private GuildHome ghome = new GuildHome();
	private GuildDeposit gdeposit = new GuildDeposit();
	private GuildWithdraw gwithdraw = new GuildWithdraw();
	
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String label, String[] args) {
		//Team Commands
		if(cmd.getName().equalsIgnoreCase("teams")) {
			
			if(args.length == 0) {
				list.execute(sender, args);
				return true;
			}
			
			//Begin commands
			if(args[0].equalsIgnoreCase("create")) {
				if(!sender.hasPermission("teams.create")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				
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
				if(!sender.hasPermission("teams.delete")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
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
				if(!sender.hasPermission("teams.join")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
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
				if(!sender.hasPermission("teams.leave")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
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
			
			if(args[0].equalsIgnoreCase("list")) {
				if(!sender.hasPermission("teams.list")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(list.canExecute(sender, args)) {
						list.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("setcolor")) {
				if(!sender.hasPermission("teams.setcolor")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(setcolor.canExecute(sender, args)) {
						setcolor.execute(sender, args);
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
				if(!sender.hasPermission("guilds.create")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gcreate.canExecute(sender, args))
						gcreate.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("delete")) {
				if(!sender.hasPermission("guilds.delete")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(gdelete.canExecute(sender, args))
						gdelete.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			if(args[0].equalsIgnoreCase("invite")) {
				if(!sender.hasPermission("guilds.invite")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(ginvite.canExecute(sender, args))
						ginvite.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("join")) {
				if(!sender.hasPermission("guilds.join")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gjoin.canExecute(sender, args))
						gjoin.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("leave")) {
				if(!sender.hasPermission("guilds.leave")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(gleave.canExecute(sender, args))
						gleave.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("claim")) {
				if(!sender.hasPermission("guilds.claim")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(gclaim.canExecute(sender, args))
						gclaim.execute(sender, args);
				}
				else 
					MessageManager.sendMessage(sender, "error.invalidargs");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("unclaim")) {
				if(!sender.hasPermission("guilds.unclaim")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(gunclaim.canExecute(sender, args))
						gunclaim.execute(sender, args);
				}
				else
					MessageManager.sendMessage(sender, "error.invalidargs");
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("info")) {
				if(!sender.hasPermission("guilds.info")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1 || args.length == 2) {
					if(ginfo.canExecute(sender, args))
						ginfo.execute(sender, args);
				}
				else
					MessageManager.sendMessage(sender, "error.invalidargs");
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("kick")) {
				if(!sender.hasPermission("guilds.kick")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gkick.canExecute(sender, args)) {
						gkick.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("promote")) {
				if(!sender.hasPermission("guilds.promote")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gpromote.canExecute(sender, args)) {
						gpromote.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("demote")) {
				if(!sender.hasPermission("guilds.demote")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gdemote.canExecute(sender, args)) {
						gdemote.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("deposit")) {
				if(!sender.hasPermission("guilds.deposit")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gdeposit.canExecute(sender, args)) {
						gdeposit.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("withdraw")) {
				if(!sender.hasPermission("guilds.withdraw")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 2) {
					if(gwithdraw.canExecute(sender, args)) {
						gwithdraw.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("sethome")) {
				if(!sender.hasPermission("guilds.sethome")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(gsethome.canExecute(sender, args)) {
						gsethome.execute(sender, args);
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
			
			if(args[0].equalsIgnoreCase("home")) {
				if(!sender.hasPermission("guilds.home")) 
				{MessageManager.sendMessage(sender, "error.nopermission"); return true;}
				if(args.length == 1) {
					if(ghome.canExecute(sender, args)) {
						ghome.execute(sender, args);
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
			
			MessageManager.sendMessage(sender, "error.nocommand");
			return true;
			
		}
		return true;
	}
}