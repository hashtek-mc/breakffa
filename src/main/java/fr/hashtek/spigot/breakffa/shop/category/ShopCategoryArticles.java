package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticleBuyAction;
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
    public abstract ShopArticle getArticle();

    /**
     * @param   item    Item to compare with
     * @return  Returns true if both items are equal.
     */
    public abstract boolean equals(ItemStack item);

}
