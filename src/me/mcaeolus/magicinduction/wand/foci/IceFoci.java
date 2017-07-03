package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.wand.WandUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class IceFoci extends Foci {

    public IceFoci() {
        super(ChatColor.BLUE + "Ice Focus", FociType.ICE_FOCUS, Material.ICE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {

    }
}
