package me.Duppy.TemplarWar.Teams;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Duppy.TemplarWar.Guilds.Guilds.Guild;

public class Team {
	private int points;
	private ArrayList<UUID> players;
	private String name;
	private org.bukkit.scoreboard.Team boardTeam;
	private ArrayList<Guild> guilds;
	//Empty Constructor
	public Team() {
		players = new ArrayList<UUID>();
	}
	
	public Team(String name) {
		players = new ArrayList<UUID>();
		this.name = name;
		boardTeam = TeamManager.board.registerNewTeam(name);
		boardTeam.setPrefix(ChatColor.GOLD + "" + name + " ");
		boardTeam.setColor(ChatColor.YELLOW);
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
	
	public org.bukkit.scoreboard.Team getBoardTeam() {
		return boardTeam;
	}

	public void setBoardTeam(org.bukkit.scoreboard.Team boardTeam) {
		this.boardTeam = boardTeam;
	}
	
	public ArrayList<Guild> getGuilds() {
		return guilds;
	}

	public void setGuilds(ArrayList<Guild> guilds) {
		this.guilds = guilds;
	}
	//End Getter and Setters
	
	
	//Begin methods
	public void addPlayer(Player p) {
		if(!this.players.contains(p.getUniqueId())) {
			this.players.add(p.getUniqueId());
			boardTeam.addEntry(p.getName());
		}
	}
	
	public void removePlayer(Player p){
		if(this.players.contains(p.getUniqueId())) {
			this.players.remove(p.getUniqueId());
			boardTeam.removeEntry(p.getName());
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
	//End methods

}
