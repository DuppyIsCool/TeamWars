package me.Duppy.TemplarWar.Guilds.Invites;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class InviteManager {
	private static ArrayList<Invite> invites = new ArrayList<Invite>();
	
	public static void addInvite(Invite i) {
		invites.add(i);
		cleanOldInvites();
	}
	
	public static void deleteInvite(UUID uuid, String guild) {
		Iterator<Invite> itr = invites.iterator();
	    while (itr.hasNext()) {
	      Invite i = itr.next();
	      if (i.getGuild().equalsIgnoreCase(guild) && i.getRecipient().equals(uuid)) {
	        itr.remove();
	      }
	    }
	}
	
	public static boolean hasInvite(UUID uuid, String guild) {
		for(Invite i : invites) {
			if(i.getRecipient().equals(uuid) && i.getGuild().equalsIgnoreCase(guild))
				return true;
		}
		return false;
	}
	
	//Should move this to a scheduled task?
	public static void cleanOldInvites() {
		Iterator<Invite> itr = invites.iterator();
	    while (itr.hasNext()) {
	      Invite i = itr.next();
	      if(i.getExpireDate().getTime() - System.currentTimeMillis() <= 0) {
	        itr.remove();
	      }
	    }
	}
}
