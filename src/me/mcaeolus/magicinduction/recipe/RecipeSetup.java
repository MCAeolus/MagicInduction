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

        ItemStack focus_fire = new ItemBuilder(Material.EYE_OF_ENDER)
                .name(ChatColor.RED + "Fire Focus")
                .lore(HexStringUtil.encodeString("{\"isFocus\":\"true\"}")).make();
        NamespacedKey focus_fire_key = new NamespacedKey(MagicInduction.getInstance(), "fire_focus");
        ShapedRecipe focus_fire_recipe = new ShapedRecipe(focus_fire_key, focus_fire);
        focus_fire_recipe.shape("iei",
                "gdg",
                "iei");
        focus_fire_recipe.setIngredient('g', Material.GOLD_NUGGET);
        focus_fire_recipe.setIngredient('i', Material.IRON_INGOT);
        focus_fire_recipe.setIngredient('e', Material.LAVA_BUCKET);
        focus_fire_recipe.setIngredient('d', Material.DIAMOND);
        MagicInduction.getInstance().getServer().addRecipe(focus_fire_recipe);

        ItemStack focus_ice = new ItemBuilder(Material.EYE_OF_ENDER)
                .name(ChatColor.DARK_BLUE + "Ice Focus")
                .lore(HexStringUtil.encodeString("{\"isFocus\":\"true\"}")).make();
        NamespacedKey focus_ice_key = new NamespacedKey(MagicInduction.getInstance(), "ice_focus");
        ShapedRecipe focus_ice_recipe = new ShapedRecipe(focus_ice_key, focus_ice);
        focus_ice_recipe.shape("iei",
                "gdg",
                "iei");
        focus_ice_recipe.setIngredient('g', Material.GOLD_NUGGET);
        focus_ice_recipe.setIngredient('i', Material.IRON_INGOT);
        focus_ice_recipe.setIngredient('e', Material.PACKED_ICE);
        focus_ice_recipe.setIngredient('d', Material.DIAMOND);
        MagicInduction.getInstance().getServer().addRecipe(focus_ice_recipe);




    }
}
