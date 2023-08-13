package cn.pixelwar.pwvault.PlayerData;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerVaultDataManager {


    public static Map<String, Boolean> playerLoadDataMap = new ConcurrentHashMap<>();
    public static Map<String, PlayerVaultData> playerVaultDataMap = new ConcurrentHashMap<>();

    public void setRow(Player player, int row){
        if (row>324){
            row =324;
        }
        playerVaultDataMap.get(player.getName()).setRowAmount(row);
        int vaultAmount = row/6;
        Map<Integer, Integer> vaultSize = new ConcurrentHashMap<>();
        for (int i = 1; i<=vaultAmount;i++){
            vaultSize.put(i,6);
        }
        int yu = row%6;
        if (yu>0){
            vaultAmount++;
            vaultSize.put(vaultAmount,yu);
        }
        playerVaultDataMap.get(player.getName()).setVaultAmount(vaultAmount);
        playerVaultDataMap.get(player.getName()).setVaultSize(vaultSize);
    }

    public int getRow(Player player){
        return playerVaultDataMap.get(player.getName()).getRowAmount();
    }

    public void setICON(Player player, int vaultIndex, Material icon){
        Map<Integer ,Material> m = playerVaultDataMap.get(player.getName()).getIcon();
        m.put(vaultIndex, icon);
        playerVaultDataMap.get(player.getName()).setIcon(m);
    }


}
