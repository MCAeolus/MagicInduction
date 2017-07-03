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
        NamespacedKey wand_key = new NamespacedKey(MagicInduction.getInstance(), "wand");
        ShapedRecipe wand_recipe = new ShapedRecipe(wand_key, wand);
        wand_recipe.shape(" g/",
                          "rdr",
                          "/g ");
        wand_recipe.setIngredient('g', Material.GOLD_NUGGET);
        wand_recipe.setIngredient('/', Material.STICK);
        wand_recipe.setIngredient('r', Material.IRON_NUGGET);
        wand_recipe.setIngredient('d', Material.DIAMOND);
        MagicInduction.getInstance().getServer().addRecipe(wand_recipe);

        ItemStack focus_ender = new ItemBuilder(Material.EYE_OF_ENDER)
                .name(ChatColor.AQUA + "Ender Focus")
                .lore(HexStringUtil.encodeString("{\"isFocus\":\"true\"}")).make();
        NamespacedKey focus_ender_key = new NamespacedKey(MagicInduction.getInstance(), "ender_focus");
        ShapedRecipe focus_ender_recipe = new ShapedRecipe(focus_ender_key, focus_ender);
        focus_ender_recipe.shape("iei",
                                 "gdg",
                                 "iei");
        focus_ender_recipe.setIngredient('g', Material.GOLD_NUGGET);
        focus_ender_recipe.setIngredient('i', Material.IRON_INGOT);
        focus_ender_recipe.setIngredient('e', Material.ENDER_PEARL);
        focus_ender_recipe.setIngredient('d', Material.DIAMOND);
        MagicInduction.getInstance().getServer().addRecipe(focus_ender_recipe);





    }
}
