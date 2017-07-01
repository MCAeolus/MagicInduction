package me.mcaeolus.magicinduction.wand.foci;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class IceFoci extends Foci {

    public IceFoci(String NAME, FociType TYPE) {
        super("Ice Focus", FociType.ICE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e) {

    }
}
