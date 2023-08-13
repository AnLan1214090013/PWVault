package cn.pixelwar.pwvault.Listeners;

import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import cn.pixelwar.pwvault.Utils.ChatColorCast;
import cn.pixelwar.pwvault.Vault.VaultMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnClickEnderChest implements Listener {

    @EventHandler
    public void OnClickEnderChest(PlayerInteractEvent event){
        if (event.getClickedBlock()==null){
            return;
        }
        if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST)){
            if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
                return;
            }
            event.setCancelled(true);
            if (PlayerVaultDataManager.playerLoadDataMap.containsKey(event.getPlayer().getName())){
                if (PlayerVaultDataManager.playerLoadDataMap.get(event.getPlayer().getName())==true){
                    VaultMenu vaultMenu = new VaultMenu();
                    vaultMenu.openMainMenu(event.getPlayer());
                    return;
                }
            }
            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f,0.1f);
            event.getPlayer().sendMessage(ChatColorCast.format("&c▸ 你的仓库数据还在加载中，请稍后再尝试打开仓库..."));
        }
    }

}
