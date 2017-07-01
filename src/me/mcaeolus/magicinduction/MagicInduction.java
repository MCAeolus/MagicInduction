package me.mcaeolus.magicinduction;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class MagicInduction extends JavaPlugin {

    private static MagicInduction mInduction;

    @Override
    public void onEnable() {
        mInduction = this;


    }

    @Override
    public void onDisable() {
        mInduction = null;


    }

    public static MagicInduction getInstance(){
        return mInduction;
    }

    public static Server getLocalServer(){
        return mInduction.getLocalServer();
    }

}
