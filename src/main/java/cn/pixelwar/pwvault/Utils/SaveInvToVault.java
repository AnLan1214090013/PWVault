package cn.pixelwar.pwvault.Utils;

import cn.pixelwar.pwvault.PWVault;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pyf
 * @description
 */

public class SaveInvToVault {

    public static void saveItemInInv(Player player, InventoryView inventoryView) {
        Inventory viewInventory = inventoryView.getTopInventory();
        int vaultNum = Integer.valueOf(inventoryView.getTitle().split("#")[1]);
        Map<Integer, ItemStack> thisItems = new ConcurrentHashMap<>();
        for (int i = 0; i < viewInventory.getSize(); i++) {
            ItemStack itemStack = viewInventory.getItem(i);
            if (itemStack != null) {
                thisItems.put(i, itemStack);
            }
        }
        Map<Integer, Map<Integer, ItemStack>> allItems = PlayerVaultDataManager.playerVaultDataMap.get(player.getName()).getAllItems();
        allItems.put(vaultNum, thisItems);
        PlayerVaultDataManager.playerVaultDataMap.get(player.getName()).setAllItems(allItems);
//        PWVault.getPlugin().getLogger().info("玩家仓库数据保存成功: " + player.getName());
    }

}
