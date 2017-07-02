package me.mcaeolus.magicinduction.multiblock;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class Cuboid {
    private Location L1;
    private Location L2;

    public Cuboid(Block c, Block c2){
        L1 = c.getLocation();
        L2 = c.getLocation();
    }
}
