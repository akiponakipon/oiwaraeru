package com.oiwaraeru.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.oiwaraeru.OiwaraeruPlugin;

public class ItemListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.DIAMOND) {
            player.sendMessage("ダイヤを使ったね！");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Bukkit.getLogger().info(event.getPlayer().getName() + " がログアウトしました。");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            attacker.sendMessage("攻撃したよ！");
        }
    }
}
