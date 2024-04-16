package fr.hashtek.spigot.breakffa.kit.starter;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class KitStarter
{

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
        inventory.setChestplate(KitStarterItem.CHESTPLATE.getItem().getItemStack());
        inventory.setLeggings(KitStarterItem.LEGGINGS.getItem().getItemStack());
        inventory.setBoots(KitStarterItem.BOOTS.getItem().getItemStack());
    }

    public void giveShop(Player player, PlayerInventory inventory)
    {
        final PlayerData playerData = this.gameManager.getPlayerData(player);
        final PlayerManager playerManager = playerData.getPlayerManager();
        final ShopManager shopManager = playerManager.getShopManager();
        final HashGuiManager guiManager = this.main.getGuiManager();

        final HashItem darkBlueGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 9)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem blueGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 3)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem magentaGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 2)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem whiteGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 0)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem shopItem = shopManager.getShopItem(false);

        final Mask mask = new Mask(inventory);

        mask.setItem('d', darkBlueGlass)
            .setItem('b', blueGlass)
            .setItem('m', magentaGlass)
            .setItem('w', whiteGlass)
            .setItem('s', shopItem);

        mask.pattern(2, "ddbmwmbdd")
            .pattern(3, "dbmwswmbd")
            .pattern(4, "ddbmwmbdd");

        mask.apply();
    }

    /**
     * Gives the kit to a player.
     *
     * @param   player  Player
     */
    public void giveItems(Player player)
    {
        final PlayerInventory playerInventory = player.getInventory();

        for (KitStarterItem starterItem : KitStarterItem.values())
            starterItem.give(player);

        this.setArmor(playerInventory);
        this.giveShop(player, playerInventory);
    }

}
