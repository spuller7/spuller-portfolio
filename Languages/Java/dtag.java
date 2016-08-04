package me.xSPULLERx.dtag;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/* Dwarven Antagonist Plugin (2013)
This plugin is the last completed java plugin I've made.
It was made for the LOTC Minecraft Community to be implemented into the server.
This plugin is intended to be used by staff, who also play a normal character on the server.

What this plugin does:
Those who have permission may type in a command in the console "/dtag" and be prompted with classes they can use.
They could become one of the three characters:
1) Cultist
2) Morph
3) Elf
This command also allows players to remove their current class or add or remove people to the permission's list.

The plugin also handles saving the player's inventory, so when they die, or disconnect from the server, their items won't be lost on their 'real' account.

On accessing a new class, they are given new characters and new items into the inventory, and everythin they had will be removed.  Also, a block will be placed
at the head of the player so that attacking players may identify the type of class a staff member is playing.
*/

public class dtag extends JavaPlugin implements Listener {
	Logger log = Logger.getLogger("Minecraft");
	
	//Hashmaps that store what class gets what armor, or weapon
	private HashMap<String, ItemStack[]> dtag = new HashMap<String, ItemStack[]>();
	private HashMap<String, ItemStack[]> dtagarmour = new HashMap<String, ItemStack[]>();
	
	public static dtag plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public ArrayList<String> Holder;

	private ArrayList<String> dtags = new ArrayList<String>();
	//A cultist has access to spells, thus need a cooldown after being activated
	ArrayList<String> broodCooldown = new ArrayList<String>();
	ArrayList<String> prisonCooldown = new ArrayList<String>();
	ArrayList<String> shardCooldown = new ArrayList<String>();

    public int number = 10;
	//How many snowball object will be spawned in the shard spell
    private int snowballs = 30;
	//Increase speed for elf class
    private double horizSpread = 15/10.0;
    private double vertSpread = 15/10.0;
    private int damage = 4;
	//Movement properties of Morph Class
    private int slowDuration = 80;
    private int slowAmount = 3;

   
    private HashSet<Block> blocks;

	
	File settingsfile;
	FileConfiguration settings;
	
	//load plugin onto server
	@Override
	public void onEnable() {
		loadSettings();
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		  {
		    public void run() {
		      try {
		        dtag.this.saveSettings();
		      }
		      catch (Exception localException)
		      {
		      }
		    }
		  }
		  , 500L, 2000L);
	}
	//Allow plugin to be disabled, but returns items who are currently in class
	public void onDisable() {
		saveSettings();
		for (Player p : getServer().getOnlinePlayers()){
			if (dtag.containsKey(p.getName())){
				leavedtag(p);
			}
		}
	}
	
	private void loadSettings() {
		settingsfile = new File(getDataFolder(), "config.yml");
		settings = YamlConfiguration.loadConfiguration(settingsfile);
		if(settingsfile.exists()) {
			try {
				List<String> dtagList = settings.getStringList("dtagList");
				for(int i = 0; i < dtags.size(); i++){
					dtagList.add(dtags.get(i));
				}
			} catch(Exception e) { log.severe("[Dwarven Antag] Encountered critical error while loading files."); }
			log.info("[Dwarven Antag] Dwarven Antag file successfully loaded.");
		} else {
			log.info("[Dwarven Antag] Dwarven Antag file not found, default file generated.");
		}
	}
	//Save updated to script
	private void saveSettings() {
		try {
			List<String> dtagList = new ArrayList<String>();
			dtagList.addAll(dtags);
			
			settings.set("dtagList", dtagList);
			
			settings.save(settingsfile);
			log.info("[Dwarven Antag] File successfully saved.");
		} catch(Exception e) { log.severe("[Dwarven Antag] Encountered critical error while saving files."); }
	}
	//Allow players to type '/dtag' and verify permissions
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		final Player p = (Player) sender;
		String cmd = command.getName();
			
		if(cmd.equalsIgnoreCase("dtag")){
			//You aint getting past here unless you have permissions
			if(dtags.contains(p.getName()) || sender.hasPermission("dtag.admin")){
			//Displays text in console, almost like a hud display
				if(args.length == 0){
					p.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Dwarven Antag Help");
					p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Cultist" + ChatColor.DARK_GREEN + "] " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "- Become a follower, one that knows of Frost Magic.");	
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Morph" + ChatColor.DARK_GREEN + "] " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "- Play as an Ice Morph to lead Ondnarch's followers." );
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Elf" + ChatColor.DARK_GREEN + "] " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "- Take on the role of a Feral Elf.");
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Clear" + ChatColor.DARK_GREEN + "] " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "- Clears current class and returns your inventory.");
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Add <Player>" + ChatColor.DARK_GREEN + "] " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "- Permit a member to be a Dwarven Antag.");
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Remove <Player>" + ChatColor.DARK_GREEN + "] " +  ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "- Remove a member from the Dwarven Antag.");
					return true;
				}
				//did you type more than just '/dtag __ __'
				else if (args.length > 0) {
			
					if(args[0].equalsIgnoreCase("Clear")){
						if (!dtag.containsKey(p.getName())){
							p.sendMessage(ChatColor.RED + "You are not serving Ondnarch.");
							return true;
						}else{
							leavedtag(p);
							return true;
						}
					}
					//assign cultist class
					else if(args[0].equalsIgnoreCase("Cultist")){
						//add player to current dtag elf
						list(p);
						p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + ("You are now playing as a Cultist of Ondnarch."));
						//changes head to ice block
						p.getEquipment().setHelmet(new ItemStack(Material.ICE));
						norm(p);
						//If has a frost glaive in first spot in inventory (wand) 
						if(p.getInventory().firstEmpty() != -1 && !(p.getInventory().contains(getFrostGlaive(1)) || p.getInventory().contains(getFrostGlaive(2)) || p.getInventory().contains(getFrostGlaive(3)))){
							p.getInventory().addItem(getFrostGlaive(1));
							p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Ondnarch has gifted you with a Frost Glaive.");		
						}
						//error log
						else{
							p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "You were not gifted a Frost Staff as your inventory is full or you already have one.");
						}
						//new health properties
						p.setMaxHealth(28.0);
						p.setHealth(28.0);
						return true;
					}
					//assign elf class
					else if(args[0].equalsIgnoreCase("Elf")){
						list(p);
						norm(p);
						//set new properties
						p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + ("You are now playing as a Corrupted Snow Elf."));
						p.getEquipment().setHelmet(new ItemStack(Material.SNOW_BLOCK));	
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 0));
						p.setMaxHealth(16.0);
						//add weapon
						p.getInventory().addItem(getDagger(1));
						return true;
					}
					//assign morph class
					else if(args[0].equalsIgnoreCase("Morph")){
						list(p);
						norm(p);
						ItemStack icehead = new ItemStack(Material.PACKED_ICE, 1);
						p.getEquipment().setHelmet(new ItemStack(icehead));
						ItemMeta gr = icehead.getItemMeta();
						gr.setDisplayName (ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Morph Head");
						p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + ("You are now playing as an Ice Morph."));
						//weidling a ice block as weapon
						ItemStack ice = new ItemStack(Material.PACKED_ICE, 1);
						ItemMeta ic = ice.getItemMeta();
						ArrayList<String> tt = new ArrayList<String>();
						ic.setDisplayName (ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Ice Shard");
						tt.add(ChatColor.GOLD + "A shard that radiates a soft glow of blue");
						tt.add(ChatColor.GOLD + "and gives chills to those who hold the ice.");
						ic.setLore(tt);
						ice.setItemMeta(ic);
						p.getInventory().addItem(new ItemStack(ice));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 2));
						p.setMaxHealth(40.0);
						p.setHealth(40.0);
						return true;
					}
					//add or remove players from permissions
					else if(args.length == 2 && sender.hasPermission("dtag.admin")){
						if(args[0].equalsIgnoreCase("add")){
							if(getServer().getPlayerExact(args[1]) != null){
								dtags.add(getServer().getPlayerExact(args[1]).getName());
								p.sendMessage(ChatColor.GREEN + "Player successfully added to Dwarven Antag.");
								getServer().getPlayerExact(args[1]).sendMessage(ChatColor.YELLOW + "Ondnarch expects great things!");
								return true;
							}
							else{
								p.sendMessage(ChatColor.RED + "Player is not online. They cannot be added to Dwarven Antag.");
								return true;
							}
						}
						else if(args[0].equalsIgnoreCase("remove")){
							if(dtags.contains(getServer().getOfflinePlayer(args[1]).getName())){
								dtags.remove(getServer().getOfflinePlayer(args[1]).getName());
								p.sendMessage(ChatColor.GREEN + "Player successfully removed from Dwarven Antag.");
								return true;
							}
							else{
								p.sendMessage(ChatColor.RED + "There are no such player in the Dwarven Antag.");
								return true;
							}
						}
					}
					else{
						p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + ("That class is not recognized under Ondnarch.  Please select [Cultist] [Morph] [Elf]"));
						return true;
					}
				}
			}
			else{
				sender.sendMessage(ChatColor.RED + "You do not believe in the ways of Ondnarch. Access Denied!");
				return true;
			}
		}return true;
	}
	//granted in elf assign
	private ItemStack getDagger(int type) {
		ItemStack dagger = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta dg = dagger.getItemMeta();							
		ArrayList<String> dd = new ArrayList<String>();
		if(type == 1){
		//dagger properties
			dg.setDisplayName (ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Hailstorm Dagger");
			dd.add(ChatColor.GOLD + "The dagger feels cold");
			dd.add(ChatColor.GOLD + "as frost covers the blade.");
			dg.setLore(dd);
			dagger.setItemMeta(dg);
			dagger.addEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
		}
		return dagger;
	}
	

	private void leavedtag(Player p) {
		//returns inventory
		p.getInventory().clear();
		p.getInventory().setContents(dtag.get(p.getName()));
		//puts back on armour you had on
		p.getInventory().setArmorContents(dtagarmour.get(p.getName()));
		dtag.remove(p.getName());
		dtagarmour.remove(p.getName());
		//remove all class properties
		for (PotionEffect effect : p.getActivePotionEffects()){
			p.removePotionEffect(effect.getType());
		}
		p.setMaxHealth(20.0);
		p.setFoodLevel(20);
		
	}
	private ItemStack getFrostGlaive(int type) {
		ItemStack glaive = new ItemStack(Material.DIAMOND_HOE, 1);
		ItemMeta gl = glaive.getItemMeta();		
		gl.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Frost Glaive");
		List<String> ll = new ArrayList<String>();
		//frost glaive has different ints to display what spell is active
		if(type == 1){
			ll.add(ChatColor.GRAY + "Current Spell: " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Frozen Prison");
		}
		else if(type == 2){
			ll.add(ChatColor.GRAY + "Current Spell: " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Shards of Glaciers");
		}
		else if(type == 3){
			ll.add(ChatColor.GRAY + "Current Spell: " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Brood Summoner");
		}
		else{
			ll.add(ChatColor.GOLD + "A Powerful Glaive covered in frost.");
		}
		gl.setLore(ll);
		glaive.setItemMeta(gl);
		return glaive;
	}

	@EventHandler
	//if kicked off server, don't screw up
	public void onPlayerKick(PlayerKickEvent e){
		if (dtag.containsKey(e.getPlayer().getName())){
			leavedtag(e.getPlayer());
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		if (dtag.containsKey(e.getPlayer().getName())){
			leavedtag(e.getPlayer());
		}
	}
	//Every class gets food and weapons so it's normal stuff -- Get it?
	public void norm(Player p) {
		p.getInventory().addItem(new ItemStack(Material.PUMPKIN_PIE, 32));
		ItemStack iceleg = new ItemStack(Material.IRON_LEGGINGS, 1);
		ItemMeta oleg = iceleg.getItemMeta();
		oleg.setDisplayName (ChatColor.GOLD + "Ondnarch Forged Leggings");
		iceleg.setItemMeta(oleg);
		p.getInventory().setLeggings(new ItemStack(iceleg));
		ItemStack icechest = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemMeta ochest = icechest.getItemMeta();
		ochest.setDisplayName (ChatColor.GOLD + "Ondnarch Forged Chestplate");
		icechest.setItemMeta(ochest);
		p.getInventory().setChestplate(new ItemStack(icechest));
		ItemStack iceboots = new ItemStack(Material.IRON_BOOTS, 1);
		ItemMeta oboot = iceboots.getItemMeta();
		oboot.setDisplayName (ChatColor.GOLD + "Ondnarch Forged Boots");
		iceboots.setItemMeta(oboot);
		p.getInventory().setBoots(new ItemStack(iceboots));
	}
	//save inventory contents
	public void list(Player p) {
		if (!dtag.containsKey(p.getName())){
			dtag.put(p.getName(), p.getInventory().getContents());
			dtagarmour.put(p.getName(), p.getInventory().getArmorContents());
			p.sendMessage(ChatColor.GOLD + "Your inventory has been saved and cleared.");
			p.getInventory().clear();
			for (PotionEffect effect : p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
			}
		}else{
			p.getInventory().clear();
			for (PotionEffect effect : p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
			}
		}
	}
	//apply damage when snowball hits you : normally they don't aplly damage, only knockback
	@EventHandler
    public void shardHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player damager = (Player) event.getDamager();
            Player target = (Player) event.getEntity();
            if (damager.getItemInHand().getItemMeta().getDisplayName().contains("Ice Shard")){
            		target.damage(6.0);
            			if(dtag.containsKey(damager.getName())){
            			damager.getWorld().playSound(damager.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
            			    Vector v = target.getLocation().toVector().subtract(damager.getLocation().toVector())
                                    .setY(0)
                                    .normalize()
                                    .multiply(2)
                                    .setY(1);
                    target.setVelocity(v);
            		}
            	}
            }
        }
	//Switching spells
	@EventHandler
	public void onGlaiveInteract(final PlayerInteractEvent e){
		if(dtag.containsKey(e.getPlayer().getName())){
				if(e.getItem() != null){
					if(e.getItem().hasItemMeta()){
						if(e.getItem().getType().equals(Material.DIAMOND_HOE)){
							if(e.getItem().getItemMeta().getDisplayName().contains("Frost Glaive")){
								if(e.getItem().getItemMeta().getLore().get(0).contains("Current Spell:")){
									if(e.getAction().equals(Action.LEFT_CLICK_AIR)){
										if(e.getItem().getItemMeta().getLore().get(0).contains(("Brood Summoner"))){
												if(!broodCooldown.contains(e.getPlayer().getName())){
													castBroodSummoner(e.getPlayer());
													broodCooldown.add(e.getPlayer().getName());
													Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
														public void run(){
															broodCooldown.remove(e.getPlayer().getName());
														}
													}, 300);
												}
												else{
													e.getPlayer().sendMessage(ChatColor.GOLD + "You must wait before using that spell again!");
												}
										}
										else if(e.getItem().getItemMeta().getLore().get(0).contains(("Frozen Prison"))){
													if(!prisonCooldown.contains(e.getPlayer().getName())){
														castFrozenPrison(e.getPlayer());
														prisonCooldown.add(e.getPlayer().getName());
														Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
															public void run(){
																prisonCooldown.remove(e.getPlayer().getName());
															}
														}, 300);
													}
													else{
														e.getPlayer().sendMessage(ChatColor.GOLD + "You must wait before using that spell again!");
													}
										}
										else if(e.getItem().getItemMeta().getLore().get(0).contains(("Shards of Glaciers"))){
											if(!shardCooldown.contains(e.getPlayer().getName())){
												castGlacierShard(e.getPlayer());
												shardCooldown.add(e.getPlayer().getName());
												Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
													public void run(){
														shardCooldown.remove(e.getPlayer().getName());
													}
												}, 160);
											}
											else{
												e.getPlayer().sendMessage(ChatColor.GOLD + "You must wait before using that spell again!");
											}
									}
								}									
								else if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
									if(e.getItem().getItemMeta().getLore().get(0).contains("Brood Summoner")){
										e.getPlayer().sendMessage(ChatColor.GRAY + "Current Spell: " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Frozen Prison");
										e.getPlayer().getInventory().setItemInHand(getFrostGlaive(1));
								}
									else if(e.getItem().getItemMeta().getLore().get(0).contains("Frozen Prison")){
										e.getPlayer().sendMessage(ChatColor.GRAY + "Current Spell: " + ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Shards of Glaciers");
										e.getPlayer().getInventory().setItemInHand(getFrostGlaive(2));
									}
									else{
										e.getPlayer().sendMessage(ChatColor.GRAY + "Current Spell: " +  ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Brood Summoner");
										e.getPlayer().getInventory().setItemInHand(getFrostGlaive(3));
									}
								}
							else{
								return;
								}
						}
					}
				}
			}
		}
	}         
}	
	//Favorite spell
	//Will block person in 2x1 blocks of ice and will eventually 'melt'
	//has to teleport player into block because they are likely moving and will glitch up
	public void castFrozenPrison(final Player p){
		new BukkitRunnable(){
			@SuppressWarnings("deprecation")
			List<Block> line = p.getLineOfSight(null, 60);
			int i = 2;
			@Override
			public void run() {
				if(i < line.size()){
					Block next = line.get(i);
					next.getWorld().playEffect(next.getLocation().add(0, .05, 0), Effect.STEP_SOUND, Material.ICE);
					next.getWorld().playEffect(next.getLocation(), Effect.STEP_SOUND, Material.COAL_BLOCK, 3);
					next.getWorld().playEffect(next.getLocation(), Effect.STEP_SOUND, Material.ANVIL, 4);
					i++;
				}
				else{
					p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 5);
					castFrostPrisonEnd(line.get(line.size() - 1).getLocation().add(0, 1, 0));
					this.cancel();
				}
			}
			 

		}.runTaskTimer(this, 0, 1);
	}
	//remove prison
private void castFrostPrisonEnd(Location l) {
	for(final Player p : l.getWorld().getPlayers()){
		if(!dtag.containsKey(p.getPlayer().getName())){	
			if(p.getLocation().distance(l) < 5 ){
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 180, 2));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 4));     
				final ArrayList<Block> tombBlocks = new ArrayList<Block>();
				Block feet = p.getLocation().getBlock();
    	
				Block temp = feet.getRelative(2,0,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(2,1,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(2,0,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(2,0,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(2,1,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(2,1,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-2,0,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-2,1,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-2,1,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-2,1,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-2,0,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-2,0,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				//Front & Back sides completed
				temp = feet.getRelative(0,0,2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,1,2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,0,2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,0,2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,1,2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,1,2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,0,-2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,1,-2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,0,-2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
						tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,0,-2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,1,-2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,1,-2);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				//For top and bottom
				//top
				temp = feet.getRelative(0,2,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,2,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,2,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,2,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,2,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,2,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,2,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,2,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,2,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
						tombBlocks.add(temp);
				}
				//bottom
				temp = feet.getRelative(0,-1,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,-1,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,-1,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(1,-1,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,-1,0);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,-1,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(-1,-1,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,-1,1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
					tombBlocks.add(temp);
				}
				temp = feet.getRelative(0,-1,-1);
				if (temp.getType() == Material.AIR) {
					temp.setType(Material.ICE);
						tombBlocks.add(temp);
				}
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
					public void run(){
			                    for (Block block : tombBlocks) {
			                            if (block.getType() == Material.ICE  || block.getType() == Material.WATER) {
			                                    block.setType(Material.AIR);
			                            }
			                    }
			                    blocks.removeAll(tombBlocks);
						blocks.addAll(tombBlocks);
						p.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "The Ice Breaks Free!");
					}
				}, 200);
			}
		}
	}
}
//players are allowed to destroy prison to let people out
@EventHandler
public void onBlockBreak(BlockBreakEvent event) {
        if (blocks.contains(event.getBlock())) {
                event.setCancelled(true);
        }
}
//spell that spawns mobs with swords and ice head
	public void castBroodSummoner(final Player p){
		new BukkitRunnable(){
			@SuppressWarnings("deprecation")
			List<Block> line = p.getLineOfSight(null, 45);
			int i = 0;
			@Override
			//btw these are particle effecs that play when spell ray hits surface
			public void run() {
				if(i < line.size()){
					Block next = line.get(i);
					next.getWorld().playEffect(next.getLocation().add(0, .05, 0), Effect.STEP_SOUND, Material.BEACON);
					next.getWorld().playEffect(next.getLocation().add(0, .05, 0), Effect.STEP_SOUND, Material.WATER, 3);
					next.getWorld().playEffect(next.getLocation(), Effect.STEP_SOUND, Material.ICE, 4);
					i++;
				}
				else{
					p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
					castBroodSummonerEnd(line.get(line.size() - 1).getLocation().add(0, 1, 0));
					this.cancel();
				}
			}
		}.runTaskTimer(this, 0, 2);
	}
	//time delay to allow players to prepare
	private void castBroodSummonerEnd(final Location l){
		for(Player p : l.getWorld().getPlayers()){
			if(p.getLocation().distance(l) < 32){
				p.sendMessage(ChatColor.DARK_RED + "[!] Minions erupt out of the summoned ice. [!]");		
			}
		}
			for(int i = 0; i < 3; i++){
				PigZombie pz = (PigZombie) l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
				pz.setCustomName(ChatColor.BOLD + "" + ChatColor.GOLD + "Odnarch's Minion");
				pz.setCustomNameVisible(true);
				pz.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
				pz.getEquipment().setItemInHandDropChance(0);
				pz.getEquipment().setHelmet(new ItemStack(Material.ICE));
				pz.getEquipment().setHelmetDropChance(0);
				pz.setAngry(true);
			}		
					for(Player p : l.getWorld().getPlayers()){
						if(p.getLocation().distance(l) < 5 ){
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2));
							p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 1));
						}
					}
	}
	public void castGlacierShard (final Player p){
			p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
            Random rand = new Random();
            Vector mod;
            for (int i = 0; i < snowballs; i++) {
                    Snowball snowball = p.launchProjectile(Snowball.class);
                    mod = new Vector((rand.nextDouble() - .5) * horizSpread, (rand.nextDouble() - .5) * vertSpread, (rand.nextDouble() - .5) * horizSpread);
                    snowball.setVelocity(snowball.getVelocity().add(mod));
            }
    }
	//don't hurt other dtag classes
@SuppressWarnings("deprecation")
@EventHandler(priority=EventPriority.LOWEST)
public void onEntityDamage(EntityDamageByEntityEvent event) {
    if (damage <= 0 || event.isCancelled() || !(event.getEntity() instanceof LivingEntity)) return;
 
    if (!(event.getDamager() instanceof Snowball)) return;
    	if(!dtag.containsKey(event.getEntity())){
                    if (event.isCancelled()) {
                            event.setCancelled(true);
                    } else {
                            event.setDamage(damage);
                    }
         
            }
    	else {
            event.setCancelled(true);
    	}
}
//spells won't effect other dtag cultist
@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
public void onEntityDamage2(EntityDamageByEntityEvent event) {
    if (slowAmount <= 0 || slowDuration <= 0) return;
   
    if (!(event.getDamager() instanceof Snowball)) return;
   
    if (event.getDamage() == damage) {
            ((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slowDuration, slowAmount), true);
    }
}
	
	@EventHandler
	public void onEntityTargetAntag(EntityTargetEvent e){
		if(e.getEntityType().equals(EntityType.PIG_ZOMBIE)){
				if(e.getTarget() instanceof Player){
					Player p = (Player) e.getTarget();
					if(dtag.containsKey(p.getName())){
						e.setCancelled(true);
					}
				}
			}
		}
	@EventHandler
	public void onDtagDeath(PlayerDeathEvent e){
		if(dtag.containsKey(e.getEntity().getName())){
				for(Player p: e.getEntity().getWorld().getPlayers()){
					if(p.getLocation().distance(e.getEntity().getLocation()) < 32){
						p.sendMessage(ChatColor.YELLOW + "[!] The dark creature cracks into icicles around you.");
					}
				}
			}
			for(int i = 0; i < e.getDrops().size(); i++){
				if(e.getDrops().get(i).equals(getFrostGlaive(1)) || e.getDrops().get(i).equals(getFrostGlaive(2)) || e.getDrops().get(i).equals(getFrostGlaive(3))){
					e.getDrops().set(i, getFrostGlaive(4));
				}
			}
		}
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
	    if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Ice Shard")){    
	    	event.setCancelled(true);
	    }
	}
}
	


