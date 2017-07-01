package me.mcaeolus.magicinduction.wand.foci;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class NoneFoci extends Foci {

    public NoneFoci() {
        super("No Focus", FociType.NONE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e) {

    }
}
