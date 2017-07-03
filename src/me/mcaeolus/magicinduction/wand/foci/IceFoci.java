package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.wand.WandUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class IceFoci extends Foci {

    private static Random r = new Random();
    private static HashSet<Material> BLACKLIST = new HashSet<>();
    static{
        BLACKLIST.add(Material.LONG_GRASS);
        BLACKLIST.add(Material.YELLOW_FLOWER);
        BLACKLIST.add(Material.DOUBLE_PLANT);
        BLACKLIST.add(Material.AIR);
    }

    public IceFoci() {
        super(ChatColor.BLUE + "Ice Focus", FociType.ICE_FOCUS, Material.ICE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player p = u.getPlayer();
            if(u.getMana() >= 100) {
                Block target = p.getTargetBlock(BLACKLIST, 30);
                u.attemptUseMana(100);
                target.getWorld().strikeLightningEffect(target.getLocation());
                for(int x = -2; x < 3; x++){
                    for(int z = -2; z < 3; z++){
                        Block xb = target.getRelative(x, 0, z);

                        if(xb.getType() == Material.WATER || xb.getType() == Material.STATIONARY_WATER) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (r.nextInt(100) < 20) xb.setType(Material.PACKED_ICE);
                                    else xb.setType(Material.ICE);
                                    xb.getWorld().spawnParticle(Particle.SNOW_SHOVEL, xb.getLocation().add(0.5, 1.2, 0.5), 4, 0, 0, 0, 0);
                                }
                            }.runTaskLater(MagicInduction.getInstance(), r.nextInt(60));
                        }else{
                            xb.getWorld().spawnParticle(Particle.BARRIER, xb.getLocation().add(0.5, 1.2, 0.5), 4, 0, 0, 0, 0);
                        }
                        u.setCasting(false);

                    }
                }
            }else{
                p.sendMessage(ChatColor.RED + "You don't have enough mana to use this spell! (Minimum mana requirement: 100)");
                u.setCasting(false);
            }
        }
    }
}
