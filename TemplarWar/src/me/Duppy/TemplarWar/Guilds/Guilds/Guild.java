package me.Duppy.TemplarWar.Guilds.Guilds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;

import me.Duppy.TemplarWar.Teams.Team;

//Guild Objects
public class Guild {
	private HashMap<UUID,String> guildMap = new HashMap<UUID,String>();
	private Location home;
	private int lives,claims;
	private String name;
	private boolean raidable;
	private Team team;
	public Guild() {
	}	
	public Guild(UUID leaderUUID) {
		guildMap.put(leaderUUID, "LEADER");
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
	
	//METHODS
	
	//Adds a member to the guild given their UUID
	public void addMember(UUID uuid) {
		if(!(this.guildMap.containsKey(uuid))) {
			guildMap.put(uuid, "MEMBER");
		}
	}
	
	//Removes a member from the guild given their UUID
	public void removeUser(UUID uuid) {
		if(this.guildMap.containsKey(uuid)) {
			switch(guildMap.get(uuid)) {
				case "LEADER":
					System.out.println("ERROR: Cannot remove leaders");
					break;
					
				case "ADMIN":
					guildMap.remove(uuid);
					break;
					
				case "MEMBER":
					guildMap.remove(uuid);
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
