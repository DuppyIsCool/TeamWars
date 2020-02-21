package me.Duppy.TemplarWar.Commands;

import org.bukkit.command.CommandSender;

public interface CMD {
	public void execute(CommandSender sender, String args[]);
	public boolean canExecute(CommandSender sender, String args[]);
	public String getDescription();
	public String getUsage();
}
