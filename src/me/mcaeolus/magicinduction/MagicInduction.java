package me.mcaeolus.magicinduction;

import me.mcaeolus.magicinduction.data.StructureData;
import me.mcaeolus.magicinduction.data.UserData;
import me.mcaeolus.magicinduction.multiblock.MultiManager;
import me.mcaeolus.magicinduction.multiblock.Multiblock;
import me.mcaeolus.magicinduction.recipe.RecipeListener;
import me.mcaeolus.magicinduction.recipe.RecipeSetup;
import me.mcaeolus.magicinduction.wand.WandListener;
import org.bukkit.Server;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class MagicInduction extends JavaPlugin {

    private static MagicInduction mInduction;
    private static UserData U_DATA;
    private static StructureData ST_DATA;
    private WandListener wListener;
    private MultiManager MBM;

    @Override
    public void onEnable() {
        mInduction = this;

        U_DATA = new UserData();
        ST_DATA = new StructureData();

        new RecipeSetup();
        wListener = new WandListener();
        new RecipeListener();
        MBM = new MultiManager();
    }

    @Override
    public void onDisable() {
        mInduction = null;

        wListener.saveAll();

        U_DATA.save();
        ST_DATA.save();
    }

    public MultiManager getMultiManager() { return MBM; }

    public static MagicInduction getInstance(){
        return mInduction;
    }

    public static Server getLocalServer(){
        return mInduction.getServer();
    }

    public static UserData getUserData(){
        return U_DATA;
    }

    public static StructureData getStructureData() { return ST_DATA; }

}
