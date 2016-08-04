package me.xSPULLERx.MusicCreation;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
 
public class SongWriting extends JavaPlugin{
    public final Logger log = Logger.getLogger("Minecraft");
    public static SongWriting plugin;
    public final HashMap<Integer, ArrayList<Integer>> Music = new HashMap<Integer, ArrayList<Integer>>();
    public final HashMap<Integer, ArrayList<Integer>> Music2 = new HashMap<Integer, ArrayList<Integer>>();
        int b = 0;     
       
            public void onDisable(){
                PluginDescriptionFile pdffile = this.getDescription();
                this.log.info(pdffile.getName() + "Has Been Disabled!");
            }
               
            public void onEnable(){
                PluginDescriptionFile pdffile = this.getDescription();
                this.log.info(pdffile.getName() + "Version" +pdffile.getVersion() + "Has Been Enabled!");
                       
                ItemStack blankSheet = new ItemStack(Material.MAP, 1);
                ItemMeta bs = blankSheet.getItemMeta();
                bs.setDisplayName (ChatColor.GOLD + "Blank Music Sheet");
                ArrayList<String> tt = new ArrayList<String>();
                tt.add(ChatColor.GRAY + "Sheet consists of horizontal lines.");
                bs.setLore(tt);
                blankSheet.setItemMeta(bs);
                ShapedRecipe bms = new ShapedRecipe (new ItemStack(blankSheet));
                bms.shape("*#*","*%*","*#*").setIngredient('*', Material.STRING).setIngredient('#', Material.INK_SACK).setIngredient('%', Material.BOOK_AND_QUILL);
                Bukkit.getServer().addRecipe(bms);
                       
                ItemStack drum = new ItemStack(Material.PISTON_BASE, 1);
                ItemMeta dr = drum.getItemMeta();
                dr.setDisplayName (ChatColor.GOLD + "Drum");
                drum.setItemMeta(dr);
                ShapedRecipe dru = new ShapedRecipe (new ItemStack(drum));
                dru.shape("   ","***","#^#").setIngredient('*', Material.LEATHER).setIngredient('#', Material.LOG).setIngredient('^', Material.WOOD_PLATE);
                Bukkit.getServer().addRecipe(dru);
                       
                ItemStack lute = new ItemStack(Material.BOW, 1);
                ItemMeta lu = lute.getItemMeta();
                lu.setDisplayName (ChatColor.GOLD + "Lute");
                lute.setItemMeta(lu);
                ShapedRecipe lut = new ShapedRecipe (new ItemStack(lute));
                lut.shape("*# "," %*","*# ").setIngredient('*', Material.STICK).setIngredient('#', Material.STRING).setIngredient('%', Material.FEATHER);
                Bukkit.getServer().addRecipe(lut);
                       
                ItemStack cello = new ItemStack(Material.NOTE_BLOCK, 1);
                ItemMeta ce = cello.getItemMeta();
                ce.setDisplayName (ChatColor.GOLD + "Cello");
                cello.setItemMeta(ce);
                ShapedRecipe cel = new ShapedRecipe (new ItemStack(cello));
                cel.shape("*#*","*#*","o#o").setIngredient('*', Material.STICK).setIngredient('#', Material.STRING).setIngredient('o', Material.LOG);
                Bukkit.getServer().addRecipe(cel);
                       
                ItemStack stick = new ItemStack(Material.STICK, 1);
                ItemMeta st = drum.getItemMeta();
                st.setDisplayName (ChatColor.GOLD + "Sticks");
                stick.setItemMeta(st);
                ShapedRecipe sti = new ShapedRecipe (new ItemStack(lute));
                sti.shape("*  ","* *"," * ").setIngredient('*', Material.STICK);
                Bukkit.getServer().addRecipe(sti);
                       
                ItemStack mara = new ItemStack(Material.REDSTONE_TORCH_OFF, 1);
                ItemMeta ma = mara.getItemMeta();
                ma.setDisplayName (ChatColor.GOLD + "Maracas");
                mara.setItemMeta(ma);
                ShapedRecipe maras = new ShapedRecipe (new ItemStack(blankSheet));
                maras.shape("# #","* *","* *").setIngredient('*', Material.STICK).setIngredient('#', Material.REDSTONE);
                Bukkit.getServer().addRecipe(maras);
            }
               
            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
                    final Player p = (Player) sender;
        ArrayList<Integer> noteHolder = new ArrayList<Integer>();
                       
        if(cmd.getName().equalsIgnoreCase("music")){
                               
            if(args.length == 0){
                if(p.getItemInHand().hasItemMeta()){
                    if(p.getItemInHand().getType().equals(Material.STICK) || p.getItemInHand().getType().equals(Material.PISTON_BASE) || p.getItemInHand().getType().equals(Material.REDSTONE_TORCH_ON) || p.getItemInHand().getType().equals(Material.BOW) || p.getItemInHand().getType().equals(Material.NOTE_BLOCK)){
                        if(p.getItemInHand().getItemMeta().getDisplayName().contains("Lute") || p.getItemInHand().getItemMeta().getDisplayName().contains("Cello") || p.getItemInHand().getItemMeta().getDisplayName().contains("Drum") || p.getItemInHand().getItemMeta().getDisplayName().contains("Maracas") || p.getItemInHand().getItemMeta().getDisplayName().contains("Stick")){
                            p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Perform <title> <tempo#> <measure>" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Play selected music.");
                            return true;
                        }
                        detail(p.getPlayer());
                        return true;
                    }
                    else{
                        detail(p.getPlayer());
                        return true;
                    }
                                                                       
                }
                else{
                    detail(p.getPlayer());
                    return true;
                }
            }
                                                       
            else if (args.length > 0) {
                if(args[0].equalsIgnoreCase("Name") || args[0].equalsIgnoreCase("Add") || args[0].equalsIgnoreCase("Remove") || args[0].equalsIgnoreCase("Listen") || args[0].equalsIgnoreCase("Display")){
                    if(p.getItemInHand() != (null)){
                        if(p.getItemInHand().hasItemMeta()){
                            if(p.getItemInHand().getType().equals(Material.MAP)){
                                                                               
                                if(args[0].equalsIgnoreCase("Name")){
                                    if(args.length == 2){
                                        if(p.getItemInHand().getItemMeta().getDisplayName().contains("Blank Music Sheet")){
                                            ItemStack result = p.getItemInHand();
                                            ItemMeta meta = result.getItemMeta();
                                            p.sendMessage(ChatColor.WHITE + "Your piece has been named " + ChatColor.GOLD + "" + args[1]);
                                            meta.setDisplayName(ChatColor.GOLD + (args[1]));
                                            List<String> l = new ArrayList<String>();
                                            String strI = "" + b;
                                            l.addAll(meta.getLore());
                                            l.add(ChatColor.DARK_GRAY + "Number: " + strI);
                                            b++;
                                            meta.setLore(l);
                                            result.setItemMeta(meta);
                                            List<String> musicLore = meta.getLore();
                                            for(Iterator<?> i = musicLore.iterator(); i.hasNext();) {
                                                String ro = (String) i.next();
                                                if (ro.startsWith("Number:")) {
                                                    String number = ro.substring("Number: ".length()).trim(); // remove "Damage:" from the begining of the string and trim surrounding spaces
                                                    number = ChatColor.stripColor(number);
                                                    int c = Integer.parseInt(number);
                                                    Music.put(c,noteHolder);
                                                    Music2.put(c,noteHolder);
                                                    return true;
                                                }
                                            }
                                        }
                                        else{
                                            ItemStack result = p.getItemInHand();
                                            ItemMeta meta = result.getItemMeta();
                                            p.sendMessage(ChatColor.WHITE + "Your piece has been named " + ChatColor.GOLD + "" + args[1]);
                                            meta.setDisplayName(ChatColor.GOLD + (args[1]));
                                            result.setItemMeta(meta);
                                        }
                                    }
                                    else{
                                        p.sendMessage(ChatColor.WHITE + "Please write the name of your piece after 'Name'.");
                                        return true;
                                    }
                                }
                                else if(!p.getItemInHand().getItemMeta().getDisplayName().contains("Blank Music Sheet")){
                                    List<String> lore = p.getItemInHand().getItemMeta().getLore();
                                    for (String line : lore) {
                                            if (line.contains("Sheet consists of horizontal lines.")){
                                                                                               
                                                    if(args[0].equalsIgnoreCase("Add")){
                                                            if(args.length > 1){
                                                                    int measureN = Integer.parseInt(args[1]);
                                                                    if(0 < measureN && measureN < 100){
                                                                            int beatN = Integer.parseInt(args[2]);
                                                                            if(beatN > 0 && beatN < 8){
                                                                                    int noteN = Integer.parseInt(args[3]);
                                                                                    if(noteN > 0 && noteN < 25){
                                                                                            p.sendMessage(ChatColor.GRAY + "Successfully added note " + ChatColor.GOLD + "" + args[3] + ChatColor.GRAY + ", at Measure " + ChatColor.GOLD + "" + args[1] + ChatColor.GRAY + ", beat " + ChatColor.GOLD + "" + args[2]);
                                                                                            int input = (measureN)*4+(beatN)-4;
                                                                                            ItemStack result = p.getItemInHand();
                                                                                            ItemMeta meta = result.getItemMeta();
                                                                                            List<String> musicLore = meta.getLore();
                                                                                            for(Iterator<?> i = musicLore.iterator(); i.hasNext();) {
                                                                                                    String ro = (String) i.next();
                                                                                                    if (ro.startsWith("Number:")) {
                                                                                                            String number = ro.substring("Number: ".length()).trim();
                                                                                                            number = ChatColor.stripColor(number);
                                                                                                            int c = Integer.parseInt(number);
                                                                                                            if(args[4].equals("1") || args[4].equals(null)){
                                                                                                                    Music.get(c).add(input, noteN);
                                                                                                                    return true;
                                    }
                                else if(args[4].equals("2")){
                                    Music2.get(c).add(input, noteN);
                                    return true;
                                }
                                else{
                                    Music.get(c).add(input, noteN);
                                    return true;
                                }
                            }
                        }
                    }
                    else{
                        p.sendMessage(ChatColor.GRAY + "There is no such note. Notes range from 1 - 25. To learn more, type: /Music [help] [notes].");
                    }
                }
                else{
                    p.sendMessage(ChatColor.GRAY + "You have entered an invalid beat number.  There are 8 beats within a measure, as odds equal quarter notes and even beats are in between.");
                }
            }
            else{
                p.sendMessage(ChatColor.GRAY + "You have entered an invalid measure. A song starts on measure one and your music sheet may only consist up to 100 measures.");
            }
        }
    }
                                                                                                                               
                                                                                                                                else if(args[0].equalsIgnoreCase("Remove")){
                                                                                                                                    if(args.length > 1){
                                                                                                                                        int measureN = Integer.parseInt(args[1]);
                                                                                                                                        if(0 < measureN && measureN < 100){
                                                                                                                                            int beatN = Integer.parseInt(args[2]);
                                                                                                                                            if(beatN > 0 && beatN < 25){
                                                                                                                                                p.sendMessage(ChatColor.GRAY + "Removed beat " + ChatColor.GOLD + "" + args[1] + ChatColor.GRAY + " in measure " + args[0] + " from layer " + ChatColor.GOLD + args[2]);
                                                                                                                                                int input = (measureN)*4+(beatN)-4;
                                                                                                                                                ItemStack result = p.getItemInHand();
                                                                                                                                                ItemMeta meta = result.getItemMeta();
                                                                                                                                                List<String> musicLore = meta.getLore();
                                                                                                                                                for(Iterator<?> i = musicLore.iterator(); i.hasNext();) {
                                                                                                                                                    String ro = (String) i.next();
                                                                                                                                                    if (ro.startsWith("Number:")) {
                                                                                                                                                        String number = ro.substring("Number: ".length()).trim(); // remove "Damage:" from the begining of the string and trim surrounding spaces
                                                                                                                                                        number = ChatColor.stripColor(number);
                                                                                                                                                        int c = Integer.parseInt(number);
                                                                                                                                                        if(args[3].equals("1") || args[3].equals(null)){
                                                                                                                                                            Music.get(c).remove(input);
                                                                                                                                                            return true;
                                                                                                                                                        }
                                                                                                                                                        else if(args[3].equals("2")){
                                                                                                                                                            Music2.get(c).remove(input);
                                                                                                                                                            return true;
                                                                                                                                                        }
                                                                                                                                                        else{
                                                                                                                                                            p.sendMessage(ChatColor.GRAY + "What are you trying to do? You can only delete notes from layers 1, and 2.");
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            else{
                                                                                                                                                p.sendMessage(ChatColor.GRAY + "You have entered an invalid beat number.  There are only 8 beats within a measure.");
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        else{
                                                                                                                                            p.sendMessage(ChatColor.GRAY + "You have entered an invalid measure. A song starts on measure one and your music sheet may only consist up to 100 measures.");
                                                                                                                                        }
                                                                                       
                                                                                                                                    }
                                                                                                                                    else{
                                                                               
                                                                                                                                    }
                                                                                                                                }
                                                               
                                                                                                                                else if(args[0].equalsIgnoreCase("Display")){
                                                                                                                                    if(args.length > 1){
                                                                                                                                        int measureN = Integer.parseInt(args[1]);
                                                                                                                                        if(0 < measureN && measureN < 100){
                                                                                                                                            int place = (measureN)*4-4;
                                                                                                                                            ItemStack result = p.getItemInHand();
                                                                                                                                            ItemMeta meta = result.getItemMeta();
                                                                                                                                            List<String> musicLore = meta.getLore();
                                                                                                                                            for(Iterator<?> i = musicLore.iterator(); i.hasNext();) {
                                                                                                                                                String ro = (String) i.next();
                                                                                                                                                if (ro.startsWith("Number:")) {
                                                                                                                                                    String number = ro.substring("Number: ".length()).trim();
                                                                                                                                                    number = ChatColor.stripColor(number);
                                                                                                                                                    int c = Integer.parseInt(number);
                                                                                                                                                    p.sendMessage(ChatColor.WHITE + "Measure " + args[1] + ": ");
                                                                                                                                                    p.sendMessage(ChatColor.DARK_GRAY + "Layer 1:    Layer 2:");
                                                                                                                                                    for(int w = place; w < measureN*4; w++){
                                                                                                                                                            int o = Music.get(c).get(w);
                                                                                                                                                            String one = "" + o;
                                                                                                                                                            int t = Music2.get(c).get(w);
                                                                                                                                                            String two = "" + t;
                                                                                                                                                            p.sendMessage(ChatColor.GRAY + "" + one + "           " + "" + two);
                                                                                                                                                            w++;
                                                                                                                                                    }
                                                                                                                                            }   return true;
                                                                                                                                        }
                                                                                                                                    }
                                                                               
                                                                                                                                }
                                                                                                                                else{
                                                                                                                p.sendMessage(ChatColor.GRAY + "");
                                                                                                                                }
}
                                                               
else if(args[0].equalsIgnoreCase("Listen")){
    if(args[1].equals(null)){
        p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Listen <measure> <instrument>" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Hear measures.");
        p.sendMessage(ChatColor.GRAY + "You may enter 0 for your measure number to listen to your entire piece.");
        p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
    int measureN = Integer.parseInt(args[1]);
    if(0 <= measureN && measureN < 100){
        if(args.length == 3){
            if(args[2].equalsIgnoreCase("Cello") || args[2].equalsIgnoreCase("Lute") || args[2].equalsIgnoreCase("Maracas") || args[2].equalsIgnoreCase("Sticks") || args[2].equalsIgnoreCase("Drum") || args[2].equals(null)){
                playMusic(0, args[2], p.getPlayer());
            }
            else{
                p.sendMessage(ChatColor.GRAY + "Not a recognized instrument. Instruments are; Lute, Maracas, Sticks, Cello, or Drum. /<Compose> <Listen> <Instrument>:");
            }
        }
    }
    p.sendMessage(ChatColor.RED + "You have entered an invalid measure number to listen to (0-100). You may enter 0 to listen to your entire piece.");
}
                                                                                               
else if(args[0].equalsIgnoreCase("Instrument")){
    if(args[1].equalsIgnoreCase("Cello") || args[1].equalsIgnoreCase("Lute") || args[1].equalsIgnoreCase("Maracas") || args[1].equalsIgnoreCase("Sticks") || args[1].equalsIgnoreCase("Drum")){
        ItemStack result = p.getItemInHand();
        ItemMeta meta = result.getItemMeta();
        List<String> musicLore = meta.getLore();
        for(Iterator<?> i = musicLore.iterator(); i.hasNext();) {
            String ro = (String) i.next();
            if (ro.startsWith("Recomended")) {
                musicLore.remove(ro);
                List<String> l = new ArrayList<String>();
                l.addAll(meta.getLore());
                l.add(ChatColor.GRAY + "Recomended Instrument: " + (args [1]));
                meta.setLore(l);
                result.setItemMeta(meta);
                return true;
            }
        }
                                                                                                               
    }
    else{
        p.sendMessage(ChatColor.WHITE + "Instrument not recognized. Please select between [Drum] [Lute] [Cello] [Sticks] [Maracas]."); 
        return true;
    }
                                                                                               
}
}
                                                                                       
                                                                                               
}
}
else{
    p.sendMessage("You must name your piece before you may edit it. To do so, type /Music [Name] <Name>.");
}
                                                                               
}
else{
    p.sendMessage(ChatColor.RED + "You must hold a music sheet to edit it. For more type: /Music [Help].");
                                                                               
}
}
else{
    p.sendMessage(ChatColor.RED + "You must hold a music sheet to edit it. For more type: /Music [Help].");
}
}
else{
    p.sendMessage(ChatColor.RED + "You must hold a music sheet to edit it. For more type: /Music [Help].");
}
}
else if(args[0].equalsIgnoreCase("test")){
    if(args.length == 2){
        switch(args[1]){
            case "1":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
                return true;
            case "2":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
                return true;
            case "3":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            case "4":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
                return true;
            case "5":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
                return true;
            case "6":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
                return true;
            case "7":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
                return true;
            case "8":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
                return true;
            case "9":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
                return true;
            case "10":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
                return true;
            case "11":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
                return true;
            case "12":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
                return true;
            case "13":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
                return true;
            case "14":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
                return true;
            case "15":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
                return true;
            case "16":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
                return true;
            case "17":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
                return true;
            case "18":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
                return true;
            case "19":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
                return true;
            case "20":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
                return true;
            case "21":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
                return true;
            case "22":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
                return true;
            case "23":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
                return true;
            case "24":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
                return true;
            case "25":
                p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
                return true;
            default:
                p.sendMessage(ChatColor.WHITE + "Please select a note from 1 - 25.");
                return true;
        }
    }
    else{
        p.sendMessage(ChatColor.WHITE + "Please select a note from 1 - 25. /Music [test] <Note>");
    }
}
else if(args[0].equalsIgnoreCase("help")){
    if(args.length == 1){
        p.sendMessage(ChatColor.BOLD + "" + ChatColor.WHITE + ("Having trouble? [compose] [help] <select>."));
        p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Crafting <item>" + ChatColor.DARK_GREEN + "] " +  ChatColor.WHITE + "- Learn crafting recipes.");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Measures" + ChatColor.DARK_GREEN + "] " +  ChatColor.WHITE + "- Learn what a measure is.");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Perform" + ChatColor.DARK_GREEN + "] " +  ChatColor.WHITE + "- Learn how to play a piece to your friends.");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Tempo" + ChatColor.DARK_GREEN + "] " +  ChatColor.WHITE + "- Learn what tempo does to your piece.");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Beats" + ChatColor.DARK_GREEN + "] " +  ChatColor.WHITE + "- Learn what beats are and why are there layers.");
        p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Notes" + ChatColor.DARK_GREEN + "] " +  ChatColor.WHITE + "- Learn which number equals to what note.");
        p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        return true;
    }
    else{
        if(args[1].equalsIgnoreCase("Crafting")){
            if(args.length == 3){
                if(args[2].equalsIgnoreCase("MusicSheet")){
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=");
                    p.sendMessage(ChatColor.WHITE + "String    Ink_Sack    String");
                    p.sendMessage(ChatColor.WHITE + "String  Book & Quill  String");
                    p.sendMessage(ChatColor.WHITE + "String    Ink_Sack    String");
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=");
                    return true;
                }
                else if(args[2].equalsIgnoreCase("Lute")){
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=");
                    p.sendMessage(ChatColor.WHITE + "Stick   String   Empty");
                    p.sendMessage(ChatColor.WHITE + "Empty  Feather   Stick");
                    p.sendMessage(ChatColor.WHITE + "Stick   String   Empty");
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=");
                    return true;
                }
                else if(args[2].equalsIgnoreCase("Cello")){
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=");
                    p.sendMessage(ChatColor.WHITE + "  Stick    String    Stick");
                    p.sendMessage(ChatColor.WHITE + "  Stick    String    Stick");
                    p.sendMessage(ChatColor.WHITE + "Oak_Wood  String   Oak_Wood");
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=");
                    return true;
                }
                else if(args[2].equalsIgnoreCase("Drum")){
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    p.sendMessage(ChatColor.WHITE + " Empty          Empty           Empty");
                    p.sendMessage(ChatColor.WHITE + " Leather       Leather        Leather");
                    p.sendMessage(ChatColor.WHITE + "Oak_Wood Wood_Pressure_Plate Oak_Wood");
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    return true;
                }
                else if(args[2].equalsIgnoreCase("Maracas")){
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    p.sendMessage(ChatColor.WHITE + "Redstone  Empty  Redstone");
                    p.sendMessage(ChatColor.WHITE + "  Stick    Empty    Stick");
                    p.sendMessage(ChatColor.WHITE + "  Stick    Empty    Stick");
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    return true;
                }      
                else if(args[2].equalsIgnoreCase("Sticks")){
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=");
                    p.sendMessage(ChatColor.WHITE + "Stick  Empty  Empty");
                    p.sendMessage(ChatColor.WHITE + "Stick  Empty  Stick");
                    p.sendMessage(ChatColor.WHITE + "Empty  Stick  Empty");
                    p.sendMessage(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=");
                    return true;
                }
                else{
                    p.sendMessage(ChatColor.WHITE + "Crafting recipes are only available for [MusicSheet] [Lute] [Cello] [Drum] [Maracas] [Sticks].");
                }
            }
            else{
                p.sendMessage(ChatColor.WHITE + "[Compose] [Help] [Crafting] <Item>:");
                p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "MusicSheet" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Crafting recipe for a Blank Music Sheet.");
                p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Lute" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Crafting recipe for a Lute.");
                p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Cello" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Crafting recipe for a Cello.");
                p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Drum" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Crafting recipe for a Drum.");
                p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Maracas" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Crafting recipe for a Maraca.");
                p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Sticks" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Crafting recipe for Sticks.");
                return true;
            }
        }
        else if(args[1].equalsIgnoreCase("Measures")){
            p.sendMessage(ChatColor.GOLD + "Measures:" +  ChatColor.GRAY + " Measures are simply a group of 4 beats. A song will consist with many measures as they are an easy way to organize music and find beats within them. For example, on measure 8, we can select the beats 1, 2, 3, or 4 in which can contain a note or not.");
            return true;
        }
        else if(args[1].equalsIgnoreCase("Beats")){
            p.sendMessage(ChatColor.GOLD + "Beats: "+ ChatColor.GRAY + "Beats can be count by fours.  As there are four beats within a measure. Though you can still add beats between these four beats by inputing 1.5 or 3.5 as to sound a note in the middle of them. If you wish to have two or more notes sound on the same beat, add a layer of 1 to add a second beat.");
            return true;
        }
        else if(args[1].equalsIgnoreCase("Perform")){
            p.sendMessage(ChatColor.GOLD + "Perform: " + ChatColor.GRAY + "To perform a piece, you need to craft 1 of the 5 instruments.  After doing so you need to obtain a music sheet that either you or someone else has created and composed music on it.  Once you have that done, type in /[compose] [perform] <name>. The name is the title of the song you wish to play that is in your inventory. This will allow you to perform the music to friends around you. If you wish to play only a measure of the piece you may type in /[compose] [perform] <name> <measure(#)>. You can stop playing during a song by typing /[compose] [perform] <name> [stop].");
            return true;
        }
        else if(args[1].equalsIgnoreCase("Tempo")){
            p.sendMessage(ChatColor.GOLD + "Tempo: " + ChatColor.GRAY + "To be added");
            return true;
        }
        else if(args[1].equalsIgnoreCase("Notes")){
            p.sendMessage(ChatColor.GOLD + "Notes: ");
            p.sendMessage(ChatColor.GOLD + "Octive 0: | " + ChatColor.WHITE + "1 - F# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "2 - G " + ChatColor.GOLD + "| " +ChatColor.WHITE + "3 - G# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "4 - A " + ChatColor.GOLD + "| " + ChatColor.WHITE + "5 - A# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "6 - B " + ChatColor.GOLD + "| " + ChatColor.WHITE + "7 - C " + ChatColor.GOLD + "| " + ChatColor.WHITE + "8 - C# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "9 - D " + ChatColor.GOLD + "| " + ChatColor.WHITE + "10 - D# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "11 - E " + ChatColor.GOLD + "| " + ChatColor.WHITE + "12 - F " + ChatColor.GOLD + "|");
            p.sendMessage(ChatColor.GOLD + "Octive 1: | " + ChatColor.WHITE + "13 - F# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "14 - G " + ChatColor.GOLD + "| " +ChatColor.WHITE + "15 - G# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "16 - A " + ChatColor.GOLD + "| " + ChatColor.WHITE + "17 - A# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "18 - B " + ChatColor.GOLD + "| " + ChatColor.WHITE + "19 - C " + ChatColor.GOLD + "| " + ChatColor.WHITE + "20 - C# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "21 - D " + ChatColor.GOLD + "| " + ChatColor.WHITE + "22 - D# " + ChatColor.GOLD + "| " + ChatColor.WHITE + "23 - E " + ChatColor.GOLD + "| " + ChatColor.WHITE + "24 - F " + ChatColor.GOLD + "|");
            p.sendMessage(ChatColor.GOLD + "Octive 2: | " + ChatColor.WHITE + "25 - F# " + ChatColor.GOLD + "|");
            return true;
        }
    }
}
else{
    detail(p.getPlayer());
    return true;
}
}
else{
    detail(p.getPlayer());
    return true;
}
}return true;
}
 
private boolean detail(Player p) {
        p.sendMessage(ChatColor.ITALIC + "" + ChatColor.BLUE + "Music Composition");
p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Name <name>" + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + "- Name the piece in your hand.");  
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Instrument <instrument>" + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + "- Select recommended instrument." );
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Test <note(#)>" + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + "- Test the sound of a note.");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Add <measure> <beat> <note> <layer>" + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + "- Add a note.");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Remove <measure> <beat> <layer>" + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + "- Remove a note.");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Display <measure>" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Display the notes within a measure.");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Listen <measure> <instrument>" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Hear measures.");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Tempo <speed(#)>" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Select recommended tempo.");
p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GOLD + "Help" + ChatColor.DARK_GREEN + "] " +  ChatColor.GRAY + "- Display the help menu.");
p.sendMessage(ChatColor.WHITE + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
return true;
                       
}
 
private void playMusic(final int n, final String st, final Player p) {
                        ItemStack result = p.getItemInHand();
ItemMeta meta = result.getItemMeta();
List<String> musicLore = meta.getLore();
for(Iterator<?> i = musicLore.iterator(); i.hasNext();) {
    String ro = (String) i.next();
    if (ro.startsWith("Number:")) {
        String number = ro.substring("Number: ".length()).trim();
        number = ChatColor.stripColor(number);
        final int c = Integer.parseInt(number);
        final int place = (n)*4-4;
        if(n == 0){
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
                        public void run(){
                                int nm = 0;
            if(nm != Music.get(c).size() + 1){
                if(nm != Music.get(c).size()){
                    int t = Music2.get(c).get(nm);
                    int s = Music.get(c).get(nm);
                    selfSound(s,t,p.getPlayer(),st);
                    nm++;
                }
                else{
                    nm++;
                }
            }
        }
    }, 12L);
}
                       
                                       
                                               
else{
                                                Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
                                                        public void run(){
                                                                int nm = place;
if(nm != n*4+1){
    p.sendMessage(ChatColor.MAGIC + "Listening to measure " + n);
    int t = Music2.get(c).get(nm);
    int s = Music.get(c).get(nm);
    selfSound(s,t,p.getPlayer(),st);
    nm++;
}
else{
    nm++;
}
                                                   
}
}, 12L);
                                               
}
}
}
}
private void selfSound(int s, int t, Player p, String stri) {
                if(stri.equalsIgnoreCase("Lute") || stri.equals(null)){
                    switch(s){
                        case 1:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
                            break;
                        case 2:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
                            break;
                        case 3:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
                            break;
                        case 4:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
                            break;
                        case 5:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
                            break;
                        case 6:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
                            break;
                        case 7:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
                            break;
                        case 8:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
                            break;
                        case 9:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
                            break;
                        case 10:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
                            break;
                        case 11:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
                            break;
                        case 12:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
                            break;
                        case 13:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
                            break;
                        case 14:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
                            break;
                        case 15:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
                            break;
                        case 16:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
                            break;
                        case 17:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
                            break;
                        case 18:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
                            break;
                        case 19:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
                            break;
                        case 20:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
                            break;
                        case 21:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
                            break;
                        case 22:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
                            break;
                        case 23:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
                            break;
                        case 24:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
                            break;
                        case 25:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
                            break;
                        default:
                            break;
                    }
                    switch(t){
                        case 1:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
                            break;
                        case 2:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
                            break;
                        case 3:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
                            break;
                        case 4:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
                            break;
                        case 5:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
                            break;
                        case 6:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
                            break;
                        case 7:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
                            break;
                        case 8:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
                            break;
                        case 9:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
                            break;
                        case 10:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
                            break;
                        case 11:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
                            break;
                        case 12:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
                            break;
                        case 13:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
                            break;
                        case 14:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
                            break;
                        case 15:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
                            break;
                        case 16:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
                            break;
                        case 17:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
                            break;
                        case 18:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
                            break;
                        case 19:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
                            break;
                        case 20:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
                            break;
                        case 21:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
                            break;
                        case 22:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
                            break;
                        case 23:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
                            break;
                        case 24:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
                            break;
                        case 25:
                            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
                            break;
                        default:
                            break;
                    }
                }
else if(stri == "Cello"){
    switch(s){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 2.0F);
            break;
        default:
            break;
    }
    switch(t){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_BASS_GUITAR, 3.0F, 2.0F);
            break;
        default:
            break;
    }
}
if(stri.equalsIgnoreCase("Sticks")){
    switch(s){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
            break;
        default:
            break;
    }
    switch(t){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
            break;
        default:
            break;
    }
}
if(stri.equalsIgnoreCase("Drum")){
    switch(s){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
            break;
        default:
            break;
    }
    switch(t){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
            break;
        default:
            break;
    }
}
if(stri.equalsIgnoreCase("Maracas")){
    switch(s){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
            break;
        default:
            break;
    }
    switch(t){
        case 1:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
            break;
        case 2:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.525F);
            break;
        case 3:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.55F);
            break;
        case 4:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.6F);
            break;
        case 5:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.65F);
            break;
        case 6:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.675F);
            break;
        case 7:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.7F);
            break;
        case 8:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.75F);
            break;
        case 9:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.8F);
            break;
        case 10:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.85F);
            break;
        case 11:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.9F);
            break;
        case 12:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.95F);
            break;
        case 13:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.0F);
            break;
        case 14:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.05F);
            break;
        case 15:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.1F);
            break;
        case 16:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.2F);
            break;
        case 17:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.25F);
            break;
        case 18:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.35F);
            break;
        case 19:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.4F);
            break;
        case 20:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.45F);
            break;
        case 21:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.6F);
            break;
        case 22:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.7F);
            break;
        case 23:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.75F);
            break;
        case 24:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 1.9F);
            break;
        case 25:
            p.playSound(p.getLocation(), Sound.NOTE_PIANO, 3.0F, 2.0F);
            break;
        default:
            break;
    }
}
                                       
}
}