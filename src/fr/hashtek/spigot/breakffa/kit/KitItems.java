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
    public abstract HashItem getItem();

    /**
     * @return  Item's slot index
     */
    public abstract int getSlotIndex();

    /**
     * Gives the item to a player.
     *
     * @param   player    Player
     */
    public abstract void give(Player player);

}
