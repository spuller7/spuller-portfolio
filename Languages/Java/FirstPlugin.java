package me.xSPULLERx.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
/*Please ignore the question why I would make this plugin, but I was young and it was my first plugin ever made.
Because it is my first plugin, I feel it is necessary to add the point where I started.  It's always nice revisiting this elementary script.

The class handles passing gas when a user types in the command 'passgas'
There are different outcomes with low probabilities such as igniting or excreting.  
This is actually something very similar to a class in Ark Survival where players do something similar.
*/
public class FirstPlugin extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static Plugin plugin;
	
	public void onDisable(){
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info(pdffile.getName() + "Has Been Disabled!");
	}
	
	public void onEnable(){
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info(pdffile.getName() + "Version" +pdffile.getVersion() + "Has Been Enabled!");
	}
	//cooldown it takes to enable command again
	ArrayList<Player> cooldown = new ArrayList<Player>();
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		final Player player = (Player) sender;
		
		
		//type command
		if(cmd.getName().equalsIgnoreCase("passgass")){
		
			if(!cooldown.contains(player)){
				//sound effect
				player.getWorld().playSound(player.getLocation(), Sound.SLIME_WALK2, 1, 1);
				//where they can hear it
				int radius = 5;
				int radiusSquared = radius * radius;
				List<Player> players = player.getWorld().getPlayers();
				for (Player otherPlayer : players){
					if (otherPlayer == player) continue;
					if (player.getLocation().distanceSquared(otherPlayer.getLocation()) < radiusSquared){
					//display to other players
						otherPlayer.sendMessage(ChatColor.GOLD + "You smell something odd coming from " + player.getName());
						otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
						return true;
				 }
			}
			//probability counter
			Random outcome = new Random();
			int chance = 1+outcome.nextInt(20);
				//prob of excreting
				if(chance == 1){
					player.sendMessage(ChatColor.RED + "It seems you have misjudged your gas.  You feel yourself excrete!");
					ItemStack poop = new ItemStack(Material.WOOL, 1, (short) 12);
					//make brown wool fall behind you
					ItemMeta meta = poop.getItemMeta();
					meta.setDisplayName ("Poop");
					poop.setItemMeta(meta);
					World world = player.getWorld();
					Location location = player.getLocation();
					world.dropItemNaturally(location, poop); 
					return true;
					
					}
					//normal attribute
				else {
					player.sendMessage(ChatColor.GOLD + "You feel the release of gas exit your body.");
				}
			cooldown.add(player);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					cooldown.remove(player);
				}
			}, 600);
			//if fire is near you when you type this command
			int radiuss = 2;
			Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
			for (int x = -(radiuss); x <= radiuss; x ++) {
			  for (int y = -(radiuss)+2; y <= radiuss+1; y ++) {
			    for (int z = -(radiuss); z <= radiuss; z ++) {
			       if(block.getRelative(x, y, z).getType().equals(Material.FIRE)) {
			        //set ablaze
			    	player.setFireTicks(40);
			    	player.sendMessage(ChatColor.RED + "Your gas is ignited by a nearby flame setting you ablaze!");
			    	return true;
			       }
			    }
			  }
			}
			
				}
			
			else{
				sender.sendMessage(ChatColor.RED + "You don't have the need to fart!");
			
				}
			
			}
		return true;
		
		}
}
				
	
		
	

