package me.Duppy.TemplarWar.Teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.Duppy.TemplarWar.Guilds.Guilds.Guild;
import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Main.ConfigManager;

public class TeamManager {
	private static ArrayList<Team> teams;
	private static ConfigManager cfgm = new ConfigManager();
	//Begin Getters and Setters
	public static ArrayList<Team> getTeams() {
		return teams;
	}

	public static void setTeams(ArrayList<Team> teams) {
		TeamManager.teams = teams;
	}
	//End Getters and Setters
	
	//Begin Methods
	public static void setupTeams() {
		teams = new ArrayList<Team>();
		for(String team : cfgm.getTeams().getKeys(false)) {
			Team t = new Team();
			t.setName(team);
			t.setPoints(cfgm.getTeams().getInt(team + ".points"));
			//Get String list and convert it into a UUID list
			ArrayList<UUID> players = new ArrayList<UUID>();
			for(String e : (ArrayList<String>) cfgm.getTeams().getStringList(team + ".members")) {
				players.add(UUID.fromString(e));
			}
			t.setColor(cfgm.getTeams().getString(team + ".color"));
			t.setPlayers(players);
			t.setGuilds(new ArrayList<Guild>());
			addTeam(t);
		}
	}
	
	public static void saveTeams() {
		//Clear config
		for(String key : cfgm.getTeams().getKeys(false))
			cfgm.getTeams().set(key, null);
		
		for(Team t : teams) {
			cfgm.getTeams().set(t.getName() + ".points", t.getPoints());
			
			List<String> players = new ArrayList<String>();
			for(UUID u : t.getPlayers())
				players.add(u.toString());
			
			cfgm.getTeams().set(t.getName() + ".members",players);
			cfgm.getTeams().set(t.getName() + ".color", t.getColor());
		}
	}
	
	public static void addTeam(Team team) {
		if(!teams.contains(team))
			teams.add(team);
	}
	
	public static void removeTeam(Team team) {
		if(teams.contains(team)) {
			teams.remove(team);
		}
		for(Guild g : team.getGuilds()) {
			GuildManager.removeGuild(g);
		}
	}
	
	public static boolean teamExists(String teamName) {
		for(Team t : teams) {
			if(t.getName().equalsIgnoreCase(teamName))
				return true;
		}
		return false;
	}
	
	public static Team getTeam(UUID playerUUID) {
		for(Team t : teams)
			if(t.getPlayers().contains(playerUUID))
				return t;
		return null;
	}
	
	public static Team getTeam(String teamName) {
		for(Team t : teams) {
			if(t.getName().equalsIgnoreCase(teamName)) {
				return t;
			}
		}
		return null;
	}
	//End Methods
}
