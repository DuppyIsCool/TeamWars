package me.Duppy.TemplarWar.Guilds.Guilds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.Duppy.TemplarWar.Main.ConfigManager;
import me.Duppy.TemplarWar.Teams.Team;
import me.Duppy.TemplarWar.Teams.TeamManager;

//Guild Objects
public class Guild {
	private HashMap<UUID,String> guildMap = new HashMap<UUID,String>();
	private Location home;
	private int lives,claims;
	private String name;
	private boolean raidable;
	private Team team;
	private org.bukkit.scoreboard.Team scoreBoardTeam;
	public Guild() {
	}	
	public Guild(UUID leaderUUID, String guildName) {
		guildMap.put(leaderUUID, "LEADER");
		this.name = guildName;
		this.team = TeamManager.getTeam(leaderUUID);
		//Setup the scoreboard team
		this.scoreBoardTeam = GuildManager.mainScoreboard.registerNewTeam(guildName);
		updateColor();
		this.scoreBoardTeam.addEntry(ConfigManager.getPlayername(leaderUUID));
	}
	
	public HashMap<UUID,String> getGuildMap() {
		return guildMap;
	}
	public void setGuildMap(HashMap<UUID,String> guildMap) {
		this.guildMap.putAll(guildMap);
	}
	
	public Location getHome() {
		return home;
	}
	public void setHome(Location home) {
		this.home = home;
	}
	
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getClaims() {
		return claims;
	}
	public void setClaims(int claims) {
		this.claims = claims;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isRaidable() {
		return raidable;
	}
	public void setRaidable(boolean raidable) {
		this.raidable = raidable;
	}
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public org.bukkit.scoreboard.Team getScoreBoardTeam() {
		return scoreBoardTeam;
	}
	public void setScoreBoardTeam(org.bukkit.scoreboard.Team scoreBoardTeam) {
		this.scoreBoardTeam = scoreBoardTeam;
	}
	
	//METHODS
	
	//Adds a member to the guild given their UUID
	public void updateColor() {
		switch(this.team.getColor()) {
		case "&a":
			this.scoreBoardTeam.setPrefix(ChatColor.GREEN + "" + this.name + " ");
			break;
			
		case "&b":
			this.scoreBoardTeam.setPrefix(ChatColor.BLUE + "" + this.name + " ");
			break;
		
		case "&c":
			this.scoreBoardTeam.setPrefix(ChatColor.RED + "" + this.name + " ");
			break;
			
		//This should never call. Team color should also be set or default by red.
		default:
			this.scoreBoardTeam.setPrefix(ChatColor.RED + "" + this.name + " ");
			break;
		}
	}
	
	public void addMember(UUID uuid) {
		if(!(this.guildMap.containsKey(uuid))) {
			this.scoreBoardTeam.addEntry(ConfigManager.getPlayers().getString(uuid.toString()));
			guildMap.put(uuid, "MEMBER");
		}
	}
	
	//Removes a member from the guild given their UUID
	public void removeMember(UUID uuid) {
		if(this.guildMap.containsKey(uuid)) {
			switch(guildMap.get(uuid)) {
				case "LEADER":
					System.out.println("ERROR: Cannot remove leaders");
					break;
					
				case "ADMIN":
					guildMap.remove(uuid);
					this.scoreBoardTeam.removeEntry(ConfigManager.getPlayers().getString(uuid.toString()));
					break;
					
				case "MEMBER":
					guildMap.remove(uuid);
					this.scoreBoardTeam.removeEntry(ConfigManager.getPlayers().getString(uuid.toString()));
					break;
					
			}
		}
	}
	
	//Promotes a member in the guild given their UUID
	public void promoteUser(UUID uuid) {
		if(this.guildMap.containsKey(uuid)) {
			switch(guildMap.get(uuid)) {
				case "LEADER":
					System.out.println("ERROR: Cannot promote leaders");
					break;
					
				case "ADMIN":
					guildMap.put(getKey(guildMap,"LEADER"), "ADMIN");
					guildMap.put(uuid, "LEADER");
					break;
					
				case "MEMBER":
					guildMap.put(uuid, "ADMIN");
					break;
					
			}
		}
	}
	
	//Demotes a member in the guild given their UUID
	public void demoteUser(UUID uuid) {
		if(this.guildMap.containsKey(uuid)) {
			switch(guildMap.get(uuid)) {
				case "LEADER":
					System.out.println("ERROR: Cannot demote leaders");
					break;
					
				case "ADMIN":
					guildMap.put(uuid, "MEMBER");
					break;
					
				case "MEMBER":
					System.out.println("ERROR: Cannot demote members");
					break;
					
			}
		}
	}
	
	private <K, V> K getKey(Map<K, V> map, V value) {
	    for (Entry<K, V> entry : map.entrySet()) {
	        if (entry.getValue().equals(value)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
}
