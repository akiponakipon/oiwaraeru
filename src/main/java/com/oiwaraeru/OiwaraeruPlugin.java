package com.oiwaraeru;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.oiwaraeru.listeners.ItemListener;

public class OiwaraeruPlugin extends JavaPlugin {

    private static OiwaraeruPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Oiwaraeru Plugin Enabled!");

        // イベントリスナー登録
        registerListeners();

        // カスタムレシピ登録
        registerRecipes();

        // コマンド登録
        registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("Oiwaraeru Plugin Disabled!");
    }

    public static OiwaraeruPlugin getInstance() {
        return instance;
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ItemListener(), this);
        // 例: Bukkit.getPluginManager().registerEvents(new AnotherListener(), this);
    }

    private void registerRecipes() {
        // TODO: カスタムレシピをここに追加する
    }

    private void registerCommands() {
        // TODO: コマンド登録処理をここに追加する
        // getCommand("mycommand").setExecutor(new MyCommand());
    }
}
