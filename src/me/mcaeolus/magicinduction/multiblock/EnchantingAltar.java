package me.mcaeolus.magicinduction.multiblock;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class EnchantingAltar extends Multiblock {
    private static List<Cuboid> altars = new ArrayList<>();
    private static HashMap<Character, Material> chars = new HashMap<>();
    static{
        chars.put('w',Material.SMOOTH_BRICK);
        chars.put(' ',Material.AIR);
        chars.put('i',Material.IRON_BLOCK);
        chars.put('s',Material.COBBLE_WALL);

    }
    public EnchantingAltar() {
        super(chars, Material.IRON_BLOCK, new Character[][]{{'w',' ',' ',' ','w'},
                                                            {' ',' ',' ',' ',' '},
                                                            {' ',' ','i',' ',' '},
                                                            {' ',' ',' ',' ',' '},
                                                            {'w',' ',' ',' ','w'}}
                                    , new Character[][]{{'s',' ',' ',' ','s'},
                                                        {' ',' ',' ',' ',' '},
                                                        {' ',' ',' ',' ',' '},
                                                        {' ',' ',' ',' ',' '},
                                                        {'s',' ',' ',' ','s'}});
    }

    @Override
    public void build(Block center){
        super.build(center);
    }
}