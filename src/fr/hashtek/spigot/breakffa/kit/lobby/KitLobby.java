package fr.hashtek.spigot.breakffa.kit.lobby;

import fr.hashtek.spigot.breakffa.BreakFFA;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitLobby
{

    private final BreakFFA main;


    public KitLobby(BreakFFA main)
    {
        this.main = main;
    }


    /**
     * Gives the kit to a player.
     *
     * @param   player  Player
     */
    public void giveItems(Player player)
    {
        final Inventory playerInventory = player.getInventory();

        for (KitLobbyItems kit : KitLobbyItems.values())
            playerInventory.setItem(kit.getSlotIndex(), kit.getItem().getItemStack());
    }

}
