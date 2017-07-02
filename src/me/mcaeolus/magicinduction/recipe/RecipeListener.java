package me.mcaeolus.magicinduction.recipe;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.wand.WandListener;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

/**
 * Created by mcaeo on 7/2/2017.
 */
public class RecipeListener implements Listener {

    public RecipeListener(){
        MagicInduction.getLocalServer().getPluginManager().registerEvents(this, MagicInduction.getInstance());
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        Player p = (Player)e.getWhoClicked();
        WandUser WU = WandListener.getUser(p.getUniqueId());
        if(WU.isWand(e.getRecipe().getResult()))
            p.sendMessage(ChatColor.GOLD + "To empower this wand, drop it on top of an iron block and then right click the block. Make sure the iron block has two blocks of space in each direction around it!");
    }
}
