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
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class KitStarter
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    /**
     * getSlotIndex() and give() functions are empty because
     * they won't be used. Masks are going to carry this.
     */
    public enum Items implements KitItems
    {

        /* Items */
        SWORD (
            new HashItem(Material.IRON_SWORD)
                .setName(Component.text(ChatColor.RED + "Epée Basique"))
                .setUnbreakable(true)
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .build()
        ),

        PICKAXE (
            new HashItem(Material.IRON_PICKAXE)
                .setName(Component.text(ChatColor.GREEN + "Pioche Basique"))
                .setUnbreakable(true)
                .build()
        ),

        INSTANT_TNT (
            new HashItem(Material.TNT, 4)
                .setName(Component.text(ChatColor.GREEN + "TNT"))
                .build()
        ),

        GOLDEN_APPLES(
            new HashItem(Material.GOLDEN_APPLE, 3)
                .setName(Component.text(ChatColor.GREEN + "Pomme d'Or"))
                .build()
        ),

        BLOCKS(
            new HashItem(Material.SANDSTONE, 64, (byte) 2)
                .setName(Component.text(ChatColor.GREEN + "Blocs"))
                .build()
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


        Items(HashItem item)
        {
            this(item, -1);
        }

        Items(HashItem item, int slotIndex)
        {
            this.item = item;
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
        final PlayerSettings settings = playerData.getPlayerSettings();
        final PlayerInventory inventory = player.getInventory();
        final ShopManager shopManager = MAIN.getShopManager();

        final Mask mask = new Mask(inventory);

        mask.setItem('s', Items.SWORD.getItem())
            .setItem('p', Items.PICKAXE.getItem())
            .setItem('t', Items.INSTANT_TNT.getItem())
            .setItem('g', Items.GOLDEN_APPLES.getItem())
            .setItem('b', Items.BLOCKS.getItem());

        mask.pattern(settings.getHotbarLayout());

        mask.apply();

        this.setArmor(inventory);
        shopManager.giveShop(player);
    }

}
