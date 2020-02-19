package me.Duppy.TemplarWar.Teams;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Team {
	private int points;
	private ArrayList<UUID> players;
	private String name;
	private org.bukkit.scoreboard.Team boardTeam;
	
	//Empty Constructor
	public Team() {
		players = new ArrayList<UUID>();
	}
	
	public Team(String name) {
		players = new ArrayList<UUID>();
		this.name = name;
		boardTeam = TeamManager.board.registerNewTeam(name);
		boardTeam.setPrefix(ChatColor.RED + "[" +name + "] "+ChatColor.WHITE);
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
