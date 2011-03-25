package com.narrowtux.Snowfall;

import org.bukkit.block.Block;

public class SnowBlower implements Runnable {
	private Snowfall plugin;
	
	public SnowBlower(Snowfall p){
		plugin = p;
	}
	@Override
	public void run() {
		int maxblocks = 30;
		if(plugin.blocks.size()==0){
			maxblocks = 0;
		}
		int snowedblocks = 0;
		int retries = 0;
		while(snowedblocks<maxblocks){
			int n = (int) (Math.random()*plugin.blocks.size());
			Block b = plugin.blocks.get(n);
			if(!plugin.letItSnow(b)){
				retries++;
			} else {
				snowedblocks++;
				retries = 0;
			}
			if(retries>100)
				break;
		}
	}

}
