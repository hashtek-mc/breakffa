package fr.hashtek.spigot.breakffa.kit.kits;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.KitItems;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerSettings;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class KitStarter
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    /**
     * getSlotIndex() and give() functions are empty because
     * they won't be used. Masks are going to carry this.
     */
    public enum Items
        implements KitItems
    {

        /* Items */
        SWORD (
            new HashItem(Material.IRON_SWORD)
                .setName(Component.text(ChatColor.RED + "Epée Basique"))
                .setUnbreakable(true)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .build(),
            'S'
        ),

        PICKAXE (
            new HashItem(Material.IRON_PICKAXE)
                .setName(Component.text(ChatColor.GREEN + "Pioche Basique"))
                .setUnbreakable(true)
                .build(),
            'P'
        ),

        INSTANT_TNT (
            new HashItem(Material.TNT, 4)
                .setName(Component.text(ChatColor.GREEN + "TNT"))
                .build(),
            'T'
        ),

        GOLDEN_APPLES(
            new HashItem(Material.GOLDEN_APPLE, 3)
                .setName(Component.text(ChatColor.GREEN + "Pomme d'Or"))
                .build(),
            'G'
        ),

        BLOCKS(
            new HashItem(Material.SANDSTONE, 64, (byte) 2)
                .setName(Component.text(ChatColor.GREEN + "Blocs"))
                .build(),
            'B'
        ),

        /* Armor */
        CHESTPLATE (
            new HashItem(Material.IRON_CHESTPLATE)
                .setName(Component.text(ChatColor.BLUE + "Plastron en fer"))
                .setUnbreakable(true)
                .build()
        ),

        LEGGINGS (
            new HashItem(Material.CHAINMAIL_LEGGINGS)
                .setName(Component.text(ChatColor.BLUE + "Jambières en maille"))
                .setUnbreakable(true)
                .build()
        ),

        BOOTS (
            new HashItem(Material.LEATHER_BOOTS)
                .setName(Component.text(ChatColor.BLUE + "Bottes en cuir"))
                .setUnbreakable(true)
                .build()
        );


        private final HashItem item;
        private final Character character;


        Items(HashItem item)
        {
            this(item, ' ');
        }

        Items(HashItem item, Character character)
        {
            this.item = item;
            this.character = character;
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
            return -1;
        }

        @Override
        public void give(Player player) {}

        /**
         * @return  Item's assigned char. Primarily used in masks.
         */
        public Character getCharacter()
        {
            return this.character;
        }

        /**
         * @param   item    Item to compare
         * @return  <code>true</code> if both items are equal, otherwise <code>false</code>.
         */
        public boolean equals(ItemStack item)
        {
            final ItemStack i1 = item.clone();
            final ItemStack i2 = this.getItem().getItemStack().clone();

            i2.setAmount(i1.getAmount());

            return i1.equals(i2);
        }

        /**
         * @param   character   Character to search
         * @return  Item assigned to the given character
         */
        public static HashItem getItemFromCharacter(Character character)
        {
            for (Items item : Items.values()) {
                if (item.getCharacter() == character)  {
                    return item.getItem();
                }
            }
            return null;
        }

        /**
         * @param   item    Item
         * @return  Item's assigned character (if any)
         */
        public static char getItemCharacter(ItemStack item)
        {
            if (item == null || item.getType() == Material.AIR) {
                return ' ';
            }

            for (KitStarter.Items starterItem : KitStarter.Items.values()) {
                if (starterItem.equals(item)) {
                    return starterItem.getCharacter();
                }
            }
            return ' ';
        }

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
     * @param   player  Player
     */
    public void giveItems(Player player)
    {
        final PlayerData playerData = MAIN.getGameManager().getPlayerManager(player).getData();
        final PlayerSettings settings = playerData.getSettings();
        final PlayerInventory inventory = player.getInventory();
        final ShopManager shopManager = MAIN.getShopManager();
        final String hotbarLayout = settings.getHotbarLayout();

        final Mask mask = new Mask(inventory);

        for (Items item : Items.values()) {
            if (item.getCharacter() == ' ') {
                continue;
            }

            mask.setItem(item.getCharacter(), item.getItem());
        }

        mask.pattern(hotbarLayout.substring(0, 9));

        mask.apply();

        final char lastCharOfHotbarLayout = hotbarLayout.charAt(hotbarLayout.length() - 1);
        if (lastCharOfHotbarLayout != ' ') {
            inventory.setItemInOffHand(mask.getItem(lastCharOfHotbarLayout).getItemStack());
        }

        this.setArmor(inventory);
        shopManager.giveShop(player);
    }

}
