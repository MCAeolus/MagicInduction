package me.mcaeolus.magicinduction.multiblock;

import me.mcaeolus.magicinduction.MagicInduction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class Cuboid {
    private Location L1;
    private Location L2;
    private UUID player;
    private List<Block> blocks;
    private Multiblock master;
    private Block center;

    public Cuboid(Block b, Location c, Location c2, UUID player, Multiblock master){
        L1 = c;
        L2 = c2;
        center = b;
        if(player != null)
            this.player = player;
        this.blocks = readRegionBlocks();
        this.master = master;
    }

    private List<Block> readRegionBlocks(){
        ArrayList<Block> blocks = new ArrayList<>();
        int     x_rel = L2.getBlockX() - L1.getBlockX(),
                y_rel = L2.getBlockY() - L1.getBlockY(),
                z_rel = L2.getBlockZ() - L1.getBlockZ();
        for(int x = 0; x < x_rel; x++){
            for(int y = 0; y < y_rel; y++){
                for(int z = 0; z < z_rel; z++){
                    blocks.add(L1.getBlock().getRelative(x, y, z));
                }
            }
        }
        return blocks;
    }

    public Block getCenter(){
        return center;
    }

    public Multiblock getMaster(){
        return master;
    }

    public boolean isRegionValid(){
        Character[][][] layers = master.getLayers();
        for(int y = 0; y < layers.length; y++){
            for(int x = 0; x < layers[y].length; x++){
                for(int z = 0; z < layers[y][x].length; z++) {
                    Material MOD_MAT = master.getCharacterMap().get(layers[y][x][z]);
                    if (MOD_MAT != Material.AIR) {
                        Block MOD = L1.getWorld().getBlockAt(L1.add(x, y, z));
                        if(MOD_MAT != MOD.getType())return false;
                    }

                }
            }
            return true;
        }

        return blocks.equals(readRegionBlocks());
    }

    public UUID getOwner(){
        return this.player;
    }

    public void remove(){
        Character[][][] layers = master.getLayers();
        for(int y = 0; y < layers.length; y++){
            for(int x = 0; x < layers[y].length; x++){
                for(int z = 0; z < layers[y][x].length; z++) {
                    Material MOD_MAT = master.getCharacterMap().get(layers[y][x][z]);
                    Block MOD = L1.getWorld().getBlockAt(L1.add(x, y, z));
                    if(MOD.getType() == MOD_MAT)MOD.setType(Material.AIR);
                }
            }
        }
    }
}
