package fr.hashtek.spigot.breakffa.kit;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.entity.Player;

/**
 * Interface for items stored in kits.
 */
public interface KitItems
{

    /**
     * @return  Item
     */
    HashItem getItem();

    /**
     * @return  Item's slot index
     */
    int getSlotIndex();

    /**
     * Gives the item to a player.
     *
     * @param   player    Player
     */
    void give(Player player);

}
