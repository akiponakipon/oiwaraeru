 package com.oiwaraeru;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class OiwaraeruPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Oiwaraeru Plugin Enabled!");

        // 例: カスタムレシピ登録
        ItemStack customItem = new ItemStack(Material.DIAMOND_SWORD);
        NamespacedKey key = new NamespacedKey(this, "custom_sword");
        ShapedRecipe recipe = new ShapedRecipe(key, customItem);
        recipe.shape(" D ", " D ", " S ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {
        getLogger().info("Oiwaraeru Plugin Disabled!");
    }
}


