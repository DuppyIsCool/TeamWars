package me.Duppy.TemplarWar.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Duppy.TemplarWar.Commands.Teams.user.TeamsCreate;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsDelete;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsJoin;
import me.Duppy.TemplarWar.Commands.Teams.user.TeamsLeave;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {
	private TeamsJoin join = new TeamsJoin();
	private TeamsLeave leave = new TeamsLeave();
	private TeamsCreate create = new TeamsCreate();
	private TeamsDelete delete = new TeamsDelete();
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("teams")) {
			
			if(args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Invalid arguments");
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
					sender.sendMessage(ChatColor.RED + "Invalid arguments");
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
					sender.sendMessage(ChatColor.RED + "Invalid arguments");
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
					sender.sendMessage(ChatColor.RED + "Invalid arguments");
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
					sender.sendMessage(ChatColor.RED + "Invalid arguments");
					return true;
				}
			}
			//End commands
			
			sender.sendMessage(ChatColor.RED + args[0] + " is not a valid subcommand");
			return true;
		}
		return true;
	}
}
