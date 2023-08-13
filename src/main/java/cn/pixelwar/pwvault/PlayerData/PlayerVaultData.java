package cn.pixelwar.pwvault.PlayerData;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerVaultData {

    private int rowAmount;
    private int vaultAmount;

    //<仓库号，ICON>
    private Map<Integer, Material> icon;



    //<仓库号，大小(以行数计算)>
    private Map<Integer, Integer> vaultSize;

    //<仓库号，物品列表>
    private Map<Integer, Map<Integer, ItemStack>> allItems;

    public Map<Integer, Integer> getVaultSize() {
        return vaultSize;
    }

    public void setVaultSize(Map<Integer, Integer> vaultSize) {
        this.vaultSize = vaultSize;
    }

    public void setRowAmount(int rowAmount) {
        this.rowAmount = rowAmount;
    }

    public void setVaultAmount(int vaultAmount) {
        this.vaultAmount = vaultAmount;
    }

    public void setIcon(Map<Integer, Material> icon) {
        this.icon = icon;
    }

    public void setAllItems(Map<Integer, Map<Integer, ItemStack>> allItems) {
        this.allItems = allItems;
    }

    public int getRowAmount() {
        return rowAmount;
    }

    public int getVaultAmount() {
        return vaultAmount;
    }

    public Map<Integer, Material> getIcon() {
        return icon;
    }

    public Map<Integer, Map<Integer, ItemStack>> getAllItems() {
        return allItems;
    }

    public PlayerVaultData(int rowAmount, Map<Integer, Material> icon, Map<Integer, Map<Integer, ItemStack>> allItems) {
        this.rowAmount = rowAmount;
        this.vaultAmount = rowAmount/6;
        this.vaultSize = new ConcurrentHashMap<>();
        for (int i = 1; i<=vaultAmount;i++){
            this.vaultSize.put(i,6);
        }
        int yu = rowAmount%6;
        if (yu>0){
            this.vaultAmount++;
            this.vaultSize.put(this.vaultAmount,yu);
        }
        this.icon = icon;
        this.allItems = allItems;
    }
}
