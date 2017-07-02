package me.mcaeolus.magicinduction.multiblock;

import me.mcaeolus.magicinduction.wand.WandUser;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class EnchantingAltar extends Multiblock {
    private static List<Cuboid> altars = new ArrayList<>();
    private static HashMap<Character, Material> chars = new HashMap<>();
    private static HashMap<UUID, Long> waitMap = new HashMap<>();
    static{
        chars.put('w',Material.SMOOTH_BRICK);
        chars.put(' ',Material.AIR);
        chars.put('i',Material.IRON_BLOCK);
        chars.put('s',Material.COBBLE_WALL);

    }
    public EnchantingAltar() {
        super(chars, Material.IRON_BLOCK, new Character[][]{{'w',' ',' ',' ','w'},
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
            altars.add(new Cuboid(corners[0], corners[1], user.getPlayer().getUniqueId(), this));
            return corners;
        }
    }
}
