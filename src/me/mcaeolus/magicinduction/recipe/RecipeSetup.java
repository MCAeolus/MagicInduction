package me.mcaeolus.magicinduction.recipe;


import me.mcaeolus.magicinduction.MagicInduction;
import me.mcaeolus.magicinduction.util.HexStringUtil;
import me.mcaeolus.magicinduction.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Created by mcaeo on 7/1/2017.
 */
public class RecipeSetup {

    public RecipeSetup(){
        //Wand crafting
        ItemStack wand = new ItemBuilder(Material.STICK)
                .name(ChatColor.GRAY + "Wand" + " - " + ChatColor.DARK_RED + "Not Empowered")
                .lore(HexStringUtil.encodeString("{\"isWand\":\"true\",\"isActivated\":\"false\"}")).make();
        NamespacedKey key = new NamespacedKey(MagicInduction.getInstance(), "wand");
        ShapedRecipe wand_recipe = new ShapedRecipe(key, wand);
        wand_recipe.shape(" g/",
                          "rdr",
                          "/g ");
        wand_recipe.setIngredient('g', Material.GOLD_NUGGET);
        wand_recipe.setIngredient('/', Material.STICK);
        wand_recipe.setIngredient('r', Material.IRON_NUGGET);
        wand_recipe.setIngredient('d', Material.DIAMOND);
        MagicInduction.getInstance().getServer().addRecipe(wand_recipe);

    }
}
