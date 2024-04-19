package fr.hashtek.spigot.breakffa.shop.article;

import org.bukkit.entity.Player;

public interface ShopArticleBuyAction
{

    /**
     * Function called when item is clicked.
     *
     * @param	player	Player who bought the article
     * @param	article	Article
     */
    void execute(
        Player player,
        ShopArticle article
    );

}
