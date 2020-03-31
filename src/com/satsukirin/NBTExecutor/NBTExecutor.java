package com.satsukirin.NBTExecutor;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.satsukirin.NBTExecutor.Listeners.Listener1_12_R1;
import com.satsukirin.NBTExecutor.Listeners.Listener1_13_R1;
import com.satsukirin.NBTExecutor.Listeners.Listener1_13_R2;
import com.satsukirin.NBTExecutor.Listeners.Listener1_14_R1;
import com.satsukirin.NBTExecutor.Listeners.Listener1_15_R1;

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
		registerListener(al);
		
		
		getLogger().info("[NBTExecutor] NBTExecutor has been loaded!");
	}
	
	@Override
	public void onDisable() {
		
		
		getLogger().info("[NBTExecutor] Unloaded NBTExecutor!");
	}
	
	public boolean registerListener(ArrayList<NBTCmd> al) {
		String raw = Bukkit.getServer().getClass().getPackage().getName();
		String nms = raw.substring(raw.lastIndexOf(".")+1);
		getLogger().info("[NBTExecutor] NMS version: "+nms);
		switch (nms) {
		case "v1_15_R1":
			Listener1_15_R1 listener115r1 = new Listener1_15_R1(this, al);
			Bukkit.getPluginManager().registerEvents(listener115r1, this);
			getLogger().info("[NBTExecutor] registered Version 1.15.x");
			return true;
		case "v1_14_R1":
			Listener1_14_R1 listener114r1 = new Listener1_14_R1(this, al);
			Bukkit.getPluginManager().registerEvents(listener114r1, this);
			getLogger().info("[NBTExecutor] registered Version 1.14.x");
			return true;
		case "v1_13_R2":
			Listener1_13_R2 listener113r2 = new Listener1_13_R2(this, al);
			Bukkit.getPluginManager().registerEvents(listener113r2, this);
			getLogger().info("[NBTExecutor] registered Version 1.13.x");
			return true;
		case "v1_13_R1":
			Listener1_13_R1 listener113r1 = new Listener1_13_R1(this, al);
			Bukkit.getPluginManager().registerEvents(listener113r1, this);
			getLogger().info("[NBTExecutor] registered Version 1.13");
			return true;
		case "v1_12_R1":
			Listener1_12_R1 listener112r1 = new Listener1_12_R1(this, al);
			Bukkit.getPluginManager().registerEvents(listener112r1, this);
			getLogger().info("[NBTExecutor] registered Version 1.12.x");
			return true;
			
		default:
			return false;
		}
		
		
		
	}

}
