package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.util.ItemBuilder;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by mcaeo on 7/2/2017.
 */
public class FociPouch implements Listener {

    private boolean isOpen;
    private boolean newEquip = false;
    private WandUser master;
    private ArrayList<FociType> foci = new ArrayList<>();

    public FociPouch(WandUser user){
        this.master = user;
        Bukkit.getPluginManager().registerEvents(this, MagicInduction.getInstance());

        if(MagicInduction.getUserData().get(master.getPlayer().getUniqueId() + ".pouch") != null){
            for(String f : MagicInduction.getUserData().getConfiguration().getStringList(master.getPlayer().getUniqueId() + ".pouch"))
                foci.add(FociType.valueOf(f));
        }else
            foci.add(FociType.NO_FOCUS);
    }

    public void open(){
        if(isOpen)return;
        isOpen = true;
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.RED + "Focus Pouch");
        for(FociType x : foci) {
            ItemStack iX = new ItemBuilder(x.FOCUS.getMaterial()).name(x.FOCUS.getName() + ChatColor.GRAY + " - " + (master.getCurrentFocus().equals(x) ? ChatColor.GREEN + "ACTIVE" : ChatColor.YELLOW + "INACTIVE")).make();
            inv.addItem(iX);
        }
        master.getPlayer().openInventory(inv);
    }

    public boolean isInventoryOpen(){
        return isOpen;
    }

    public boolean attemptAddFocus(FociType x){
        if(foci.contains(x))return false;
        foci.add(x);
        return true;
    }

    public void saveAndExit(){
        List<String> foci_list = new ArrayList<>();
        for(FociType x : foci) foci_list.add(x.toString());
        MagicInduction.getUserData().set(foci_list, master.getPlayer().getUniqueId() + ".pouch");
    }



    @EventHandler
    public void click(InventoryClickEvent e){
        if(e.getWhoClicked().getUniqueId().equals(master.getPlayer().getUniqueId()) && isOpen){

            if(e.getClick() == ClickType.SHIFT_RIGHT || e.getClick() == ClickType.SHIFT_LEFT){
                e.setCancelled(true);
                return;
            }

            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if(e.getClickedInventory() != null && !(e.getClickedInventory().getType() == InventoryType.PLAYER)) {

                if(e.getSlot() < foci.size()) {
                    FociType f = foci.get(e.getSlot());
                    master.setFocus(f);
                    newEquip = true;
                    master.getPlayer().closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent e){
        if(e.getPlayer().getUniqueId().equals(master.getPlayer().getUniqueId()) && isOpen) {
            isOpen = false;

            if (newEquip) {
                master.getPlayer().sendMessage(ChatColor.YELLOW + "You have equipped the " + master.getCurrentFocus().FOCUS.getName()+ ChatColor.YELLOW + ".");
                newEquip = false;

            }
        }
    }
}
