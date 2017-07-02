package me.mcaeolus.magicinduction.multiblock;

import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class MultiManager {

    private List<Multiblock> structures = new ArrayList<>();

    public MultiManager(){
        structures.add(new EnchantingAltar());
    }

    public boolean attemptBuild(PlayerInteractEvent e, WandUser u){
        for(Multiblock x : structures)if(x.interact(e, u))return true;
        return false;
    }
}
