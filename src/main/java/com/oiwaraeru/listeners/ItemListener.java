package com.oiwaraeru.listeners;

import com.oiwaraeru.OiwaraeruPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemListener implements Listener {

    private final OiwaraeruPlugin plugin;

    public ItemListener(OiwaraeruPlugin plugin) {
        this.plugin = plugin;
    }

    // 吸血鬼の剣のダメージ吸収効果
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (handItem == null || !handItem.hasItemMeta()) return;
        if (!handItem.getItemMeta().hasDisplayName()) return;

        String name = handItem.getItemMeta().getDisplayName();

        if (name.equals(ChatColor.DARK_RED + "吸血鬼の剣")) {
            double damage = e.getDamage();
            // 対象が敵またはPvP可能プレイヤーなら
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) e.getEntity();

                // 自分の最大体力
                double maxHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
                double currentHealth = player.getHealth();

                double healAmount = damage * 0.5;
                double newHealth = Math.min(maxHealth, currentHealth + healAmount);
                player.setHealth(newHealth);

                // 攻撃時に剣の先端に赤いパーティクル（血っぽい）を表示
                player.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, player.getLocation().add(0, 1.5, 0), 10, 0.2, 0.2, 0.2, 0.1);
            }
        }
    }

    // クラフト時の処理やその他の効果はplugin側で登録予定

}
