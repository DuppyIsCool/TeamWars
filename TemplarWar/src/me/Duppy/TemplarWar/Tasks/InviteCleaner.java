package me.Duppy.TemplarWar.Tasks;


import org.bukkit.scheduler.BukkitRunnable;

import me.Duppy.TemplarWar.Guilds.Invites.InviteManager;
import me.Duppy.TemplarWar.Main.Plugin;

public class InviteCleaner extends BukkitRunnable {
	private int counter;
    public InviteCleaner(int counter) {
        if (counter < 1) {
            throw new IllegalArgumentException("Counter must be greater than 1");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        if (counter > 0) { 
        	counter--;
        } else {
        	this.counter = Plugin.plugin.getConfig().getInt("defaults.invitetime");
        	InviteManager.cleanOldInvites();
        }
    }
}
