package fr.hashtek.spigot.breakffa.kit.starter;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.PlayerInventory;

public enum KitStarterItems
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
            .build(),
        2
    ),

    GAPPLES (
        new HashItem(Material.GOLDEN_APPLE, 3)
            .build(),
        3
    ),

    SANDSTONE (
        new HashItem(Material.SANDSTONE, 64, (byte) 2)
            .build(),
        4
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


    /**
     * Creates a new instance of KitStarter.
     * (without slot index)
     *
     * @param   item            Item
     */
    KitStarterItems(HashItem item)
    {
        this(item, -1);
    }

    /**
     * Creates a new instance of KitStarter.
     * (with only one slot index)
     *
     * @param   item        Item
     * @param   slotIndex   Slot index
     */
    KitStarterItems(HashItem item, int slotIndex)
    {
        this(item, new int[] { slotIndex });
    }

    /**
     * Creates a new instance of KitStarter.
     *
     * @param   item            Item
     * @param   slotIndexes     Slot indexes
     */
    KitStarterItems(HashItem item, int[] slotIndexes)
    {
        this.item = item;
        this.slotIndexes = slotIndexes;
    }


    public void give(Player player)
    {
        final PlayerInventory inventory = player.getInventory();

        for (int slot : this.slotIndexes) {
            if (slot == -1)
                continue;

            inventory.setItem(slot, this.getItem().getItemStack());
        }
    }


    /**
     * @return  Item
     */
    public HashItem getItem()
    {
        return this.item;
    }

    /**
     * @return  Slot indexes
     */
    public int[] getSlotIndexes()
    {
        return this.slotIndexes;
    }

}
