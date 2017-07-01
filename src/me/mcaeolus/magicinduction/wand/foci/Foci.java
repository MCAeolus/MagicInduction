package me.mcaeolus.magicinduction.wand.foci;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class Foci {
    public enum FociType{NONE, FIRE, ICE, ENDER}

    private FociType TYPE;
    private String NAME;

    public Foci(String NAME, FociType TYPE){
        this.NAME = NAME;
        this.TYPE = TYPE;
    }

    public void interceptInteractEvent(PlayerInteractEvent e){
        //Override in subclasses.
    }
}
