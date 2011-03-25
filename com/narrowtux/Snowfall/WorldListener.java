package com.narrowtux.Snowfall;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class WorldListener extends org.bukkit.event.world.WorldListener {
	public Snowfall plugin;
	@Override
	public void onChunkLoaded(ChunkLoadEvent event){
		Chunk c = event.getChunk();
		scanChunk(c, false);
	}
	
	@Override
	public void onChunkUnloaded(ChunkUnloadEvent event){
		Chunk c = event.getChunk();
		scanChunk(c, true);
	}
	
	public boolean isAcceptedBiome(Biome b){
		return b.equals(Biome.TAIGA)||b.equals(Biome.ICE_DESERT)||b.equals(Biome.FOREST);
	}
	
	public void scanChunk(Chunk c, boolean remove){
		for(int x = 0;x<16;x++){
			for(int z = 0;z<16;z++){
				Block b = c.getBlock(x, 127, z);
				if(isAcceptedBiome(Snowfall.getBiome(c.getWorld(), x+c.getX()*16, z+c.getZ()*16))){
					for(int y=127;y>0;y--){
						Block block = c.getBlock(x, y, z);
						if(!block.getType().equals(Material.AIR)&&!block.getType().equals(Material.SNOW)){
							if(!remove){
								plugin.blocks.add(b);
							} else {
								plugin.blocks.remove(b);
							}
							break;
						}
					}
				}
			}
		}
	}
}
