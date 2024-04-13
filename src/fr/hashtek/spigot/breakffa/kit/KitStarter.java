package fr.hashtek.spigot.breakffa.kit;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public enum KitStarter
{

    /* Items */
    SWORD (
        new HashItem(Material.IRON_SWORD)
            .setName(ChatColor.BLACK + "dieu")
            .setUnbreakable(true)
            .build(),
        0
    ),

    PICKAXE (
        new HashItem(Material.IRON_PICKAXE)
            .setName(ChatColor.BLUE + "pioche mdr")
            .setUnbreakable(true)
            .build(),
        1
    ),

    INSTA_TNT (
        new HashItem(Material.TNT, 16)
            .setName(ChatColor.RED + "Insta TNT")
            .build(),
        2
    ),

    GAPPLES (
        new HashItem(Material.GOLDEN_APPLE, 8)
            .setName(ChatColor.YELLOW + "Gapples")
            .build(),
        3
    ),

    SANDSTONE (
        new HashItem(Material.SANDSTONE, 64, (byte) 2)
            .setName(ChatColor.YELLOW + "Blocs de sandstone")
            .build(),
        new int[] { 4, 5, 6, 7, 8 }
    ),

    /* Armor */
    HELMET (
        new HashItem(Material.LEATHER_HELMET)
            .build()
    ),

    CHESTPLATE (
        new HashItem(Material.IRON_CHESTPLATE)
            .build()
    ),

    LEGGINGS (
        new HashItem(Material.LEATHER_LEGGINGS)
            .build()
    ),

    BOOTS (
        new HashItem(Material.LEATHER_BOOTS)
            .build()
    );


    private final HashItem item;
    private final int[] slotIndexes;


    KitStarter(HashItem item)
    {
        this(item, -1);
    }

    KitStarter(HashItem item, int slotIndex)
    {
        this(item, new int[] { slotIndex });
    }

    KitStarter(HashItem item, int[] slotIndexes)
    {
        this.item = item;
        this.slotIndexes = slotIndexes;
    }


    private static void setArmor(PlayerInventory playerInventory)
    {
        playerInventory.setHelmet(HELMET.getItem().getItemStack());
        playerInventory.setChestplate(CHESTPLATE.getItem().getItemStack());
        playerInventory.setLeggings(LEGGINGS.getItem().getItemStack());
        playerInventory.setBoots(BOOTS.getItem().getItemStack());
    }

    public static void giveItems(Player player)
    {
        final PlayerInventory playerInventory = player.getInventory();

        for (KitStarter kit : KitStarter.values()) {
            for (int slot : kit.slotIndexes) {
                if (slot == -1)
                    continue;
                playerInventory.setItem(slot, kit.getItem().getItemStack());
            }
        }

        setArmor(playerInventory);
    }


    public HashItem getItem()
    {
        return this.item;
    }

    public int[] getSlotIndexes()
    {
        return this.slotIndexes;
    }

}
