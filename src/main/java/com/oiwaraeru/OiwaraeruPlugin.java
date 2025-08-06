package com.oiwaraeru;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class OiwaraeruPlugin extends JavaPlugin implements Listener {

    private final Map<UUID, Set<String>> craftedItems = new HashMap<>();
    private final Map<UUID, Boolean> resurrectionFlag = new HashMap<>();

    // アイテムのレシピKey定義
    private final NamespacedKey vampireSwordKey = new NamespacedKey(this, "vampire_sword");
    private final NamespacedKey iceRodKey = new NamespacedKey(this, "ice_rod");
    private final NamespacedKey fireRodKey = new NamespacedKey(this, "fire_rod");
    private final NamespacedKey legendaryBowKey = new NamespacedKey(this, "legendary_bow");
    private final NamespacedKey legendarySwordKey = new NamespacedKey(this, "legendary_sword");
    private final NamespacedKey bestFoodKey = new NamespacedKey(this, "best_food");
    private final NamespacedKey legendaryCrossbowKey = new NamespacedKey(this, "legendary_crossbow");
    private final NamespacedKey handymanKey = new NamespacedKey(this, "handyman");
    private final NamespacedKey brokenEnderPearlKey = new NamespacedKey(this, "broken_ender_pearl");
    private final NamespacedKey throwableSpongeKey = new NamespacedKey(this, "throwable_sponge");
    private final NamespacedKey superTntKey = new NamespacedKey(this, "super_tnt");
    private final NamespacedKey darkSwordKey = new NamespacedKey(this, "dark_sword");
    private final NamespacedKey immobilizeBowKey = new NamespacedKey(this, "immobilize_bow");

    @Override
    public void onEnable() {
        getLogger().info("Oiwaraeru Plugin enabled");
        getServer().getPluginManager().registerEvents(this, this);
        registerRecipes();
    }

    @Override
    public void onDisable() {
        getLogger().info("Oiwaraeru Plugin disabled");
    }

    private void registerRecipes() {
        // 吸血鬼の剣
        ShapedRecipe vampireSwordRecipe = new ShapedRecipe(vampireSwordKey, createVampireSword());
        vampireSwordRecipe.shape("  m", "epe", "isb");
        vampireSwordRecipe.setIngredient('m', Material.ROTTEN_FLESH); // 羊の生肉はないので腐った肉代用
        vampireSwordRecipe.setIngredient('e', Material.ENDER_EYE);
        vampireSwordRecipe.setIngredient('p', Material.STICK);
        vampireSwordRecipe.setIngredient('i', Material.IRON_SWORD);
        vampireSwordRecipe.setIngredient('s', Material.BLAZE_POWDER);
        vampireSwordRecipe.setIngredient('b', Material.BLAZE_POWDER);
        Bukkit.addRecipe(vampireSwordRecipe);

        // 氷の杖
        ShapedRecipe iceRodRecipe = new ShapedRecipe(iceRodKey, createIceRod());
        iceRodRecipe.shape("  i", " s ", "   ");
        iceRodRecipe.setIngredient('i', Material.ICE);
        iceRodRecipe.setIngredient('s', Material.SNOW_BLOCK);
        iceRodRecipe.setIngredient(' ', Material.AIR);
        iceRodRecipe.setIngredient(' ', Material.AIR);
        Bukkit.addRecipe(iceRodRecipe);

        // 炎の杖
        ShapedRecipe fireRodRecipe = new ShapedRecipe(fireRodKey, createFireRod());
        fireRodRecipe.shape("  s", " m ", "bs ");
        fireRodRecipe.setIngredient('s', Material.BLAZE_POWDER);
        fireRodRecipe.setIngredient('m', Material.MAGMA_BLOCK);
        fireRodRecipe.setIngredient('b', Material.STICK);
        fireRodRecipe.setIngredient(' ', Material.SOUL_SAND);
        Bukkit.addRecipe(fireRodRecipe);

        // 伝説の弓
        ShapedRecipe legendaryBowRecipe = new ShapedRecipe(legendaryBowKey, createLegendaryBow());
        legendaryBowRecipe.shape(" p ", "aya", " f ");
        legendaryBowRecipe.setIngredient('p', Material.PAPER);
        legendaryBowRecipe.setIngredient('a', Material.ARROW);
        legendaryBowRecipe.setIngredient('y', Material.BOW);
        legendaryBowRecipe.setIngredient('f', Material.FLINT_AND_STEEL);
        Bukkit.addRecipe(legendaryBowRecipe);

        // 他のレシピも同様に登録してください
    }

    private ItemStack createVampireSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "吸血鬼の剣");
        meta.setLore(Collections.singletonList(ChatColor.RED + "攻撃した分の50%を吸収する"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createIceRod() {
        ItemStack item = new ItemStack(Material.BRINE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "氷の杖");
        meta.setLore(Collections.singletonList(ChatColor.BLUE + "ブロックリーチ50m、移動速度低下付与"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createFireRod() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "炎の杖");
        meta.setLore(Collections.singletonList(ChatColor.GOLD + "周囲のmobに炎上付与"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createLegendaryBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "伝説の弓");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "チャージで最大750ダメージ"));
        item.setItemMeta(meta);
        return item;
    }

    // ここに残りのアイテム生成メソッドも同様に実装してください

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        // 隠しアイテムクラフト監視、レシピ削除、全体チャット表示などの処理をここに実装
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // 吸血鬼の剣、伝説の剣、炎の杖などの特殊効果処理をここに実装
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event) {
        // 不死のトーテム（隠しトーテム）優先処理をここに実装
    }

    // 他にも必要なイベント・メソッドを実装してください

}
