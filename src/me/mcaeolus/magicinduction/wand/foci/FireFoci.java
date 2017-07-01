package me.mcaeolus.magicinduction.wand.foci;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class FireFoci extends Foci {

    public FireFoci() {
        super("Fire Focus", FociType.FIRE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e) {

    }
}
