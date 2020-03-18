package com.satsukirin.NBTExecutor;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class NBTExecutor extends JavaPlugin {
	
	
	@Override
	public void onEnable() {
		
		//if first enable, save default config file
		if(!getDataFolder().exists()) {
			saveDefaultConfig();
		}
		
		FileConfiguration config = getConfig();
		ConfigurationSection vars = config.getConfigurationSection("cmds");
		ArrayList<NBTCmd> al = new ArrayList<NBTCmd>();
		int count=0;
		for (String str : vars.getKeys(false)) {
			al.add(new NBTCmd(str, config.getString("cmds."+str+".trigger"), config.getString("cmds."+str+".command"), config.getString("cmds."+str+".key"), config.getString("cmds."+str+".value")));
			count++;
		}
		getLogger().info("[NBTExecutor] Loaded "+count+" commands");
		NBTCListener listener = new NBTCListener(this, al);
		Bukkit.getPluginManager().registerEvents(listener, this);
		
		
		
		getLogger().info("[NBTExecutor] NBTExecutor has been loaded!");
	}
	
	@Override
	public void onDisable() {
		
		
		getLogger().info("[NBTExecutor] Unloaded NBTExecutor!");
	}

}
