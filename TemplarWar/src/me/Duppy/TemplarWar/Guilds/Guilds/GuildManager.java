package me.Duppy.TemplarWar.Guilds.Guilds;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Duppy.TemplarWar.Main.ConfigManager;
import me.Duppy.TemplarWar.Teams.TeamManager;

public class GuildManager {
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static ArrayList<Guild> guildList = new ArrayList<Guild>();
	private static ConfigManager cfgm = new ConfigManager();
	public static Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	public static void setupGuilds() {
		//Cycles through config file and sets up the object ArrayList of guilds
		for(String guild : cfgm.getGuilds().getKeys(false)) {
			Guild g = new Guild();
			g.setName(guild);
			g.setLives(cfgm.getGuilds().getInt(guild + ".lives"));
			g.setTeam(TeamManager.getTeam(cfgm.getGuilds().getString(guild + ".team")));
			g.setBalance(cfgm.getGuilds().getDouble(guild + ".balance"));
			g.setDateFounded(LocalDate.parse(cfgm.getGuilds().getString(guild + ".date"), formatter));
			
			//Create Home Location
			if(cfgm.getGuilds().getConfigurationSection(guild + ".home") != null)
				g.setHome(new Location(Bukkit.getServer().getWorld(cfgm.getGuilds().getString(guild + ".home.world")),
					cfgm.getGuilds().getInt(guild + ".home.x"),
					cfgm.getGuilds().getInt(guild + ".home.y"),
					cfgm.getGuilds().getInt(guild + ".home.z")));
			
			//Setup chunk claims
			if(cfgm.getGuilds().getConfigurationSection(guild + ".claims") != null)
				for(String s : cfgm.getGuilds().getConfigurationSection(guild + ".claims").getKeys(false)) {
					try {
						World w = Bukkit.getServer().getWorld(cfgm.getGuilds().getString(guild + ".claims."+s));
						
						//Grabs the x&z from the long
						long l = Long.parseLong(s);			
						int x = (int)(l >> 32);
						int z = (int)l;
						
						//Create chunk object and add it to the arrayList
						Chunk c = w.getChunkAt(x, z);
						g.addChunk(c);
					}
					//This catch can occur if the world is no longer valid (ex: has been deleted).
					catch(NullPointerException e) {}
				}
			
			//Setup members
			HashMap<UUID, String> map = new HashMap<>();
			for (String s : cfgm.getGuilds().getConfigurationSection(guild + ".members").getKeys(false)) {
			   String value =  cfgm.getGuilds().getString(guild + ".members."+s);
			   map.put(UUID.fromString(s),value);
			}
			
			g.setGuildMap(map);
			
			//Scoreboard team setup
			Team t = mainScoreboard.registerNewTeam(g.getName());
			for (Entry<UUID, String> entry : map.entrySet()) {
		        t.addEntry(ConfigManager.getPlayername(entry.getKey()));
		    }
			
			//Setup upkeep
			g.setUpkeep();
			
			//Set the scoreboard team
			g.setScoreBoardTeam(t);
			
			//Update scoreboard team color (and prefix)
			g.updateColor();
			//Add guild to guild manager list
			GuildManager.addGuild(g);
			
		}
	}
	
	public static void saveGuilds() {
		//Clears the old config file
		for(String key : cfgm.getGuilds().getKeys(false))
			cfgm.getGuilds().set(key, null);
		
		//Saves guilds from object array to config
		for(Guild g : guildList) {
			cfgm.getGuilds().createSection(g.toString() + ".members", g.getGuildMap());
			cfgm.getGuilds().set(g.toString() + ".lives",g.getLives());
			cfgm.getGuilds().set(g.toString() + ".team", g.getTeam().getName());
			cfgm.getGuilds().set(g.toString()+".balance",g.getBalance());
			cfgm.getGuilds().set(g.toString() + ".date", g.getDateFoundedObject().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			if(g.getHome() != null) {
				Location loc = g.getHome();
				cfgm.getGuilds().set(g.toString() + ".home.x",loc.getBlockX());
				cfgm.getGuilds().set(g.toString() + ".home.y",loc.getBlockY());
				cfgm.getGuilds().set(g.toString() + ".home.z",loc.getBlockZ());
				cfgm.getGuilds().set(g.toString() + ".home.world",loc.getWorld().getName());
			}
			//Claim storage
			for(Chunk c : g.getChunks()) {
				//Stores claims in format World: x&z
				//x&z are stored with bitwise manipulation
				cfgm.getGuilds().set(g.toString() + ".claims."+Long.toString((((long)c.getX()) << 32) | (c.getZ() & 0xffffffffL)), c.getWorld().getName());
			}
		}
	}
	//Removes a guild from the guild list
	public static ArrayList<Guild> getGuildList() {
		return guildList;
	}
	
	//Returns a guild object that has the given name
	public static Guild getGuildFromGuildName(String guildName) {
		for(Guild g : guildList)
			if(g.toString().equalsIgnoreCase(guildName))
				return g;
		return null;
	}
	
	public static Guild getGuildFromPlayerUUID(UUID uuid) {
		for(Guild g : guildList) {
			if(g.getGuildMap().containsKey(uuid))
				return g;
		}
		return null;
	}
	
	//Adds a guild to the guild list
	public static void addGuild(Guild guild) {
		if(!(guildList.contains(guild)))
			guildList.add(guild);
	}
	
	//Removes a guild from the guild list
	public static void removeGuild(Guild guild) {
		if(guildList.contains(guild)) {
			mainScoreboard.getTeam(guild.getName()).unregister();
			guildList.remove(guild);
		}
	}
	
	//Retrive chunk owner
	public static Guild getChunkOwner(Chunk c) {
		for(Guild g : guildList) {
			if(g.getChunks().contains(c))
				return g;
		}
		return null;
	}
	
}
