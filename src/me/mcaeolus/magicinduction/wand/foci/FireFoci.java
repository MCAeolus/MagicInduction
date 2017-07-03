package me.mcaeolus.magicinduction.wand.foci;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.util.ItemBuilder;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class FireFoci extends Foci {

    private static HashSet<Material> BLACKLIST = new HashSet<>();
    static{
        BLACKLIST.add(Material.LONG_GRASS);
        BLACKLIST.add(Material.YELLOW_FLOWER);
        BLACKLIST.add(Material.DOUBLE_PLANT);
        BLACKLIST.add(Material.AIR);
    }

    public FireFoci() {
        super(ChatColor.RED+"Fire Focus", FociType.FIRE_FOCUS, Material.MAGMA);
    }

    @Override
    public void interceptInteractEvent(PlayerInteractEvent e, WandUser u) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player p = u.getPlayer();
            if(u.getMana() >= 100){
                u.attemptUseMana(100);
                new BukkitRunnable(){
                    private Location from = e.getClickedBlock().getLocation().add(0.5, 10, 0.5);
                    private double rel_y = 0;
                    @Override
                    public void run() {
                        if(rel_y <= -4.2){
                            e.getClickedBlock().setType(Material.LAVA);
                            e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_GRASS_BREAK, 5, 5);
                            u.setCasting(false);
                            this.cancel();
                        }else{
                            rel_y-= 0.2;
                            double x = .4 * Math.cos(rel_y), z = .4 * Math.sin(rel_y);
                            e.getClickedBlock().getWorld().spawnParticle(Particle.FLAME, from.add(0,rel_y,0), 2, 0, 0, 0 ,0);
                            e.getClickedBlock().getWorld().spawnParticle(Particle.LAVA, from.add(0,rel_y,0), 2, 0, 0, 0 ,0);
                            //from.getWorld().spawnParticle(Particle.SPELL_INSTANT, e.getClickedBlock().getLocation().add(0.5,0.7,0.5), 6, 0, 0, 0, 0);
                            e.getClickedBlock().getWorld().spawnParticle(Particle.SPELL_INSTANT, e.getClickedBlock().getLocation().add(0.5,1.1,0.5).add(x,0,z),0,0,0,0,0);
                        }
                    }
                }.runTaskTimer(MagicInduction.getInstance(), 0, 2);
            }else{
                p.sendMessage(ChatColor.RED + "You don't have enough mana to use this spell! (Minimum mana requirement: 100)");
                u.setCasting(false);
            }
        }else if (e.getAction() == Action.RIGHT_CLICK_AIR){
            Player p = u.getPlayer();
            Block target = p.getTargetBlock(BLACKLIST, 30);
            Location targetl = target.getLocation().add(0.5,1,0.5);
            if(u.getMana() >= 100) {
                u.attemptUseMana(100);
                new BukkitRunnable(){
                    private Location center = targetl.add(0,1,0);
                    private double pi_iterator = 0;
                    @Override
                    public void run() {
                        if(pi_iterator > 4){
                            for(Entity x : targetl.getWorld().getNearbyEntities(targetl, 3,1,3)){
                                if(x instanceof LivingEntity){
                                    x.setFireTicks(7 * 20);
                                    x.getWorld().spawnParticle(Particle.LAVA, x.getLocation().add(0,1,0), 10, 0, 0, 0, 0);
                                }
                            }
                            this.cancel();
                            u.setCasting(false);
                        }else {
                            pi_iterator += .3;
                            double x = 3 * Math.cos(pi_iterator), z = 3 * Math.sin(pi_iterator);
                            center.getWorld().spawnParticle(Particle.FLAME, center.clone().add(x, 0, z), 1, 0, 0, 0, 0);
                            center.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, center.clone().add(-x, 0, -z), 1, 0, 0, 0, 0);
                        }
                    }
                }.runTaskTimer(MagicInduction.getInstance(), 0, 2);


            }else{
                p.sendMessage(ChatColor.RED + "You don't have enough mana to use this spell! (Minimum mana requirement: 100)");
                u.setCasting(false);
            }
        }
    }
}
