package me.mcaeolus.magicinduction;

import me.mcaeolus.magicinduction.data.UserData;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class MagicInduction extends JavaPlugin {

    private static MagicInduction mInduction;
    private UserData DATA;

    @Override
    public void onEnable() {
        mInduction = this;

        DATA = new UserData();
    }

    @Override
    public void onDisable() {
        mInduction = null;


    }

    public static MagicInduction getInstance(){
        return mInduction;
    }

    public static Server getLocalServer(){
        return mInduction.getServer();
    }

}
