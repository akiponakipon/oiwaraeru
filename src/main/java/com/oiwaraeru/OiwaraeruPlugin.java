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

        // 例: カスタムレシピを複数登録
        registerRecipes();
    }

    @Override
    public void onDisable() {
        getLogger().info("Oiwaraeru Plugin Disabled!");
    }

    private void registerRecipes() {
        // 1つ目: ダイヤ剣
        ItemStack customSword = new ItemStack(Material.DIAMOND_SWORD);
        NamespacedKey swordKey = new NamespacedKey(this, "custom_sword");
        ShapedRecipe swordRecipe = new ShapedRecipe(swordKey, customSword);
        swordRecipe.shape(" D ", " D ", " S ");
        swordRecipe.setIngredient('D', Material.DIAMOND);
        swordRecipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(swordRecipe);

        // 2つ目: ダイヤブロック -> ネザライトインゴット
        ItemStack customIngot = new ItemStack(Material.NETHERITE_INGOT);
        NamespacedKey ingotKey = new NamespacedKey(this, "custom_ingot");
        ShapedRecipe ingotRecipe = new ShapedRecipe(ingotKey, customIngot);
        ingotRecipe.shape("DDD", "DDD", "DDD");
        ingotRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(ingotRecipe);
    }
}
