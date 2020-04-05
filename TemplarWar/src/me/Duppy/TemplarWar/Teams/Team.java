package me.Duppy.TemplarWar.Teams;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Main.ConfigManager;

public class Team {
	private int points;
	private ArrayList<UUID> players;
	private String name,color;
	private ArrayList<Guild> guilds;
	private org.bukkit.scoreboard.Team scoreBoardTeam;
	
	//Constructor used during setup
	public Team(String name) {
		players = new ArrayList<UUID>();
		this.scoreBoardTeam = GuildManager.mainScoreboard.registerNewTeam(name);
		this.name = name;
	}
	
	//Constructor used by players
	public Team(String name,UUID creator) {
		this.scoreBoardTeam = GuildManager.mainScoreboard.registerNewTeam(name);
		setColor("red");
		updateColor();
		this.players = new ArrayList<UUID>();
		this.players.add(creator);
		this.scoreBoardTeam.addEntry(ConfigManager.getPlayername(creator));
		this.name = name;
		this.guilds = new ArrayList<Guild>();
		this.points = 0;
	}
	//Start Getter and Setters
	public ArrayList<UUID> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<UUID> players) {
		this.players.clear();
		for(UUID e : players)
			this.players.add(e);
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Guild> getGuilds() {
		return guilds;
	}

	public void setGuilds(ArrayList<Guild> guilds) {
		this.guilds = guilds;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		switch(color.toLowerCase()) {
			case "red":
				this.color = "red";
				break;
			case "blue":
				this.color = "blue";
				break;
			case "green":
				this.color = "green";
				break;
			case "yellow":
				this.color = "yellow";
				break;
			default:
				this.color = "red";
				break;
		}
		updateColor();
	}
	
	public org.bukkit.scoreboard.Team getScoreBoardTeam() {
		return this.scoreBoardTeam;
	}
	//End Getter and Setters
	
	
	//Begin methods
	public void addPlayer(Player p) {
		if(!this.players.contains(p.getUniqueId())) {
			this.players.add(p.getUniqueId());
			this.scoreBoardTeam.addEntry(p.getName());
		}
	}
	
	public void removePlayer(Player p){
		if(this.players.contains(p.getUniqueId())) {
			this.players.remove(p.getUniqueId());
			this.scoreBoardTeam.removeEntry(p.getName());
		}
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public void removePoints(int points) {
		this.points -= points;
	}
	
	public void resetPoints(int points) {
		this.points = 0;
	}
	
	public void addGuild(Guild g) {
		if(!guilds.contains(g))
			guilds.add(g);
	}
	
	public void removeGuild(Guild g) {
		if(guilds.contains(g))
			guilds.remove(g);
	}
	
	public void updateColor() {
		switch(this.color) {
		case "green":
			this.scoreBoardTeam.setColor(ChatColor.GREEN);
			break;
			
		case "blue":
			this.scoreBoardTeam.setColor(ChatColor.BLUE);
			break;
		
		case "red":
			this.scoreBoardTeam.setColor(ChatColor.RED);
			break;
		case "yellow":
			this.scoreBoardTeam.setColor(ChatColor.YELLOW);
			break;
			
		//This should never call. Team color should also be set or default by red.
		default:
			this.scoreBoardTeam.setColor(ChatColor.RED);
			break;
		}
	}
	

	//End methods

}
