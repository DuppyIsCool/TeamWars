package me.Duppy.TemplarWar.Commands.Guilds.Commands.user;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Duppy.TemplarWar.Commands.CMD;
import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Guilds.Guilds.MessageManager;
import me.Duppy.TemplarWar.Main.ConfigManager;
import me.Duppy.TemplarWar.Main.Plugin;

public class GuildInfo implements CMD {
//WHAT A PAIN TO CODE. JESUS
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(canExecute(sender, args)) {
			
			//Start of inventory creation
			Player p = (Player) sender;
			Guild g = null;
			if(args.length == 1 || args.length == 0)
				g = GuildManager.getGuildFromPlayerUUID(p.getUniqueId());
			else if(args.length == 2) {
				g = GuildManager.getGuildFromGuildName(args[1]);
			}
			
			//Create a list of online guild members
			ArrayList<Player> onlinePlayers = new ArrayList<Player>();
			for(Player player : Bukkit.getOnlinePlayers())
				if(g.getGuildMap().containsKey(player.getUniqueId()))
					onlinePlayers.add(player);
			
			Inventory inv;
			ItemStack item;
			
			//Create Inventory
			inv = Bukkit.createInventory(null, 45, ChatColor.RED+"Guild Display");
			
			//Create data beacon
			item = new ItemStack(Material.BEACON,1);
			ItemMeta metas = item.getItemMeta();
			metas.setDisplayName(ChatColor.GOLD + ""+ChatColor.BOLD + g.toString());
			ArrayList<String> lores = new ArrayList<String>();
			
			//Setting lore
			lores.add(ChatColor.GREEN + "Name: "+ChatColor.YELLOW + g.getName());
			lores.add(ChatColor.GREEN + "Leader: "+ChatColor.YELLOW + ConfigManager.getPlayername(g.getLeader()));
			lores.add(ChatColor.GREEN + "Date Founded: "+ChatColor.YELLOW+g.getDateFoundedString());

			
			//Setting lore to item
			metas.setLore(lores);
			item.setItemMeta(metas);
			
			//Adds beacon to inventory
			inv.setItem(4,item);
			
			//Create data gold
			item = new ItemStack(Material.GOLD_INGOT,1);
			ItemMeta goldmetas = item.getItemMeta();
			goldmetas.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Economy");
			ArrayList<String> goldlores = new ArrayList<String>();
			
			//Setting lore
			goldlores.add(ChatColor.GREEN + "Balance: "+ChatColor.YELLOW + g.getBalance());
			goldlores.add(ChatColor.GREEN + "Upkeep: "+ChatColor.RED+ g.getUpkeep());

			
			//Setting lore to item
			goldmetas.setLore(goldlores);
			item.setItemMeta(goldmetas);
			
			//Adds gold to inventory
			inv.setItem(19,item);
			
			//Create grass data
			item = new ItemStack(Material.GRASS_BLOCK,1);
			ItemMeta grassmetas = item.getItemMeta();
			grassmetas.setDisplayName(ChatColor.GOLD + ""+ChatColor.BOLD + "Claims");
			ArrayList<String> grasslores = new ArrayList<String>();
			
			//Setting lore
			grasslores.add(ChatColor.GREEN + "Claims: "+ChatColor.YELLOW + g.getClaimSize() + "/"+Plugin.plugin.getConfig().getInt("defaults.maxclaimcount"));
			grasslores.add(ChatColor.GREEN + "Lives: "+ChatColor.YELLOW + g.getLives());
			//Setting lore to item
			grassmetas.setLore(grasslores);
			item.setItemMeta(grassmetas);
			
			//Adds grass to inventory
			inv.setItem(22,item);
			
			//Create head data
			item = new ItemStack(Material.PLAYER_HEAD,1);
			ItemMeta headmetas = item.getItemMeta();
			headmetas.setDisplayName(ChatColor.GOLD + ""+ChatColor.BOLD + "Members");
			ArrayList<String> headlores = new ArrayList<String>();
			
			//Setting lore
			headlores.add(ChatColor.GREEN + "Online: "+ChatColor.YELLOW+ onlinePlayers.size());
			headlores.add(ChatColor.GRAY+ "Offline: "+ChatColor.RED +(g.getGuildMap().size()-onlinePlayers.size()));
			headlores.add(ChatColor.GREEN +"Total: "+ChatColor.YELLOW + (g.getGuildMap().size()));

			//Setting lore to item
			headmetas.setLore(headlores);
			item.setItemMeta(headmetas);
			
			//Adds beacon to inventory
			inv.setItem(25,item);
			
			//Finally, Open the inventory to the player
			p.openInventory(inv);
		}
		
	}

	@Override
	public boolean canExecute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 2) {
				if(GuildManager.getGuildFromGuildName(args[1]) != null)
					return true;
			}
			else
			{
				if(GuildManager.getGuildFromPlayerUUID(p.getUniqueId()) != null) {
					return true;
				}
				else if(args.length == 2) {
					MessageManager.sendMessage(p, "guild.error.invalidguild");
					return false;
				}
				else {
					MessageManager.sendMessage(p, "guild.error.notinguild");
					return false;
				}
			}
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
