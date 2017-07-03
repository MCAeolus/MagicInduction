package me.mcaeolus.magicinduction.recipe;

import com.sun.xml.internal.ws.api.server.AbstractInstanceResolver;
import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.wand.WandListener;
import me.mcaeolus.magicinduction.wand.WandUser;
import me.mcaeolus.magicinduction.wand.foci.Foci;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

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
        if(Foci.isFocus(e.getRecipe().getResult()) && WU.getMana() >= 250){
            WU.attemptUseMana(250);
            p.sendMessage(ChatColor.GOLD + "You have created a focus! Shift-right click the focus to add it to your focus pouch.");
        }
    }

    @EventHandler
    public void attemptCraft(PrepareItemCraftEvent e){
        if(e.getRecipe() != null && Foci.isFocus(e.getRecipe().getResult())){
            WandUser WU = WandListener.getUser(e.getViewers().get(0).getUniqueId());
            if(WU.getMana() < 250){
                e.getInventory().setResult(new ItemStack(Material.AIR));
                WU.getPlayer().sendMessage(ChatColor.RED + "You don't have enough mana to craft this!");
            }
        }
    }
}
