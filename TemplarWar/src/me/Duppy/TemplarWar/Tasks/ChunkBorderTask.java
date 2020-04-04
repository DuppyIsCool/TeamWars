package me.Duppy.TemplarWar.Tasks;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import me.Duppy.TemplarWar.Guilds.Guilds.GuildManager;
import me.Duppy.TemplarWar.Main.Plugin;

public class ChunkBorderTask extends BukkitRunnable {
	private int claimcounter;
	private Chunk c;
    private ArrayList<Block> blocks;
    @SuppressWarnings("unchecked")
	public ChunkBorderTask(int counter,int claimcounter,Chunk c, ArrayList<Block> blocks) {
    	if (claimcounter < 1) {
            throw new IllegalArgumentException("Counter must be greater than 1");
        } else {
            this.blocks = (ArrayList<Block>) blocks.clone();
            this.c = c;
            this.claimcounter = claimcounter;
        }
    }
    
	@Override
	public void run() {
		if(claimcounter > 0) {
        	claimcounter--;
        }
        else {
        	removeBorder();
        	GuildManager.chunkmap.remove(c);
        	this.cancel();
        }
	}
	
	private void removeBorder() {
		if(blocks.size() > 0) {
	    	for(Block b : blocks) {
    			b.setType((Material) b.getMetadata("SPAWNED").get(0).value());
    			b.removeMetadata("SPAWNED", Plugin.plugin);
	    	}
		}
	}
}
