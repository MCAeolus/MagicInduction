package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class EnderFoci extends Foci {

    private static HashSet<Material> BLACKLIST = new HashSet<>();
    static{
        BLACKLIST.add(Material.LONG_GRASS);
        BLACKLIST.add(Material.YELLOW_FLOWER);
        BLACKLIST.add(Material.DOUBLE_PLANT);
        BLACKLIST.add(Material.AIR);
    }


    public EnderFoci() {
        super(ChatColor.DARK_PURPLE +"Ender Focus", FociType.ENDER_FOCUS, Material.ENDER_STONE);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {
        Action action = e.getAction();
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){ //TELEPORT
            Player p = u.getPlayer();
            Block potential = p.getTargetBlock(BLACKLIST, 30);
            if(u.getMana() < 100){
                p.sendMessage(ChatColor.RED + "You don't have enough mana to use this spell! (Minimum mana requirement: 100)");
                p.getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 5, 0, 0,0, 0.01);
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 5, 5);
                return;
            }
            if(potential.getType() == Material.AIR){
                p.sendMessage(ChatColor.RED + "That area is not safe to teleport to!");
                return;
            }
            for(int y = 0; y < 2; y++){
                for(int x = -1; x < 2; x++){
                    for(int z = -1; z < 2; z++){
                        Block MOD = potential.getRelative(x, y+1, z);
                        if(!BLACKLIST.contains(MOD.getType())){
                            p.sendMessage(ChatColor.RED + "That area is not safe to teleport to!");
                            return;
                        }
                    }
                }
            }
            u.attemptUseMana(100);
            new BukkitRunnable(){
                private double rel_y = 0;
                private double radius = 1;

                private Location l = p.getLocation();
                private Location l2 = potential.getLocation().add(0,3,0);

                @Override
                public void run() {
                    if(rel_y > 2) {
                        this.cancel();
                        p.teleport(potential.getLocation().add(0, 1, 0));
                    }else{
                        rel_y+=.15;
                        radius-=.05;
                        double x = radius * Math.cos(rel_y * 6);
                        double z = radius * Math.sin(rel_y * 6);

                        p.getWorld().spawnParticle(Particle.DRAGON_BREATH, p.getLocation().add(x,rel_y,z), 3, 0, 0, 0, 0);
                        p.getWorld().spawnParticle(Particle.SPELL_INSTANT, l2.subtract(z,0,z), 3, 0, 0, 0, 0);

                        if(Math.abs(l.getX() - p.getLocation().getX()) > 0.5 || Math.abs(l.getZ() - p.getLocation().getZ()) > 0.5)
                            p.teleport(l);
                    }

                }
            }.runTaskTimer(MagicInduction.getInstance(), 0, 2);

        }
    }
}
