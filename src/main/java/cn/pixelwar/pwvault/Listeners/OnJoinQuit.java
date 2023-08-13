package cn.pixelwar.pwvault.Listeners;

import cn.pixelwar.pwvault.File.YamlStorage;
import cn.pixelwar.pwvault.PWVault;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnJoinQuit implements Listener {

    @EventHandler
    public void OnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerVaultDataManager.playerLoadDataMap.put(player.getName(), false);
        new BukkitRunnable() {
            @Override
            public void run() {
                YamlStorage yamlStorage = new YamlStorage();
                yamlStorage.CheckYamlFile(player);
                yamlStorage.CreatePlayerDataMap(player);
                PlayerVaultDataManager.playerLoadDataMap.put(player.getName(), true);
            }
        }.runTaskAsynchronously(PWVault.getPlugin());

    }

    @EventHandler
    public void OnQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                YamlStorage yamlStorage = new YamlStorage();
                yamlStorage.savePlayerData(player);
                PlayerVaultDataManager.playerVaultDataMap.remove(player.getName());
            }
        }.runTaskAsynchronously(PWVault.getPlugin());
    }


}
