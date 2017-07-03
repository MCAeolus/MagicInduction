package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.util.ItemBuilder;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class FireFoci extends Foci {

    public FireFoci() {
        super(ChatColor.RED+"Fire Focus", FociType.FIRE_FOCUS, Material.FIRE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {

    }
}
