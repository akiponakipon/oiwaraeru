package com.oiwaraeru;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class OiwaraeruPlugin extends JavaPlugin implements Listener {

    private final Map<UUID, Set<String>> craftedItems = new HashMap<>();
    private final Map<UUID, Long> iceRodCooldown = new HashMap<>();
    private final Map<UUID, Long> fireRodCooldown = new HashMap<>();
    private final Map<UUID, Long> legendaryBowChargeStart = new HashMap<>();
    private final Set<UUID> chargingPlayers = new HashSet<>();

    private static final String VAMPIRE_SWORD_NAME = ChatColor.DARK_RED + "吸血鬼の剣";
    private static final String ICE_ROD_NAME = ChatColor.AQUA + "氷の杖";
    private static final String FIRE_ROD_NAME = ChatColor.RED + "炎の杖";
    private static final String LEGENDARY_BOW_NAME = ChatColor.GOLD + "伝説の弓";
    private static final String LEGENDARY_SWORD_NAME = ChatColor.DARK_PURPLE + "伝説の剣";

    @Override
    public void onEnable() {
        getLogger().info("Oiwaraeru Plugin Enabled");
        getServer().getPluginManager().registerEvents(this, this);
        registerRecipes();
    }

    private void registerRecipes() {
        // 吸血鬼の剣
        ItemStack vampireSword = createVampireSword();
        ShapedRecipe vampireRecipe = new ShapedRecipe(new NamespacedKey(this, "vampire_sword"), vampireSword);
        vampireRecipe.shape("  m", "epe", "isb");
        vampireRecipe.setIngredient('m', Material.ROTTEN_FLESH);
        vampireRecipe.setIngredient('e', Material.ENDER_EYE);
        vampireRecipe.setIngredient('p', Material.STICK);
        vampireRecipe.setIngredient('i', Material.IRON_SWORD);
        vampireRecipe.setIngredient('s', Material.BLAZE_POWDER);
        vampireRecipe.setIngredient('b', Material.BLAZE_POWDER);
        Bukkit.addRecipe(vampireRecipe);

        // 氷の杖
        ItemStack iceRod = createIceRod();
        ShapedRecipe iceRodRecipe = new ShapedRecipe(new NamespacedKey(this, "ice_rod"), iceRod);
        iceRodRecipe.shape("  i", " s ", "   ");
        iceRodRecipe.setIngredient('i', Material.ICE);
        iceRodRecipe.setIngredient('s', Material.SNOW_BLOCK);
        Bukkit.addRecipe(iceRodRecipe);

        // 炎の杖
        ItemStack fireRod = createFireRod();
        ShapedRecipe fireRodRecipe = new ShapedRecipe(new NamespacedKey(this, "fire_rod"), fireRod);
        fireRodRecipe.shape("  s", " m ", "bs ");
        fireRodRecipe.setIngredient('s', Material.BLAZE_POWDER);
        fireRodRecipe.setIngredient('m', Material.MAGMA_BLOCK);
        fireRodRecipe.setIngredient('b', Material.STICK);
        Bukkit.addRecipe(fireRodRecipe);

        // 伝説の弓
        ItemStack legendaryBow = createLegendaryBow();
        ShapedRecipe legendaryBowRecipe = new ShapedRecipe(new NamespacedKey(this, "legendary_bow"), legendaryBow);
        legendaryBowRecipe.shape(" p ", "aya", " f ");
        legendaryBowRecipe.setIngredient('p', Material.PAPER);
        legendaryBowRecipe.setIngredient('a', Material.ARROW);
        legendaryBowRecipe.setIngredient('y', Material.BOW);
        legendaryBowRecipe.setIngredient('f', Material.FLINT_AND_STEEL);
        Bukkit.addRecipe(legendaryBowRecipe);

        // 伝説の剣
        ItemStack legendarySword = createLegendarySword();
        ShapedRecipe legendarySwordRecipe = new ShapedRecipe(new NamespacedKey(this, "legendary_sword"), legendarySword);
        legendarySwordRecipe.shape(" n ", "sss", " s ");
        legendarySwordRecipe.setIngredient('n', Material.NETHERITE_SWORD);
        legendarySwordRecipe.setIngredient('s', Material.GOLD_INGOT);
        Bukkit.addRecipe(legendarySwordRecipe);
    }

    private ItemStack createVampireSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(VAMPIRE_SWORD_NAME);
        meta.setLore(Collections.singletonList(ChatColor.RED + "攻撃ダメージの50%を吸収する"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createIceRod() {
        ItemStack item = new ItemStack(Material.BRINE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ICE_ROD_NAME);
        meta.setLore(Collections.singletonList(ChatColor.BLUE + "ブロックリーチ50m 移動速度低下付与"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createFireRod() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FIRE_ROD_NAME);
        meta.setLore(Collections.singletonList(ChatColor.GOLD + "周囲mobに炎上付与"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createLegendaryBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(LEGENDARY_BOW_NAME);
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "チャージ最大750ダメージ"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createLegendarySword() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(LEGENDARY_SWORD_NAME);
        meta.setLore(Arrays.asList(
            ChatColor.GREEN + "移動速度上昇5",
            ChatColor.GREEN + "攻撃力上昇3",
            ChatColor.GREEN + "ジャンプ力上昇2",
            ChatColor.GREEN + "回復速度上昇5"
        ));
        item.setItemMeta(meta);
        return item;
    }

    // ここから実際の効果処理（イベントハンドラなど）を追加していきます...

}
