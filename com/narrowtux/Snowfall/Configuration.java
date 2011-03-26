package com.narrowtux.Snowfall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public class Configuration {
	private int frequency = 10;
	private List<Biome> biomesToSnow = new ArrayList<Biome>();
	private int spawnrate = 30;
	private int retries = 10;
	private List<Material> blocksNotToSnow = new ArrayList<Material>();
	private File file;

	public Configuration(File file) {
		this.file = file;
		biomesToSnow.add(Biome.TUNDRA);
		biomesToSnow.add(Biome.ICE_DESERT);
		load();
	}

	private void load() {
		if (file.exists()) {
			FileInputStream input;
			try {
				input = new FileInputStream(file.getAbsoluteFile());
				InputStreamReader ir = new InputStreamReader(input);
				BufferedReader r = new BufferedReader(ir);
				while (true) {
					String line = r.readLine();
					if (line == null)
						break;
					if (!line.startsWith("#")) {
						String splt[] = line.split("=");
						if (splt.length == 2) {
							String key = splt[0];
							String value = splt[1];
							if (key.equalsIgnoreCase("frequency")) {
								try {
									frequency = Integer.valueOf(value);
								} catch (Exception e) {
									frequency = 10;
								}
							}
							if (key.equalsIgnoreCase("spawnrate")) {
								try {
									spawnrate = Integer.valueOf(value);
								} catch (Exception e) {
									spawnrate = 30;
								}
							}
							if (key.equalsIgnoreCase("retries")) {
								try {
									retries = Integer.valueOf(value);
								} catch (Exception e) {
									retries = 30;
								}
							}
							if (key.equalsIgnoreCase("blocksNotToSnow")) {
								try {
									blocksNotToSnow.clear();
									String blocks[] = value.split(",");
									for (String b : blocks) {
										Material m;
										try {
											m = Material.valueOf(b);
										} catch (Exception e) {
											continue;
										}
										blocksNotToSnow.add(m);
									}
								} catch (Exception e) {
									blocksNotToSnow.clear();
								}
							}
							if (key.equalsIgnoreCase("biomesToSnow")) {
								try {
									String biomes[] = value.split(",");
									biomesToSnow.clear();
									for (String b : biomes) {
										Biome biome;
										try{
											biome = Biome.valueOf(b);
										} catch(Exception e){
											continue;
										}
										biomesToSnow.add(biome);
									}
									if(biomesToSnow.size()==0){
										throw new Exception("No biomes set.");
									}
								} catch (Exception e) {
									biomesToSnow.clear();
									biomesToSnow.add(Biome.TUNDRA);
									biomesToSnow.add(Biome.ICE_DESERT);
								}
							}
						}
					}
				}
				r.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out
					.println("No Snowfall configuration file found. Please create one in %bukkitdir%/plugins/Snowfall/snowfall.cfg");
		}
	}

	public int getFrequency() {
		return frequency;
	}

	public List<Biome> getBiomesToSnow() {
		return biomesToSnow;
	}

	public int getSpawnrate() {
		return spawnrate;
	}

	public int getRetries() {
		return retries;
	}

	public List<Material> getBlocksNotToSnow() {
		return blocksNotToSnow;
	}
}
