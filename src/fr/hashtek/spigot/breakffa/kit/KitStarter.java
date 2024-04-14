package fr.hashtek.spigot.breakffa.kit;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.PlayerInventory;

public enum KitStarter
{

    /* Items */
    SWORD (
        new HashItem(Material.IRON_SWORD)
            .setUnbreakable(true)
            .addFlag(ItemFlag.HIDE_UNBREAKABLE)
            .addEnchant(Enchantment.DAMAGE_ALL, 1)
            .build(),
        0
    ),

    PICKAXE (
        new HashItem(Material.IRON_PICKAXE)
            .setUnbreakable(true)
            .addFlag(ItemFlag.HIDE_UNBREAKABLE)
            .build(),
        1
    ),

    INSTANT_TNT (
        new HashItem(Material.TNT, 4)
            .setName("Instant TNT")
            .build(),
        2
    ),

    GAPPLES (
        new HashItem(Material.GOLDEN_APPLE, 4)
            .build(),
        3
    ),

    SANDSTONE (
        new HashItem(Material.SANDSTONE, 64, (byte) 2)
            .build(),
        new int[] { 4, 5, 6, 7, 8 }
    ),

    /* Armor */
    CHESTPLATE (
        new HashItem(Material.IRON_CHESTPLATE)
            .setUnbreakable(true)
            .addFlag(ItemFlag.HIDE_UNBREAKABLE)
            .build()
    ),

    LEGGINGS (
        new HashItem(Material.CHAINMAIL_LEGGINGS)
            .setUnbreakable(true)
            .addFlag(ItemFlag.HIDE_UNBREAKABLE)
            .build()
    ),

    BOOTS (
        new HashItem(Material.LEATHER_BOOTS)
            .setUnbreakable(true)
            .addFlag(ItemFlag.HIDE_UNBREAKABLE)
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
