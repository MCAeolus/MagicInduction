package me.mcaeolus.magicinduction;

import me.mcaeolus.magicinduction.data.UserData;
import me.mcaeolus.magicinduction.multiblock.MultiManager;
import me.mcaeolus.magicinduction.multiblock.Multiblock;
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
    private static UserData DATA;
    private WandListener wListener;
    private MultiManager MBM;

    @Override
    public void onEnable() {
        mInduction = this;

        DATA = new UserData();

        new RecipeSetup();
        wListener = new WandListener();
        MBM = new MultiManager();
    }

    @Override
    public void onDisable() {
        mInduction = null;

        wListener.saveAll();

        DATA.save();
    }

    public MultiManager getMultiManager() { return MBM; }

    public static MagicInduction getInstance(){
        return mInduction;
    }

    public static Server getLocalServer(){
        return mInduction.getServer();
    }

    public static UserData getData(){
        return DATA;
    }

}
