package fr.hashtek.spigot.breakffa.kit.starter;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
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
        inventory.setChestplate(KitStarterItems.CHESTPLATE.getItem().getItemStack());
        inventory.setLeggings(KitStarterItems.LEGGINGS.getItem().getItemStack());
        inventory.setBoots(KitStarterItems.BOOTS.getItem().getItemStack());
    }

    /**
     * Gives the kit to a player.
     *
     * @param   player  Player
     */
    public void giveItems(Player player)
    {
        final ShopManager shopManager = this.main.getShopManager();
        final PlayerData playerData = this.gameManager.getPlayerData(player);
        final PlayerInventory playerInventory = player.getInventory();

        for (KitStarterItems starterItem : KitStarterItems.values())
            starterItem.give(player);

        this.setArmor(playerInventory);
        shopManager.giveShop(playerData);
    }

}
