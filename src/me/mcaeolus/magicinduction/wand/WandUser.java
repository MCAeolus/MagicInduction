package me.mcaeolus.magicinduction.wand;


import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.multiblock.MultiManager;
import me.mcaeolus.magicinduction.util.HexStringUtil;
import me.mcaeolus.magicinduction.util.ItemBuilder;
import me.mcaeolus.magicinduction.wand.foci.Foci;
import me.mcaeolus.magicinduction.wand.foci.FociPouch;
import me.mcaeolus.magicinduction.wand.foci.FociType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class WandUser extends BukkitRunnable {

    private Player player;
    private boolean usingWand = false;
    private int mana;
    private int max_mana;
    private FociType currentFocus = FociType.NO_FOCUS;
    private List<ItemStack> saved_hotbar;
    private FociPouch pouch;

    private int former_slot;
    private ItemStack wand;

    private long last_switch;

    private static ItemBuilder FULL_MANA_PORTION = new ItemBuilder(Material.POTION).data(8193);
    private static ItemBuilder PARTIAL_MANA_PORTION = new ItemBuilder(Material.LINGERING_POTION).data(8193);
    private static ItemBuilder EMPTY_MANA_PORTION = new ItemBuilder(Material.GLASS_BOTTLE);

    public WandUser(Player p){
        this.player = p;
        if(MagicInduction.getUserData().get(player.getUniqueId().toString() + ".mana_current") != null) {
            mana = (int)MagicInduction.getUserData().get(player.getUniqueId().toString() + ".mana_current");
            max_mana = (int)MagicInduction.getUserData().get(player.getUniqueId().toString() + ".mana_max");
        }else {
            max_mana = 250;
            mana = 0;
            MagicInduction.getUserData().set("", player.getUniqueId().toString());
        }
        this.pouch = new FociPouch(this);
    }

    public void saveAndExit(){
        this.cancel();
        pouch.saveAndExit();
        MagicInduction.getUserData().set(mana, player.getUniqueId().toString() + ".mana_current");
        MagicInduction.getUserData().set(max_mana, player.getUniqueId().toString() + ".mana_max");
        if(usingWand)
            usingWand(false);
    }

    public void setFocus(FociType foci){
        this.currentFocus = foci;
    }

    public FociType getCurrentFocus(){
        return this.currentFocus;
    }

    public void usingWand(boolean isUsing){
        if(System.currentTimeMillis() - last_switch < 500){
            player.sendMessage(ChatColor.RED + "You must wait half a second before switching between wand and normal play mode again!");
            player.playSound(player.getEyeLocation(), Sound.ENTITY_IRONGOLEM_DEATH, 5, 1);
        }
        else {
            last_switch = System.currentTimeMillis();
            usingWand = isUsing;
            if (usingWand) {
                wand = player.getInventory().getItemInMainHand();
                saved_hotbar = new ArrayList<>();
                for (int i = 0; i <= 8; i++)
                    saved_hotbar.add(player.getInventory().getItem(i));
                wand.setType(Material.BLAZE_ROD);
                player.getInventory().setItem(0, wand);
                former_slot = player.getInventory().getHeldItemSlot();
                player.getInventory().setHeldItemSlot(0);
                updateManaBar();
            } else {
                for (int i = 0; i <= 8; i++)
                    player.getInventory().setItem(i, saved_hotbar.get(i));
                ItemMeta wMx = player.getInventory().getItem(former_slot).getItemMeta();
                wMx.setDisplayName(ChatColor.GOLD + "WAND" + ChatColor.GREEN + " - " + ChatColor.GRAY + "Inactive");
                player.getInventory().getItem(former_slot).setItemMeta(wMx);
                player.getInventory().getItem(former_slot).setType(Material.MAGMA_CREAM);
                player.getInventory().setHeldItemSlot(former_slot);
            }
        }
    }

    public boolean attemptUseMana(int use){
        if(mana - use < 0)return false;
        else {
            mana -= use;
            return true;
        }
    }

    public Player getPlayer(){
        return this.player;
    }

    public int getMana(){
        return mana;
    }

    public void wandInteract(PlayerInteractEvent e){
        if(isWand(player.getInventory().getItemInMainHand())) {
            if (player.isSneaking()){
                if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                    if(isEmpowered(player.getInventory().getItemInMainHand()))
                        usingWand(!usingWand);
                    else player.sendMessage(ChatColor.RED + "This wand has not been empowered!");
                }else if((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
                    pouch.open();
            }
            else if (usingWand) currentFocus.FOCUS.interceptInteractEvent(e, this);
        }else{
            if(Foci.isFocus(player.getInventory().getItemInMainHand())){
                if (player.isSneaking() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                    ItemStack iX = player.getInventory().getItemInMainHand();
                    FociType f = FociType.valueOf(ChatColor.stripColor(iX.getItemMeta().getDisplayName()).replace(" ", "_").toUpperCase());
                    if(pouch.attemptAddFocus(f)){
                        player.sendMessage(ChatColor.GREEN + "You have added the "+f.FOCUS.getName() + " to your focus pouch.");
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                    }
                }
            }else {
                Block b = e.getClickedBlock();
                if(b != null ){
                    for (Entity x : b.getWorld().getNearbyEntities(b.getLocation().add(0, 1, 0), 1, 1, 1)) {
                        if (x instanceof Item) {
                            ItemStack iX = ((Item) x).getItemStack();
                            if (isWand(iX) && !isEmpowered(iX)
                                    && MagicInduction.getInstance().getMultiManager().attemptBuild(e, this)) {
                                World wX = x.getWorld();
                                x.remove();
                                ItemMeta iMx = iX.getItemMeta();
                                ArrayList<String> lores = new ArrayList<>();
                                lores.add(HexStringUtil.encodeString("{\"isWand\":\"true\",\"isActivated\":\"true\"}"));
                                iMx.setLore(lores);
                                iMx.setDisplayName(ChatColor.GOLD + "WAND" + ChatColor.GREEN + " - " + ChatColor.GRAY + "Inactive");
                                iX.setItemMeta(iMx);
                                iX.setType(Material.MAGMA_CREAM);
                                wX.dropItem(b.getLocation().add(0, 2, 0), iX);

                                player.sendMessage(ChatColor.GOLD + "You have successfully empowered your wand! Shift-right click to activate and de-activate your wand.");
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isWand(ItemStack x){
        if(x.hasItemMeta()) {
            List<String> lore = x.getItemMeta().getLore();
            if (lore != null && HexStringUtil.hasHiddenString(lore.get(0))) {

                String jstring = HexStringUtil.extractHiddenString(lore.get(0));

                JSONParser parser = new JSONParser();
                JSONObject jOb = null;
                try {
                    jOb = (JSONObject) parser.parse(jstring);
                } catch (ParseException ex) {
                    MagicInduction.getInstance().getLogger().warning("Internal json parsing error! Plugin json was improperly created.");
                }
                return (jOb != null && jOb.containsKey("isWand"));
            }
        }
        return false;
    }

    public boolean isEmpowered(ItemStack x){
        if(x.hasItemMeta()) {
            List<String> lore = x.getItemMeta().getLore();
            if (isWand(x)) {
                if (lore != null && HexStringUtil.hasHiddenString(lore.get(0))) {

                    String jstring = HexStringUtil.extractHiddenString(lore.get(0));

                    JSONParser parser = new JSONParser();
                    JSONObject jOb = null;
                    try {
                        jOb = (JSONObject) parser.parse(jstring);
                    } catch (ParseException ex) {
                        MagicInduction.getInstance().getLogger().warning("Internal json parsing error! Plugin json was improperly created.");
                    }

                    return (jOb != null && jOb.getOrDefault("isActivated", "false").equals("true"));
                }
            }
        }
        return false;
    }

    public void itemHeld(PlayerItemHeldEvent e){
        if(usingWand)if(e.getNewSlot() != 0)player.getInventory().setHeldItemSlot(0);
    }

    public void updateManaBar(){
        double percent_full = (double)mana/max_mana;
        for(int i = 1; i<= 8;i++){
            if((double)i/8 <= percent_full) player.getInventory().setItem(i, FULL_MANA_PORTION.name(ChatColor.GREEN + "" + mana + ChatColor.YELLOW + "/" + ChatColor.GREEN + "" + max_mana).make());
            else if((((i*2)-1)/(double)16) <= percent_full) player.getInventory().setItem(i, PARTIAL_MANA_PORTION.name(ChatColor.GREEN + "" + mana + ChatColor.YELLOW + "/" + ChatColor.GREEN + "" + max_mana).make());
            else player.getInventory().setItem(i, EMPTY_MANA_PORTION.name(ChatColor.GREEN + "" + mana + ChatColor.YELLOW + "/" + ChatColor.GREEN + "" + max_mana).make());
        }
        ItemMeta xWm = wand.getItemMeta();
        xWm.setDisplayName(ChatColor.BLUE + "Mana: " + ChatColor.YELLOW + mana + ChatColor.LIGHT_PURPLE + "/" + ChatColor.YELLOW + max_mana);
        wand.setItemMeta(xWm);
        player.getInventory().setItemInMainHand(wand);
    }

    @Override
    public void run() { //mana tick
        int mana_add = (max_mana/32);
        if(mana < max_mana) mana = (mana + mana_add <= max_mana) ? mana_add + mana : max_mana;
        if (usingWand)
            updateManaBar();
    }
}
