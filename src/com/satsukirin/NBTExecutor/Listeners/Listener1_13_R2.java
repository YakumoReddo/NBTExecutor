package com.satsukirin.NBTExecutor.Listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.satsukirin.NBTExecutor.NBTCmd;
import com.satsukirin.NBTExecutor.NBTExecutor;

import net.minecraft.server.v1_13_R2.NBTTagCompound;

public class Listener1_13_R2 implements Listener {
	
	private NBTExecutor plugin;
	private ArrayList<NBTCmd> al;
	
	public Listener1_13_R2(NBTExecutor p,ArrayList<NBTCmd> a) {
		plugin=p;
		al=a;
		plugin.getLogger().info("[NBTExecutor] Listener registered!");
	}
	
	
	
	@EventHandler
	public void onSwap(PlayerSwapHandItemsEvent e) {
		Player player = e.getPlayer();
		if(!player.isSneaking())return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item.getType().equals(Material.AIR))return;
		for (NBTCmd nbtCmd : al) {
			if(!nbtCmd.getTrigger().equals("SF"))continue;
			if(compareNBT(item, nbtCmd.getKey(), nbtCmd.getValue())) {
				e.setCancelled(true);
				for (String str : nbtCmd.getCommand().split(";")) {
					if(str.equals(""))continue;
					if(str.substring(0,8).equalsIgnoreCase("console:")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),str.substring(8));
					}
					else {
							player.performCommand(str);
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getHand().equals(EquipmentSlot.OFF_HAND))return;
		Player player = e.getPlayer();
		String trigger = (player.isSneaking()?"S":"");
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item.getType().equals(Material.AIR))return;
		if(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			trigger+="L";
		}
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			trigger+="R";
		}
		for (NBTCmd nbtCmd : al) {
			if(!nbtCmd.getTrigger().equals(trigger))continue;
			if(compareNBT(item, nbtCmd.getKey(), nbtCmd.getValue())) {
				e.setCancelled(true);
				for (String str : nbtCmd.getCommand().split(";")) {
					if(str.equals(""))continue;
					if(str.substring(0,8).equalsIgnoreCase("console:")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),str.substring(8));
					}
					else {
						player.performCommand(str);
					}
				}
			}
		}
	}
	
	
	public boolean compareNBT(ItemStack item,String key,String value) {
		net.minecraft.server.v1_13_R2.ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
		if(!nmsitem.hasTag())return false;
		NBTTagCompound nbttc = nmsitem.getTag();
		if (!nbttc.hasKey(key)) return false;
		if(nbttc.getString(key).equals(value))return true;
		else return false;
		
	}
	
}
