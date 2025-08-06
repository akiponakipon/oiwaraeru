package com.oiwaraeru.listeners;

import com.oiwaraeru.OiwaraeruPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class ItemListener implements Listener {

    private final OiwaraeruPlugin plugin;

    private final Map<UUID, Set<String>> craftedPlayers = new HashMap<>();
    private final Map<UUID, Long> iceRodCooldown = new HashMap<>();
    private final Map<UUID, Long> fireRodCooldown = new HashMap<>();
    private final Map<UUID, Long> legendaryBowChargeStart = new HashMap<>();
    private final Map<UUID, Integer> legendaryBowChargeCount = new HashMap<>();

    public ItemListener(OiwaraeruPlugin plugin) {
        this.plugin = plugin;
    }

    // --- アイテム生成メソッド（全アイテム共通） ---

    public static ItemStack createVampireSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "吸血鬼の剣");
        meta.setLore(Collections.singletonList(ChatColor.RED + "攻撃ダメージの50%を吸収する"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createIceRod() {
        ItemStack item = new ItemStack(Material.BRINE_ROD); // ブリーズロッドとして代用
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "氷の杖");
        meta.setLore(Collections.singletonList(ChatColor.BLUE + "ブロックリーチ50m 移動速度低下付与"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createFireRod() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "炎の杖");
        meta.setLore(Collections.singletonList(ChatColor.GOLD + "周囲mobに炎上付与"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createLegendaryBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "伝説の弓");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "チャージ最大750ダメージ"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createLegendarySword() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "伝説の剣");
        meta.setLore(Arrays.asList(
                ChatColor.GREEN + "移動速度上昇5",
                ChatColor.GREEN + "攻撃力上昇3",
                ChatColor.GREEN + "ジャンプ力上昇2",
                ChatColor.GREEN + "回復速度上昇5"
        ));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createBestFood() {
        ItemStack item = new ItemStack(Material.APPLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "最高の食料");
        meta.setLore(Collections.singletonList(ChatColor.GREEN + "何度でも食べられる特別な食料"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createLegendaryCrossbow() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "伝説のクロスボウ");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "チャージ時間短縮と無限ダメージ"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createHandyman() {
        ItemStack item = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "なんでも屋さん");
        meta.setLore(Collections.singletonList(ChatColor.GREEN + "アイテムを一回だけ取り寄せ可能"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createBrokenEnderPearl() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "壊れたエンダーパール");
        meta.setLore(Collections.singletonList(ChatColor.RED + "使うとランダムテレポート"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createThrowingSponge() {
        ItemStack item = new ItemStack(Material.SPONGE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "投げるスポンジ");
        meta.setLore(Collections.singletonList(ChatColor.GREEN + "矢にスポンジを設置"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createSuperTNT() {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "超強力型TNTo-6型b式");
        meta.setLore(Collections.singletonList(ChatColor.RED + "水中でも爆発"));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createDarkSword() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "暗黒の剣");
        meta.setLore(Arrays.asList(
                ChatColor.DARK_PURPLE + "攻撃命中で攻撃力上昇5秒",
                ChatColor.DARK_PURPLE + "持っている間は暗闇効果"
        ));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createImmobileBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "移動不可の弓");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "矢命中で15秒間移動不可"));
        item.setItemMeta(meta);
        return item;
    }

    // --- イベントハンドラ例: 吸血鬼の剣効果 ---

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player player)) return;

        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem == null || !handItem.hasItemMeta()) return;
        if (!handItem.getItemMeta().hasDisplayName()) return;

        String name = handItem.getItemMeta().getDisplayName();

        if (name.equals(ChatColor.DARK_RED + "吸血鬼の剣")) {
            if (!(e.getEntity() instanceof LivingEntity)) return;

            LivingEntity target = (LivingEntity) e.getEntity();

            // PvP判定（サーバー設定か、プレイヤー同士の場合許可）
            if (target instanceof Player) {
                Player targetPlayer = (Player) target;
                if (!targetPlayer.getWorld().getPVP()) return;
            }

            double damage = e.getDamage();

            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double currentHealth = player.getHealth();

            double healAmount = damage * 0.5;
            double newHealth = Math.min(maxHealth, currentHealth + healAmount);
            player.setHealth(newHealth);

            // 血の赤いパーティクル（攻撃時）
            player.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, player.getLocation().add(0, 1.5, 0), 10, 0.2, 0.2, 0.2, 0.1);
        }
    }

    // --- 他アイテム効果、クールダウン管理、パーティクルなどの処理はここに追記 ---

    // 省略しているけど、氷の杖の半径3の移動速度低下効果、  
    // 炎の杖の半径3の炎上、伝説の弓のチャージとダメージ増加、  
    // 伝説の剣のバフ、暗黒の剣の暗闇と周囲パーティクル、  
    // 壊れたエンダーパールのランダムテレポート、投げるスポンジの処理、  
    // TNTo-6の爆発処理、移動不可の弓のターゲット固定、  
    // なんでも屋さんの1回使い切りアイテム召喚など。  

    // 全て実装が膨大なので別途分割や段階的実装を推奨します。

}
