package com.narrowtux.Snowfall;

public class SnowDelay implements Runnable {
	public Snowfall plugin;
	private int id;
	@Override
	public void run() {
		plugin.getServer().broadcastMessage("Let it snow!");
		id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,
				plugin.snowBlower, 0, plugin.config.getFrequency());
	}
	
	void stopTask(){
		plugin.getServer().getScheduler().cancelTask(id);
	}

}
