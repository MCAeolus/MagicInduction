package me.mcaeolus.magicinduction.wand;


import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.util.HexStringUtil;
import me.mcaeolus.magicinduction.util.ItemBuilder;
import me.mcaeolus.magicinduction.wand.foci.Foci;
import me.mcaeolus.magicinduction.wand.foci.FociType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.List;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class WandUser extends BukkitRunnable {

    private Player player;
    private boolean usingWand = false;
    private int mana;
    private int max_mana;
    private Foci currentFocus;
    private List<ItemStack> saved_hotbar;

    private static ItemBuilder FULL_MANA_PORTION = new ItemBuilder(Material.POTION).data(8193);
    private static ItemBuilder PARTIAL_MANA_PORTION = new ItemBuilder(Material.LINGERING_POTION).data(8193);
    private static ItemBuilder EMPTY_MANA_PORTION = new ItemBuilder(Material.GLASS_BOTTLE);

    public WandUser(Player p){
        this.player = p;
        currentFocus = FociType.NONE.FOCUS;
        ConfigurationSection sect = MagicInduction.getData().getSection(player.getUniqueId().toString());
        if(sect.contains("mana_current"))mana = sect.getInt("mana_current");
        if(sect.contains("mana_max"))max_mana = sect.getInt("mana_max");
        else max_mana = 250;

        //TODO pull from file
    }

    public void saveAndExit(){
        this.cancel();
        ConfigurationSection sect = MagicInduction.getData().getSection(player.getUniqueId().toString());
        sect.set("mana_current", mana);
        sect.set("mana_max", max_mana);
        if(usingWand){
            usingWand(false);
        }

    }

    public Foci getCurrentFocus(){
        return this.currentFocus;
    }

    public void usingWand(boolean isUsing){
        usingWand = isUsing;
        if(usingWand){
            ItemStack wand = player.getInventory().getItemInMainHand();
            saved_hotbar.clear();
            for(int i = 0; i <= 8; i++)
                saved_hotbar.add(player.getInventory().getItem(i));
            player.getInventory().setItem(0, wand);
            updateManaBar();
        }else{
            for(int i = 0; i <= 8; i++)
                player.getInventory().setItem(i, saved_hotbar.get(i));
        }
    }

    public void wandChange(PlayerChangedMainHandEvent e) {
        if(isWand(player.getInventory().getItemInMainHand())) {
            if (!usingWand) usingWand(true);
        }else
            if(usingWand)usingWand(false);
    }

    public void wandInteract(PlayerInteractEvent e){
        if(usingWand) currentFocus.interceptInteractEvent(e);
    }

    public static boolean isWand(ItemStack x){
        ItemBuilder i = new ItemBuilder(x);
        List<String> lore = i.meta().getLore();
        if(lore != null && HexStringUtil.hasHiddenString(lore.get(0))) {
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
        return false;
    }

    private void updateManaBar(){
        double percent_full = (double)mana/max_mana;
        for(int i = 1; i<= 8;i++){
            if((double)i/8 <= percent_full) player.getInventory().setItem(i, FULL_MANA_PORTION.name(ChatColor.GREEN + "" + mana + ChatColor.YELLOW + "/" + ChatColor.GREEN + "" + max_mana).make());
            else if((double)(((i*2)-1)/16) <= percent_full) player.getInventory().setItem(i, PARTIAL_MANA_PORTION.name(ChatColor.GREEN + "" + mana + ChatColor.YELLOW + "/" + ChatColor.GREEN + "" + max_mana).make());
            else player.getInventory().setItem(i, EMPTY_MANA_PORTION.name(ChatColor.GREEN + "" + mana + ChatColor.YELLOW + "/" + ChatColor.GREEN + "" + max_mana).make());
        }
    }

    @Override
    public void run() { //mana tick
        int mana_add = (max_mana/32);
        if(mana < max_mana) mana = (mana + mana_add <= max_mana) ? mana_add + mana : max_mana;
        if (usingWand)
            updateManaBar();
    }
}
