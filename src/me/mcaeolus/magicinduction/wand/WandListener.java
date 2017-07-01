package me.mcaeolus.magicinduction.wand;

import me.mcaeolus.magicinduction.MagicInduction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class WandListener implements Listener {

    private HashMap<UUID, WandUser> USERS = new HashMap<>();

    public WandListener(){
        MagicInduction.getLocalServer().getPluginManager().registerEvents(this, MagicInduction.getInstance());
    }

    @EventHandler
    public void changeHand(PlayerChangedMainHandEvent e){
        USERS.get(e.getPlayer().getUniqueId()).wandChange(e);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        USERS.get(e.getPlayer().getUniqueId()).wandInteract(e);
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        USERS.put(e.getPlayer().getUniqueId(), new WandUser(e.getPlayer())).runTaskTimer(MagicInduction.getInstance(), 20 * 4, 20 * 2);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        USERS.get(e.getPlayer().getUniqueId()).saveAndExit();
        USERS.remove(e.getPlayer().getUniqueId());
    }
}
