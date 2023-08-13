package cn.pixelwar.pwvault;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import cn.pixelwar.pwvault.File.YamlStorage;
import cn.pixelwar.pwvault.Listeners.OnClickEnderChest;
import cn.pixelwar.pwvault.Listeners.OnClickVaultMenu;
import cn.pixelwar.pwvault.Listeners.OnCloseInv;
import cn.pixelwar.pwvault.Listeners.OnJoinQuit;
import cn.pixelwar.pwvault.Utils.SaveInvToVault;
import cn.pixelwar.pwvault.Vault.VaultMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class PWVault extends JavaPlugin {
    static PWVault instance;
    SkriptAddon addon;
    private static Plugin plugin;
    @Override
    public void onEnable() {
        this.addon = Skript.registerAddon(this);
        instance = this;
        plugin = this;
        getServer().getPluginManager().registerEvents(new OnClickEnderChest(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new OnJoinQuit(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new OnClickVaultMenu(), (Plugin)this);
        getServer().getPluginManager().registerEvents(new OnCloseInv(), (Plugin)this);
        loadIcons();
        try {
            addon.loadClasses("cn.pixelwar.pwvault");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[PixelWarSKAddon-Vault] has been enabled!");
        setupTimer();
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }
    public static PWVault getInstance() {
        return instance;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        YamlStorage yamlStorage = new YamlStorage();
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {

            //万一玩家仓库是开着的
            if (player.getOpenInventory()!=null){
                if (player.getOpenInventory().getTopInventory()!=null){
                    if (player.getOpenInventory().getTitle().contains("仓库 #")){
                        SaveInvToVault.saveItemInInv(player, player.getOpenInventory());
                    }
                }
            }
            yamlStorage.savePlayerData(player);
        }
    }

    public void loadIcons(){
        VaultMenu vaultMenu = new VaultMenu();
        vaultMenu.loadICONs();
    }

    public void setupTimer(){
        YamlStorage yamlStorage = new YamlStorage();
        yamlStorage.savePlayerDataTimer();
    }

}
