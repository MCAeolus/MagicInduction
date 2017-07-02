package me.mcaeolus.magicinduction.multiblock;

import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.List;


public class Multiblock implements Listener {

    private Character[][][] layers;
    private HashMap<Character, Material> charCheck;
    private Material master;


    public Multiblock(HashMap<Character, Material> charConvert, Material master, Character[][] ... layers){
        this.layers = layers;
        this.charCheck = charConvert;
        this.master = master;
    }

    public boolean interact(PlayerInteractEvent e, WandUser u){
       Block ms = e.getClickedBlock();
       if(ms.getType() == master){
           build(ms);
           return true;
       }
       return false;
    }

    public void build(Block center){
        Block corner = center.getRelative(-layers[0].length/2, 0, -layers[0][0].length/2);
        for(int y = 0; y < layers.length; y++){
            for(int x = 0; x < layers[y].length; x++){
                for(int z = 0; z < layers[y][x].length; z++){
                    corner.getRelative(x, y, z).setType(charToMaterial(layers[y][x][z]));
                }
            }
        }
    }

    private Material charToMaterial(Character x){
        return charCheck.get(x);
    }

}
