package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class IceFoci extends Foci {

    public IceFoci() {
        super("Ice Focus", FociType.ICE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {

    }
}
