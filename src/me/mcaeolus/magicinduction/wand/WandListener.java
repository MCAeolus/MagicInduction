package me.mcaeolus.magicinduction.wand;

import me.mcaeolus.magicinduction.MagicInduction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class WandListener implements Listener {

    private static HashMap<UUID, WandUser> USERS = new HashMap<>();

    public WandListener(){
        MagicInduction.getLocalServer().getPluginManager().registerEvents(this, MagicInduction.getInstance());
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        USERS.get(e.getPlayer().getUniqueId()).wandInteract(e);
    }

    @EventHandler
    public void changeHeld(PlayerItemHeldEvent e){
        USERS.get(e.getPlayer().getUniqueId()).itemHeld(e);
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        WandUser user =  new WandUser(e.getPlayer());
        user.runTaskTimer(MagicInduction.getInstance(), 20 * 4, 20 * 2);
        USERS.put(e.getPlayer().getUniqueId(), user);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        USERS.get(e.getPlayer().getUniqueId()).saveAndExit();
        USERS.remove(e.getPlayer().getUniqueId());
    }

    public void saveAll(){
        for(Map.Entry<UUID, WandUser> x : USERS.entrySet()){
            x.getValue().saveAndExit();
        }
    }

    public static WandUser getUser(UUID playerId){
        return USERS.get(playerId);
    }
}
