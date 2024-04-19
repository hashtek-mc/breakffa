package fr.hashtek.spigot.breakffa.kit.kits;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.KitItems;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class KitStarter
{

    public enum Items implements KitItems
    {

        /* Items */
        SWORD (
            new HashItem(Material.IRON_SWORD)
                .setName(ChatColor.RED + "Epée Basique" + ChatColor.DARK_GRAY + " (+7.25)")
                .setUnbreakable(true)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .build(),
            0
        ),

        PICKAXE (
            new HashItem(Material.IRON_PICKAXE)
                .setName(ChatColor.GREEN + "Pioche Basique" + ChatColor.DARK_GRAY + " (+4)")
                .setUnbreakable(true)
                .build(),
            1
        ),

        INSTANT_TNT (
            new HashItem(Material.TNT, 4)
                .setName(ChatColor.RED + "TNT")
                .build(),
            2
        ),

        GOLDEN_APPLES(
            new HashItem(Material.GOLDEN_APPLE, 3)
                .setName(ChatColor.GREEN + "Pomme d'Or")
                .build(),
            3
        ),

        SANDSTONE (
            new HashItem(Material.SANDSTONE, 64, (byte) 2)
                .setName(ChatColor.GREEN + "Blocs")
                .build(),
            4
        ),

        /* Armor */
        CHESTPLATE (
            new HashItem(Material.IRON_CHESTPLATE)
                .setName(ChatColor.BLUE + "Plastron en fer")
                .setUnbreakable(true)
                .build()
        ),

        LEGGINGS (
            new HashItem(Material.CHAINMAIL_LEGGINGS)
                .setName(ChatColor.BLUE + "Jambières en maille")
                .setUnbreakable(true)
                .build()
        ),

        BOOTS (
            new HashItem(Material.LEATHER_BOOTS)
                .setName(ChatColor.BLUE + "Bottes en cuir")
                .setUnbreakable(true)
                .build()
        );


        private final HashItem item;
        private final int slotIndex;


        Items(HashItem item)
        {
            this(item, -1);
        }

        Items(HashItem item, int slotIndex)
        {
            this.item = item;
            this.slotIndex = slotIndex;
        }


        @Override
        public HashItem getItem()
        {
            return this.item
                .setFlags(Arrays.asList(
                    ItemFlag.HIDE_UNBREAKABLE,
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS
                ))
                .build();
        }

        @Override
        public int getSlotIndex()
        {
            return this.slotIndex;
        }

        @Override
        public void give(Player player)
        {
            if (this.slotIndex == -1)
                return;

            player.getInventory().setItem(this.slotIndex, this.getItem().getItemStack());
        }

    }

    private final BreakFFA main;
    private final GameManager gameManager;


    /**
     * Creates a new instance of KitStarter.
     *
     * @param   main    BreakFFA main
     */
    public KitStarter(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }

    /**
     * Sets player's armor.
     *
     * @param   inventory     Player's inventory
     */
    private void setArmor(PlayerInventory inventory)
    {
        inventory.setChestplate(Items.CHESTPLATE.getItem().getItemStack());
        inventory.setLeggings(Items.LEGGINGS.getItem().getItemStack());
        inventory.setBoots(Items.BOOTS.getItem().getItemStack());
    }

    /**
     * Gives the kit to a player.
     *
     * @param   playerData  Player's data
     */
    public void giveItems(PlayerData playerData)
    {
        final Player player = playerData.getPlayer();
        final PlayerInventory playerInventory = player.getInventory();
        final ShopManager shopManager = this.main.getShopManager();

        for (Items item : Items.values())
            item.give(player);

        this.setArmor(playerInventory);
        shopManager.giveShop(playerData);
    }

}
