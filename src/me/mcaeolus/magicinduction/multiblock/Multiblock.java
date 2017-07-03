package me.mcaeolus.magicinduction.multiblock;

import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Multiblock implements Listener {

    private Character[][][] layers;
    private HashMap<Character, Material> charCheck;
    private Material master;
    private String name;
    private static List<Material> BLACKLIST = new ArrayList<>();
    static{
        BLACKLIST.add(Material.LONG_GRASS);
        BLACKLIST.add(Material.YELLOW_FLOWER);
        BLACKLIST.add(Material.DOUBLE_PLANT);
        BLACKLIST.add(Material.AIR);
    }


    public Multiblock(String name, HashMap<Character, Material> charConvert, Material master, Character[][] ... layers){
        this.layers = layers;
        this.charCheck = charConvert;
        this.master = master;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Character[][][] getLayers(){
        return layers;
    }

    public HashMap<Character, Material> getCharacterMap(){
        return charCheck;
    }

    public boolean interact(PlayerInteractEvent e, WandUser u){
       Block ms = e.getClickedBlock();
       if(ms.getType() == master){
           if(build(ms, u) == null){
               u.getPlayer().sendMessage(ChatColor.RED + "Make sure that you have enough space for the structure you are trying to spawn! Something is stopping it from being created.");
               u.getPlayer().playSound(u.getPlayer().getEyeLocation(), Sound.BLOCK_ANVIL_DESTROY, 5, 1);
               return false;
           }
           return true;
       }
       return false;
    }

    public Location[] build(Block center, WandUser user){
        Block corner = center.getRelative(-layers[0].length/2, 0, -layers[0][0].length/2);
        for(int y = 0; y < layers.length; y++){
            for(int x = 0; x < layers[y].length; x++){
                for(int z = 0; z < layers[y][x].length; z++){
                    Block MOD = corner.getRelative(x, y, z);
                    if(!BLACKLIST.contains(MOD.getType()) && !MOD.getLocation().equals(center.getLocation()))return null;
                }
            }
        }
        for(int y = 0; y < layers.length; y++){
            for(int x = 0; x < layers[y].length; x++){
                for(int z = 0; z < layers[y][x].length; z++){
                    Block MOD = corner.getRelative(x, y, z);

                    corner.getRelative(x, y, z).setType(charToMaterial(layers[y][x][z]));
                }
            }
        }
        return new Location[]{corner.getLocation(), corner.getRelative(layers[0].length, layers.length, layers[0][0].length).getLocation()};

    }

    private Material charToMaterial(Character x){
        return charCheck.get(x);
    }

}
