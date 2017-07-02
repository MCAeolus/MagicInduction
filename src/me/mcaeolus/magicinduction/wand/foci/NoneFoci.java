package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.multiblock.MultiManager;
import me.mcaeolus.magicinduction.multiblock.Multiblock;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.ChatColor;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class NoneFoci extends Foci {

    public NoneFoci() {
        super("No Focus", FociType.NONE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && u.getMana() >= 250) {
            if (MagicInduction.getInstance().getMultiManager().attemptBuild(e, u)) u.attemptUseMana(250);
        }else{
            u.getPlayer().sendMessage(ChatColor.RED + "You do not have enough mana to perform this action! (Minimum mana requirement: 250)");
        }
        u.updateManaBar();
    }
}
