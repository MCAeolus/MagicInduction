package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class EnderFoci extends Foci {

    public EnderFoci() {
        super("Ender Focus", FociType.ENDER);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {

    }
}
