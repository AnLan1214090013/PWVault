package cn.pixelwar.pwvault.File;

import cn.pixelwar.pwvault.PlayerData.PlayerVaultData;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class YamlStorage {

    private final FileConfiguration config = new YamlConfiguration();
    
    public void CheckYamlFile(Player player) {

        String playerName = player.getName();
        boolean isExist = true;

        File dataFolder = new File("plugins/PWVault/Players");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File dataFile = new File("plugins/PWVault/Players/" + playerName + ".yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                isExist = false;
            } catch (IOException ex) {}
        }
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {

        }
        config.set("Name", player.getName());
        if (!(isExist)){
            config.set("rowAmount", 1);
        }
        try{
        config.save(dataFile);}catch (IOException ex){}
    }

    public void CreatePlayerDataMap(Player player) {
        File dataFile = new File("plugins/PWVault/Players/" + player.getName() + ".yml");

        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {
        }
        int rowAmount = 1;
        if (config.contains("rowAmount")){
            rowAmount = config.getInt("rowAmount");
        }

        Map<Integer, Material> icon = new ConcurrentHashMap<>();

        ConfigurationSection icons = config.getConfigurationSection("icons");
        if (icons!=null) {
            for (String vaultNum : icons.getKeys(false)) {
                Material ic = Material.valueOf(config.getString("icons." + vaultNum));
                icon.put(Integer.valueOf(vaultNum), ic);
            }
        }

        Map<Integer, Map<Integer, ItemStack>> allItems = new ConcurrentHashMap<>();
        ConfigurationSection all = config.getConfigurationSection("allItems");
        if (all!=null) {
            for (String vaultNum : all.getKeys(false)) {
                ConfigurationSection pageItems = config.getConfigurationSection("allItems." + vaultNum);
                Map<Integer, ItemStack> thispage = new ConcurrentHashMap<>();
                for (String slotNum : pageItems.getKeys(false)) {
                    ItemStack itemStack = config.getItemStack("allItems." + vaultNum + "." + slotNum);
                    thispage.put(Integer.valueOf(slotNum), itemStack);
                }
                allItems.put(Integer.valueOf(vaultNum), thispage);
            }
        }

        PlayerVaultData playerVaultData = new PlayerVaultData(rowAmount,icon,allItems);
        PlayerVaultDataManager.playerVaultDataMap.put(player.getName(), playerVaultData);
    }

    public void savePlayerData(Player player){
        File dataFolder = new File("plugins/PWVault/Players");
        File dataFile = new File("plugins/PWVault/Players/" + player.getName() + ".yml");
        try (InputStreamReader Config = new InputStreamReader(new FileInputStream(dataFile), "UTF-8")) {
            config.load(Config);
        } catch (IOException | InvalidConfigurationException ex) {}
        config.set("icons", null);
        config.set("allItems", null);
        PlayerVaultData playerVaultData = PlayerVaultDataManager.playerVaultDataMap.get(player.getName());

        int rowAmount = playerVaultData.getRowAmount();
        config.set("rowAmount", rowAmount);

        Map<Integer, Material> icon = playerVaultData.getIcon();
        icon.forEach((key, value) -> {
            config.set("icons."+key, value.toString());
        });

        Map<Integer, Map<Integer, ItemStack>> allItems = playerVaultData.getAllItems();
        allItems.forEach((vaultNum, pageItems) -> {
            pageItems.forEach((slotNum, item) -> {
                config.set("allItems."+vaultNum+"."+slotNum, item);
            });
        });

//        allItems:
//            1:
//                0:
//                    WOODEN_PICKAXE
//                1:
//                    STICK
//            2:
//                0:
//                    WOODEN_PICKAXE
//                1:
//                    STICK
        try{
            config.save(dataFile);}catch (IOException ex){
            System.out.println("玩家"+player.getName()+"的等级信息保存出错");
        }
    }
}
