package com.narrowtux.Snowfall;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener extends org.bukkit.event.block.BlockListener {
	public Snowfall plugin;
	@Override
	public void onBlockBreak(BlockBreakEvent event){
		if(event.getBlock().getType().equals(Material.SNOW)){
			Block b = event.getBlock().getFace(BlockFace.DOWN);
			plugin.snowedBlocks.remove(b);
			plugin.blocksToSnow.add(b);
		} 
		if(event.getBlock().getType().equals(Material.ICE)){
			Block b = event.getBlock();
			plugin.snowedBlocks.remove(b);
			plugin.blocksToSnow.add(b);
		}
	}
}
