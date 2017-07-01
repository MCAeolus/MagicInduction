package me.mcaeolus.magicinduction.wand;

import me.mcaeolus.magicinduction.MagicInduction;
import org.bukkit.event.Listener;

import java.util.ArrayList;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class WandListener implements Listener {

    private ArrayList<WandUser> USERS = new ArrayList<>();

    public WandListener(){
        MagicInduction.getLocalServer().getPluginManager().registerEvents(this, MagicInduction.getInstance());
    }
}
