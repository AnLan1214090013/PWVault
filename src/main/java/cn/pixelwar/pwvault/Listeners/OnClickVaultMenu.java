package cn.pixelwar.pwvault.Listeners;

import cn.pixelwar.pwvault.PlayerData.PlayerVaultData;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import cn.pixelwar.pwvault.Vault.VaultMenu;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

public class OnClickVaultMenu  implements Listener {

    @EventHandler
    public void OnClickVaultMenu(InventoryClickEvent event){


        if (event.getClickedInventory() == null){
            return;
        }
        if (event.getView().getTitle().contains("个人仓库")) {
            Inventory viewInventory = event.getView().getTopInventory();
            if (!(event.getClickedInventory().equals(viewInventory))) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            player.playSound(player.getEyeLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 2.0f);
            int vaults = PlayerVaultDataManager.playerVaultDataMap.get(player.getName()).getVaultAmount();
            int slot = event.getSlot();
            if (slot>vaults-1){
                return;
            }
            VaultMenu vaultMenu = new VaultMenu();
            if (event.getClick().equals(ClickType.LEFT)){
                vaultMenu.openVault(player, slot+1);
            }
            if (event.getClick().equals(ClickType.RIGHT)){
                vaultMenu.openIconMenu(player,1,slot+1);
            }



        }
        if (event.getView().getTitle().contains("仓库图标选择 页码")){
            Inventory viewInventory = event.getView().getTopInventory();
            if (!(event.getClickedInventory().equals(viewInventory))) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            player.playSound(player.getEyeLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 2.0f);
            int slot = event.getSlot();
            if (slot>=45){
                if (!(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) && slot==45 ){
                    VaultMenu vaultMenu = new VaultMenu();
                    NBTItem nbtItem = new NBTItem(event.getCurrentItem());
                    NBTItem nbtItem2 = new NBTItem(viewInventory.getItem(49));
                    int page = nbtItem.getInteger("page");
                    int vault = nbtItem2.getInteger("vault");
                    player.closeInventory();
                    vaultMenu.openIconMenu(player, page-1,vault);
                    return;
                }
                if (!(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) && slot==53 ){
                    VaultMenu vaultMenu = new VaultMenu();
                    NBTItem nbtItem = new NBTItem(event.getCurrentItem());
                    NBTItem nbtItem2 = new NBTItem(viewInventory.getItem(49));
                    int vault = nbtItem2.getInteger("vault");
                    int page = nbtItem.getInteger("page");
                    player.closeInventory();
                    vaultMenu.openIconMenu(player, page+1,vault);
                    return;
                }
                if (!(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) && slot==49 ){
                    VaultMenu vaultMenu = new VaultMenu();
                    player.closeInventory();
                    vaultMenu.openMainMenu(player);
                    return;
                }



            }
            else{
                Material chose = event.getCurrentItem().getType();
                NBTItem nbtItem = new NBTItem(viewInventory.getItem(49));
                int vault = nbtItem.getInteger("vault");
                PlayerVaultDataManager playerVaultDataManager = new PlayerVaultDataManager();
                playerVaultDataManager.setICON(player, vault, chose);

                player.closeInventory();
                VaultMenu vaultMenu = new VaultMenu();
                vaultMenu.openMainMenu(player);
            }
        }

    }

}
