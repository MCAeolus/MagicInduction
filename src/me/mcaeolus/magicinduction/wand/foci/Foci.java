package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.util.HexStringUtil;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class Foci {

    private FociType TYPE;
    private String NAME;
    private Material MAT;

    public Foci(String NAME, FociType TYPE, Material MAT){
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.MAT = MAT;
    }

    public void interceptInteractEvent(PlayerInteractEvent e, WandUser user){
        //Override in subclasses.
    }

    public Material getMaterial(){
        return this.MAT;
    }

    public FociType getType(){
        return TYPE;
    }

    public String getName(){
        return NAME;
    }

    public static boolean isFocus(ItemStack x){
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
                return (jOb != null && jOb.containsKey("isFocus"));
            }
        }
        return false;
    }
}
