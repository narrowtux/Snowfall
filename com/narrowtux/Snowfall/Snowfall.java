package com.narrowtux.Snowfall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class Snowfall extends JavaPlugin {
	private WorldListener worldListener = new WorldListener();
	public HashMap<Chunk, List<Block>> blocksToSnow;
	public List<Block> blocks;

	public Snowfall() {
		blocks = new ArrayList<Block>();
	}

	@Override
	public void onDisable() {
		System.out.println("Snowfall by narrowtux disbled.");
	}

	@Override
	public void onEnable() {
		System.out.println("Snowfall by narrowtux enabled.");
		System.out.println("Loading all chunks...");
		worldListener.plugin = this;
		for(int wi = 0;wi<getServer().getWorlds().size();wi++){
			World w = getServer().getWorlds().get(wi);
			for(int ci = 0; ci<w.getLoadedChunks().length; ci++){
				Chunk c = w.getLoadedChunks()[ci];
				worldListener.scanChunk(c, false);
				System.out.println("world "+wi+"/"+getServer().getWorlds().size()+": "+ci+"/"+w.getLoadedChunks().length+"loaded.");
			}
		}
		getServer().getPluginManager().registerEvent(Type.CHUNK_LOADED,
				worldListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Type.CHUNK_UNLOADED,
				worldListener, Priority.Normal, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new SnowBlower(this), 10, 100);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String args[]) {
		if (cmd.getName().equals("snow")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Chunk c = p.getLocation().getWorld()
				.getChunkAt(p.getLocation());
				letItSnow(c);
				sender.sendMessage(ChatColor.AQUA
						+ "Let it snow! Let it snow! Let it snow! ~Sammy Cahn");
				return true;
			}
		}
		if (cmd.getName().equals("melt")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Chunk c = p.getLocation().getWorld()
				.getChunkAt(p.getLocation());
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						for (int y = 127; y >= 0; y--) {
							Block b = c.getBlock(x, y, z);
							if (b.getType().equals(Material.SNOW)) {
								b.setType(Material.AIR);
								break;
							} else if (b.getType().equals(Material.ICE)) {
								b.setType(Material.WATER);
							}
						}
					}
				}
				sender.sendMessage(ChatColor.GREEN
						+ "No matter how long the winter, spring is sure to follow. ~Proverb");
				return true;
			}
		}
		if (cmd.getName().equals("biome")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(getBiome(p.getWorld(),p.getLocation().getBlockX(), p.getLocation().getBlockZ()).toString());
				return true;
			}
		}
		return false;
	}

	public boolean letItSnow(Block b) {
		if (!b.getType().equals(Material.SNOW)) {
			if(b.getType().equals(Material.WATER)||b.getType().equals(Material.STATIONARY_WATER)){
				b.setType(Material.ICE);
				return true;
			} else {
				b.getFace(BlockFace.UP).setType(Material.SNOW);
				return true;
			}
		}
		return false;
	}

	public boolean letItSnow(int x, int z, Chunk c) {
		for (int y = 127; y >= 0; y--) {
			Block b = c.getBlock(x, y, z);
			if (!b.getType().equals(Material.AIR)) {
				if (b.getType().equals(Material.STATIONARY_WATER)
						|| b.getType().equals(Material.WATER)) {
					b.setType(Material.ICE);
					return true;
				}
				if (!b.getType().equals(Material.SNOW)) {
					b.getFace(BlockFace.UP).setType(Material.SNOW);
					return true;
				}
				break;
			}
		}
		return false;
	}

	public void letItSnow(Chunk c) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				letItSnow(x, z, c);
			}
		}
	}

	static Biome getBiome(World w, int x, int z){
		CraftWorld world = (CraftWorld)w;
		return Biome.valueOf(world.getHandle().a().a(x, z).m.toUpperCase().replace(" ", "_"));
	}
}
