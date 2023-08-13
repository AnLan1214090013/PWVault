package cn.pixelwar.pwvault.Vault;

import cn.pixelwar.pwvault.PlayerData.PlayerVaultData;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import cn.pixelwar.pwvault.Utils.ChatColorCast;
import cn.pixelwar.pwvault.Utils.CustomCollectors;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;

public class VaultMenu {

    public static List<List<ItemStack>> icons;

    public void openMainMenu(Player player){
        PlayerVaultData playerVaultData = PlayerVaultDataManager.playerVaultDataMap.get(player.getName());
        int vaultAmount = playerVaultData.getVaultAmount();
        int line = vaultAmount/6;
        if (line>6){
            line = 6;
        }
        if (line<6&&vaultAmount%6>0){
            line++;
        }
        Inventory gui = Bukkit.createInventory(player, line*9, ChatColor.DARK_GRAY+player.getName()+"的个人仓库 "+" (共"+vaultAmount+"个)");
        ItemStack back = getButton(
                Material.BLACK_STAINED_GLASS_PANE,
                ChatColorCast.format("&c&l仓库未解锁"),
                asList(
                        " ",
                        ChatColorCast.format("&7你可以通过&d商店 &b荣耀等级"),
                        ChatColorCast.format("&e打开宝箱&7来&7解锁更多仓库!")
                ),
                false
        );

        for (int i = 0; i<line*9;i++){
            gui.setItem(i, back);
        }

        for (int i = 1; i<=vaultAmount;i++){
            Material icon = Material.ENDER_CHEST;

            if (playerVaultData.getIcon().containsKey(i)){
                icon = playerVaultData.getIcon().get(i);
            }
            ItemStack chest = getButton(
                    icon,
                    ChatColorCast.format("&b&l个人仓库 &f&l#"+i),
                    asList(
                            " ",
                            ChatColorCast.format("&7(左键)打开个人仓库"),
                            ChatColorCast.format("&7(右键)选择仓库图标")
                    ),
                    false
                    );
            gui.setItem(i-1, chest);


        }
        player.openInventory(gui);
        player.playSound(player.getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 0.8f);



    }

    public void openVault(Player player, int index){
        PlayerVaultData playerVaultData = PlayerVaultDataManager.playerVaultDataMap.get(player.getName());
        int rowAmount = playerVaultData.getRowAmount();
        int vaultAmount = playerVaultData.getVaultAmount();
        int line;
        if (vaultAmount>index){
            line = 6;
        }else{
            line = rowAmount%6;
        }
        if (vaultAmount==index && rowAmount%6==0){
            line = 6;
        }
        Inventory gui = Bukkit.createInventory(player, line*9, ChatColor.DARK_GRAY+player.getName()+"仓库 #"+index);
        Map<Integer, ItemStack> thisItems = playerVaultData.getAllItems().get(index);
        if (thisItems!=null){
            thisItems.forEach((key, value) -> {
                gui.setItem(key, value);
            });
        }

        player.openInventory(gui);
        player.playSound(player.getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 0.8f);
    }
    public void openIconMenu(Player player, int page, int vault){
        if (page>icons.size()){
            return;
        }
        Inventory gui = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY+player.getName()+"仓库图标选择 页码"+page+"/"+icons.size());
        int i = 1;
        List<ItemStack> thisPageItems = new ArrayList<>();
        for (List<ItemStack> pageItems : icons){
            if (i==page){
                thisPageItems = pageItems;
                break;
            }
            i++;
        }
        int j=0;
        for (ItemStack item : thisPageItems){
            gui.setItem(j,item);
            j++;
        }
        ItemStack back = getButton(
                Material.BLACK_STAINED_GLASS_PANE,
                " ",
                asList(),
                false
        );
        gui.setItem(53, back);
        gui.setItem(52, back);
        gui.setItem(51, back);
        gui.setItem(50, back);
        gui.setItem(48, back);
        gui.setItem(47, back);
        gui.setItem(46, back);
        gui.setItem(45, back);
        ItemStack next = getButton(
                Material.TIPPED_ARROW,
                ChatColorCast.format("&a&l下一页"),
                asList(),
                false,
                "Potion",
                "minecraft:leaping",
                "page",
                page
        );
        ItemStack last = getButton(
                Material.TIPPED_ARROW,
                ChatColorCast.format("&c&l上一页") ,
                asList(),
                false,
                "Potion",
                "minecraft:healing",
                "page",
                page
        );
        ItemStack close = getButton(
                Material.BARRIER,
                ChatColorCast.format("&c&l返回") ,
                asList(),
                false,
                "",
                "",
                "vault",
                vault
        );
        if (page==1){
            gui.setItem(53,next);
        }
        else if (page==icons.size()){
            gui.setItem(45,last);
        }else{
            gui.setItem(53,next);
            gui.setItem(45,last);
        }
        gui.setItem(49,close);
        player.openInventory(gui);
        player.playSound(player.getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 0.8f);
    }

    public ItemStack getButton(Material material, String name, List<String> lore, boolean glow){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        if (glow) {
            itemMeta.addEnchant(Enchantment.OXYGEN, 1, true);
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);
        return item;
    }

    public void loadICONs(){
        List<ItemStack> items = new ArrayList<>();
        for (Material material : Material.values()){
            if (
                    material.toString().contains("PICKAXE") ||
                    material.toString().contains("AXE") ||
                    material.toString().contains("SWORD") ||
                    material.toString().contains("BOW") ||
                    material.toString().contains("SHIELD") ||
                    material.toString().contains("TRIDENT") ||
                    material.toString().contains("CROSSBOW") ||
                    material.toString().contains("HELMET") ||
                    material.toString().contains("CHESTPLATE") ||
                    material.toString().contains("LEGGINGS") ||
                    material.toString().contains("BOOTS") ||
                    material.toString().contains("BOOK") ||
                    material.toString().contains("GLASS") ||
                    material.toString().contains("WOOL") ||
                    material.toString().contains("COAL") ||
                    material.toString().contains("IRON") ||
                    material.toString().contains("LAPIS") ||
                    material.toString().contains("REDSTONE") ||
                    material.toString().contains("GOLD") ||
                    material.toString().contains("DIAMOND") ||
                    material.toString().contains("EMERALD") ||
                    material.toString().contains("QUARTZ") ||
                    material.toString().contains("AMETHYST") ||
                    material.toString().contains("OAK") ||
                    material.toString().contains("SPRUCE") ||
                    material.toString().contains("BIRCH") ||
                    material.toString().contains("JUNGLE") ||
                    material.toString().contains("ACACIA") ||
                    material.toString().contains("WARPED") ||
                    material.toString().contains("BLACKSTONE")
            ){
                ItemStack item = new ItemStack(material);
                ItemMeta itemMeta = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColorCast.format("&7(点击)选择该图标"));
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                items.add(item);
            }
        }
        CustomCollectors customCollectors = new CustomCollectors();
        List<List<ItemStack>> groupedList = items.stream().collect(CustomCollectors.groupByNumber(45));
        icons = groupedList;
    }


    public ItemStack getButton(Material material, String name, List<String> lore, boolean glow, String nbtPath, String nbt, String nbtPath2, int nbtint){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        if (glow) {
            itemMeta.addEnchant(Enchantment.OXYGEN, 1, true);
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(nbtPath, nbt);
        nbtItem.setInteger(nbtPath2, nbtint);
        item = nbtItem.getItem();
        return item;
    }


}
