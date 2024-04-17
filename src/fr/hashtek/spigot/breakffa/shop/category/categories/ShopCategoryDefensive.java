package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ShopCategoryDefensive extends ShopCategory
{

    /**
     * Creates a new instance of ShopCategoryDefensive.
     *
     * @param   main    BreakFFA main
     * @param   player  Player
     */
    public ShopCategoryDefensive(BreakFFA main, Player player)
    {
        super(main, player, "DEFENSIF", ChatColor.BLUE, (byte) 11, (byte) 3);

        this.loadArticles();
    }

    /**
     * Loads articles.
     */
    @Override
    public void loadArticles()
    {
        List<ShopArticle> articles = Arrays.asList(

        );

        super.addArticles(articles);
    }

}
