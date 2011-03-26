package com.narrowtux.Snowfall;

import org.bukkit.block.Block;

public class SnowBlower implements Runnable {
	private Snowfall plugin;
	
	public SnowBlower(Snowfall p){
		plugin = p;
	}
	@Override
	public void run() {
		int maxblocks = plugin.config.getSpawnrate();
		if(plugin.blocksToSnow.size()==0){
			plugin.stopBlower(5000);
			return;
		}
		int snowedblocks = 0;
		int retries = 0;
		while(snowedblocks<maxblocks&&plugin.blocksToSnow.size()>0){
			int n = (int) (Math.random()*plugin.blocksToSnow.size());
			Block b = plugin.blocksToSnow.get(n);
			if(plugin.config.getBlocksNotToSnow().contains(b.getType())){
				plugin.blocksToSnow.remove(b);
				plugin.blocks.remove(b);
				snowedblocks++;
				continue;
			}
			if(!plugin.letItSnow(b)){
				retries++;
			} else {
				snowedblocks++;
				retries = 0;
			}
			plugin.blocksToSnow.remove(b);
			plugin.snowedBlocks.add(b);
			if(retries>plugin.config.getRetries())
				break;
		}
	}

}
