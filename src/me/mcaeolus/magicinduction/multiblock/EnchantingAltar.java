package me.mcaeolus.magicinduction.multiblock;

import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class EnchantingAltar extends Multiblock {
    private static HashMap<Character, Material> chars = new HashMap<>();
    private static HashMap<UUID, Long> waitMap = new HashMap<>();
    static{
        chars.put('w',Material.SMOOTH_BRICK);
        chars.put(' ',Material.AIR);
        chars.put('i',Material.IRON_BLOCK);
        chars.put('s',Material.COBBLE_WALL);

    }
    public EnchantingAltar() {
        super("EnchantingAltar", chars, Material.IRON_BLOCK, new Character[][]{{'w',' ',' ',' ','w'},
                                                            {' ',' ',' ',' ',' '},
                                                            {' ',' ','i',' ',' '},
                                                            {' ',' ',' ',' ',' '},
                                                            {'w',' ',' ',' ','w'}}
                                    , new Character[][]{{'s',' ',' ',' ','s'},
                                                        {' ',' ',' ',' ',' '},
                                                        {' ',' ',' ',' ',' '},
                                                        {' ',' ',' ',' ',' '},
                                                        {'s',' ',' ',' ','s'}});
    }

    @Override
    public boolean interact(PlayerInteractEvent e, WandUser u){
        Block ms = e.getClickedBlock();

        if(ms.getType() == Material.IRON_BLOCK) {
            if (!waitMap.containsKey(u.getPlayer().getUniqueId()) || (System.currentTimeMillis() - waitMap.get(u.getPlayer().getUniqueId()) > 250)) {
                waitMap.put(u.getPlayer().getUniqueId(), System.currentTimeMillis());
                if (build(ms, u) == null) {
                    u.getPlayer().sendMessage(ChatColor.RED + "Make sure that you have enough space for the structure you are trying to spawn! Something is stopping it from being created.");
                    u.getPlayer().playSound(u.getPlayer().getEyeLocation(), Sound.BLOCK_ANVIL_DESTROY, 5, 1);
                    return false;
                }
                ms.getWorld().strikeLightningEffect(ms.getLocation().add(0, 1, 0));
                ms.getWorld().playSound(ms.getLocation().add(0, 1, 0), Sound.ENTITY_FIREWORK_LARGE_BLAST, 5, 5);
                return true;
            }
        }
        return false;
    }

    public static void removeUUID(UUID id){
        waitMap.remove(id);
    }

    @Override
    public Location[] build(Block center, WandUser user){
        Location[] corners = super.build(center, user);
        if(corners == null)
            return null;
        else {
            new BukkitRunnable(){
                private double radius = 2,radius2 = 0;
                private double rel_y = 0;

                @Override
                public void run() {
                    if(rel_y > 6){
                        this.cancel();
                    }
                    radius2 += 0.1;
                    double x1 = radius * Math.cos(rel_y*8),x2 = radius * Math.cos((rel_y*8)+15),x3 = radius * Math.cos((rel_y*8)+30),x4 = radius2 * Math.cos(rel_y*8);
                    double z1 = radius * Math.sin(rel_y*8),z2 = radius * Math.sin((rel_y*8)+15),z3 = radius * Math.sin((rel_y*8)+30),z4 = radius2 * Math.sin(rel_y*8);
                    rel_y +=.15;
                    Location center_l = center.getLocation().add(0.5,0,0.5);
                    center.getWorld().spawnParticle(Particle.SPELL_INSTANT, center_l.add(x1,rel_y,z1), 3, 0, 0, 0, 0);
                    center.getWorld().spawnParticle(Particle.SPELL_INSTANT, center_l.add(x2,rel_y,z2), 3, 0, 0, 0, 0);
                    center.getWorld().spawnParticle(Particle.SPELL_INSTANT, center_l.add(x3,rel_y,z3), 3, 0, 0, 0, 0);
                    center.getWorld().spawnParticle(Particle.FLAME, center_l.add(x4,rel_y,z4), 3, 0, 0, 0, 0);

                }
            }.runTaskTimer(MagicInduction.getInstance(), 0, 2);
            return corners;
        }
    }
}
