    package com.oiwaraeru;

import com.oiwaraeru.listeners.ItemListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class OiwaraeruPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Oiwaraeru Plugin Enabled");
        Bukkit.getPluginManager().registerEvents(new ItemListener(this), this);
        registerRecipes();
    }

    private void registerRecipes() {
        // 吸血鬼の剣レシピ
        ItemStack vampireSword = ItemListener.createVampireSword();
        ShapedRecipe vampireRecipe = new ShapedRecipe(new NamespacedKey(this, "vampire_sword"), vampireSword);
        vampireRecipe.shape("  m", "epe", "isb");
        vampireRecipe.setIngredient('m', org.bukkit.Material.ROTTEN_FLESH);
        vampireRecipe.setIngredient('e', org.bukkit.Material.ENDER_EYE);
        vampireRecipe.setIngredient('p', org.bukkit.Material.STICK);
        vampireRecipe.setIngredient('i', org.bukkit.Material.IRON_SWORD);
        vampireRecipe.setIngredient('s', org.bukkit.Material.BLAZE_POWDER);
        vampireRecipe.setIngredient('b', org.bukkit.Material.BLAZE_POWDER);
        Bukkit.addRecipe(vampireRecipe);

        // 氷の杖レシピ
        ItemStack iceRod = ItemListener.createIceRod();
        ShapedRecipe iceRodRecipe = new ShapedRecipe(new NamespacedKey(this, "ice_rod"), iceRod);
        iceRodRecipe.shape("  i", " s ", "   ");
        iceRodRecipe.setIngredient('i', org.bukkit.Material.ICE);
        iceRodRecipe.setIngredient('s', org.bukkit.Material.SNOW_BLOCK);
        Bukkit.addRecipe(iceRodRecipe);

        // 炎の杖レシピ
        ItemStack fireRod = ItemListener.createFireRod();
        ShapedRecipe fireRodRecipe = new ShapedRecipe(new NamespacedKey(this, "fire_rod"), fireRod);
        fireRodRecipe.shape("  s", " m ", "bs ");
        fireRodRecipe.setIngredient('s', org.bukkit.Material.BLAZE_POWDER);
        fireRodRecipe.setIngredient('m', org.bukkit.Material.MAGMA_BLOCK);
        fireRodRecipe.setIngredient('b', org.bukkit.Material.STICK);
        Bukkit.addRecipe(fireRodRecipe);

        // 伝説の弓レシピ
        ItemStack legendaryBow = ItemListener.createLegendaryBow();
        ShapedRecipe legendaryBowRecipe = new ShapedRecipe(new NamespacedKey(this, "legendary_bow"), legendaryBow);
        legendaryBowRecipe.shape(" p ", "aya", " f ");
        legendaryBowRecipe.setIngredient('p', org.bukkit.Material.PAPER);
        legendaryBowRecipe.setIngredient('a', org.bukkit.Material.ARROW);
        legendaryBowRecipe.setIngredient('y', org.bukkit.Material.BOW);
        legendaryBowRecipe.setIngredient('f', org.bukkit.Material.FLINT_AND_STEEL);
        Bukkit.addRecipe(legendaryBowRecipe);

        // 伝説の剣レシピ
        ItemStack legendarySword = ItemListener.createLegendarySword();
        ShapedRecipe legendarySwordRecipe = new ShapedRecipe(new NamespacedKey(this, "legendary_sword"), legendarySword);
        legendarySwordRecipe.shape(" n ", "sss", " s ");
        legendarySwordRecipe.setIngredient('n', org.bukkit.Material.NETHERITE_SWORD);
        legendarySwordRecipe.setIngredient('s', org.bukkit.Material.GOLD_INGOT);
        Bukkit.addRecipe(legendarySwordRecipe);

        // 最高の食料レシピ
        ItemStack bestFood = ItemListener.createBestFood();
        ShapedRecipe bestFoodRecipe = new ShapedRecipe(new NamespacedKey(this, "best_food"), bestFood);
        bestFoodRecipe.shape(" s ", "sgs", " s ");
        bestFoodRecipe.setIngredient('s', org.bukkit.Material.COOKED_BEEF);
        bestFoodRecipe.setIngredient('g', org.bukkit.Material.GOLDEN_CARROT);
        Bukkit.addRecipe(bestFoodRecipe);

        // 伝説のクロスボウレシピ
        ItemStack legendaryCrossbow = ItemListener.createLegendaryCrossbow();
        ShapedRecipe legendaryCrossbowRecipe = new ShapedRecipe(new NamespacedKey(this, "legendary_crossbow"), legendaryCrossbow);
        legendaryCrossbowRecipe.shape(" a ", " c ", " b ");
        legendaryCrossbowRecipe.setIngredient('a', org.bukkit.Material.ARROW);
        legendaryCrossbowRecipe.setIngredient('c', org.bukkit.Material.CROSSBOW);
        legendaryCrossbowRecipe.setIngredient('b', org.bukkit.Material.BLAZE_POWDER);
        Bukkit.addRecipe(legendaryCrossbowRecipe);

        // なんでも屋さんレシピ
        ItemStack handyman = ItemListener.createHandyman();
        ShapedRecipe handymanRecipe = new ShapedRecipe(new NamespacedKey(this, "handyman"), handyman);
        handymanRecipe.shape("   ", " e ", "   ");
        handymanRecipe.setIngredient('e', org.bukkit.Material.EMERALD_BLOCK);
        Bukkit.addRecipe(handymanRecipe);

        // 壊れたエンダーパールレシピ
        ItemStack brokenEnderPearl = ItemListener.createBrokenEnderPearl();
        ShapedRecipe brokenEnderPearlRecipe = new ShapedRecipe(new NamespacedKey(this, "broken_ender_pearl"), brokenEnderPearl);
        brokenEnderPearlRecipe.shape("   ", " e ", "   ");
        brokenEnderPearlRecipe.setIngredient('e', org.bukkit.Material.ENDER_PEARL);
        Bukkit.addRecipe(brokenEnderPearlRecipe);

        // 投げるスポンジレシピ
        ItemStack throwingSponge = ItemListener.createThrowingSponge();
        ShapedRecipe throwingSpongeRecipe = new ShapedRecipe(new NamespacedKey(this, "throwing_sponge"), throwingSponge);
        throwingSpongeRecipe.shape("   ", " s ", "   ");
        throwingSpongeRecipe.setIngredient('s', org.bukkit.Material.SPONGE);
        Bukkit.addRecipe(throwingSpongeRecipe);

        // 超強力TNTo-6型b式レシピ
        ItemStack superTNT = ItemListener.createSuperTNT();
        ShapedRecipe superTNTRecipe = new ShapedRecipe(new NamespacedKey(this, "super_tnt"), superTNT);
        superTNTRecipe.shape("   ", " t ", "   ");
        superTNTRecipe.setIngredient('t', org.bukkit.Material.TNT);
        Bukkit.addRecipe(superTNTRecipe);

        // 暗黒の剣レシピ
        ItemStack darkSword = ItemListener.createDarkSword();
        ShapedRecipe darkSwordRecipe = new ShapedRecipe(new NamespacedKey(this, "dark_sword"), darkSword);
        darkSwordRecipe.shape(" n ", " n ", " n ");
        darkSwordRecipe.setIngredient('n', org.bukkit.Material.NETHERITE_SWORD);
        Bukkit.addRecipe(darkSwordRecipe);

        // 移動不可の弓レシピ
        ItemStack immobileBow = ItemListener.createImmobileBow();
        ShapedRecipe immobileBowRecipe = new ShapedRecipe(new NamespacedKey(this, "immobile_bow"), immobileBow);
        immobileBowRecipe.shape(" b ", " b ", " b ");
        immobileBowRecipe.setIngredient('b', org.bukkit.Material.BOW);
        Bukkit.addRecipe(immobileBowRecipe);
    }
}
