package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.inventory.ItemStack;

/**
 * Interface for articles stored in a shop category.
 */
public interface ShopCategoryArticles
{

    /**
     * @return  Article
     */
    public abstract HashItem getArticle();

    /**
     * @return  Article price
     */
    public abstract int getPrice();

    /**
     * @param   item    Item to compare with
     * @return  Returns true if both items are equal.
     */
    public abstract boolean equals(ItemStack item);

}
