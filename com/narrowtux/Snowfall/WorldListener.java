package com.narrowtux.Snowfall;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
		return plugin.config.getBiomesToSnow().contains(b);
	}
	
	public void scanChunk(Chunk c, boolean remove){
		for(int x = 0;x<16;x++){
			for(int z = 0;z<16;z++){
				if(isAcceptedBiome(Snowfall.getBiome(c.getWorld(), x+c.getX()*16, z+c.getZ()*16))){
					for(int y=127;y>0;y--){
						Block block = c.getBlock(x, y, z);
						if(!block.getType().equals(Material.AIR)&&!block.getType().equals(Material.SNOW)){
							if(plugin.config.getBlocksNotToSnow().contains(block.getType())){
								break;
							}
							if(!remove){
								plugin.blocks.add(block);
								if(block.getType().equals(Material.ICE)||block.getFace(BlockFace.UP).getType().equals(Material.SNOW)){
									plugin.snowedBlocks.add(block);
								} else {
									plugin.blocksToSnow.add(block);
								}
							} else {
								plugin.blocks.remove(block);
								plugin.blocksToSnow.remove(block);
								plugin.snowedBlocks.remove(block);
							}
							break;
						}
					}
				}
			}
		}
	}
}
