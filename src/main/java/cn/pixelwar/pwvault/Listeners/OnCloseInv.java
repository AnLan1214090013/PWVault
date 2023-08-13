package cn.pixelwar.pwvault.Listeners;

import cn.pixelwar.pwvault.PWVault;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultData;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import cn.pixelwar.pwvault.Utils.SaveInvToVault;
import cn.pixelwar.pwvault.Vault.VaultMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnCloseInv implements Listener {

    @EventHandler
    public void OnCloseInv(InventoryCloseEvent event) {
        if (event.getView().getTitle().contains("仓库 #")) {
//            Inventory viewInventory = event.getView().getTopInventory();
//            Player player = (Player) event.getPlayer();
//            int vaultNum = Integer.valueOf(event.getView().getTitle().split("#")[1]);
//            Map<Integer, ItemStack> thisItems = new ConcurrentHashMap<>();
//            for (int i = 0 ; i < viewInventory.getSize();i++){
//                ItemStack itemStack = viewInventory.getItem(i);
//                if (itemStack!=null){
//                    thisItems.put(i, itemStack);
//                }
//            }
//            Map<Integer, Map<Integer, ItemStack>> allItems = PlayerVaultDataManager.playerVaultDataMap.get(player.getName()).getAllItems();
//            allItems.put(vaultNum, thisItems);
//            PlayerVaultDataManager.playerVaultDataMap.get(player.getName()).setAllItems(allItems);
            Player player = (Player) event.getPlayer();
            SaveInvToVault.saveItemInInv(player, event.getView());
            VaultMenu vaultMenu = new VaultMenu();
            new BukkitRunnable() {
                @Override
                public void run() {
                    vaultMenu.openMainMenu(player);
                }
            }.runTaskLater(PWVault.getPlugin(),  3l);

        }
    }

}
