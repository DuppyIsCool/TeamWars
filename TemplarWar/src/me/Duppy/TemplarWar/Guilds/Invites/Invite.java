package me.Duppy.TemplarWar.Guilds.Invites;

import java.util.Date;
import java.util.UUID;

public class Invite {
	private Date expireDate;
	private UUID recipient;
	private String guild;
	
	public Invite(UUID recipient, String guild, Date expireDate) {
		this.expireDate = expireDate;
		this.recipient = recipient;
		this.guild = guild;
	}
	
	public String getGuild() {
		return guild;
	}

	public UUID getRecipient() {
		return recipient;
	}

	public Date getExpireDate() {
		return expireDate;
	}

}

