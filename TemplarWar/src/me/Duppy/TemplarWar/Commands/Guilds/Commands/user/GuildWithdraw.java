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
import net.milkbowl.vault.economy.EconomyResponse;

public class GuildWithdraw implements CMD {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender,args)) {
			Player p = (Player) sender;
			double input = 0; 
			input = Double.parseDouble(args[1]);
			input = round(input, 2);
			Guild g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
			EconomyResponse r = Main.getVault().getEconomy().depositPlayer(p, input);
			if(r.transactionSuccess()) {
                sender.sendMessage(String.format(ChatColor.BLUE + "Guilds> "+
                		ChatColor.GRAY +"You withdrew"+
                		ChatColor.YELLOW+" %s"+
                		ChatColor.GRAY+ " and now have"+
                		ChatColor.YELLOW+" %s", 
                		Main.getVault().getEconomy().format(r.amount), 
                		Main.getVault().getEconomy().format(r.balance)));
            } else {
                sender.
                sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
			g.setBalance(g.getBalance()-input);
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
					if(g.getBalance() > input) {
						return true;
					}else {MessageManager.sendMessage(p, "guild.error.withdrawtoomuch"); return false;}
				} else {MessageManager.sendMessage(p, "guild.error.lowrole"); return false;}
			}else {MessageManager.sendMessage(p, "guild.error.notinguild"); return false;}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Withdraws money from your guild";
	}

	@Override
	public String getUsage() {
		return "/g withdraw [amount]";
	}
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
