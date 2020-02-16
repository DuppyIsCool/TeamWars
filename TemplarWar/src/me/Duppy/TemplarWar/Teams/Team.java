package me.Duppy.TemplarWar.Teams;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
	private int points;
	private ArrayList<UUID> players;
	private String name;
	
	//Empty Constructor
	public Team() {
		players = new ArrayList<UUID>();
	}
	
	public Team(String name) {
		players = new ArrayList<UUID>();
		this.name = name;
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
	//End Getter and Setters
	
	
	//Begin methods
	public void addPlayer(UUID uuid) {
		if(!this.players.contains(uuid))
			this.players.add(uuid);
	}
	
	public void removePlayer(UUID uuid){
		if(this.players.contains(uuid))
			this.players.remove(uuid);
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
