package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.Main;
import me.Duppy.TemplarWar.Main.Plugin;
import net.milkbowl.vault.economy.EconomyResponse;

public class GuildDeposit implements CMD{
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			double input = 0; 
			input = Double.parseDouble(args[1]);
			input = round(input, 2);
			Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
			EconomyResponse r = Main.getVault().getEconomy().withdrawPlayer(p, input);
			if(r.transactionSuccess()) {
                sender.sendMessage(String.format(ChatColor.BLUE + "Guilds> "+
                		ChatColor.GRAY +"You deposited"+
                		ChatColor.YELLOW+" %s"+
                		ChatColor.GRAY+ " and now have"+
                		ChatColor.YELLOW+" %s", 
                		Main.getVault().getEconomy().format(r.amount), 
                		Main.getVault().getEconomy().format(r.balance)));
            } else {
                sender.
                sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
			g.setBalance(g.getBalance()+input);
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			double input = 0;
			try {
				input = Double.parseDouble(args[1]);
			}
			catch(NumberFormatException e){
				MessageManager.sendMessage(p,"guild.error.invalidnumberinput");
				return false;
			}
			if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId())!=null) {
				Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
				String rank = g.getGuildMap().get(p.getUniqueId());
				if(rank.equalsIgnoreCase("LEADER") || rank.equalsIgnoreCase("ADMIN")) {
					if(Main.getVault().getEconomy().getBalance(p) >= input) {
						if(!((g.getBalance()+input) > Plugin.plugin.getConfig().getDouble("defaults.maxbankamount"))) {
							return true;
						}else {p.sendMessage(ChatColor.BLUE +"Guilds> "+ChatColor.GRAY
								+"Deposting "
								+ChatColor.YELLOW+"$"+input
								+ChatColor.GRAY + " would be over your banks cap of "
								+ChatColor.YELLOW+"$"+ 
								Plugin.plugin.getConfig().getDouble("defaults.maxbankamount"));
						return false;}
					}else {MessageManager.sendMessage(p, "guild.error.lackfunds"); return false;}
				} else {MessageManager.sendMessage(p, "guild.error.lowrole"); return false;}
			}else {MessageManager.sendMessage(p, "guild.error.notinguild"); return false;}
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
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
