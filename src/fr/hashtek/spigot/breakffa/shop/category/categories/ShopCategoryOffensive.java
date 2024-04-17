package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ShopCategoryOffensive extends ShopCategory
{

    private final BreakFFA main;

    private List<ShopArticle> articles = new ArrayList<ShopArticle>();


    public ShopCategoryOffensive(
        PlayerManager playerManager
    ) {
        super(playerManager, "OFFENSIF", ChatColor.RED, (byte) 14, (byte) 1);

        this.main = playerManager.getPlayerData().getMain();

        this.loadArticles();

        super.addArticles(this.articles);
    }


    private void loadArticles()
    {
        ShopArticle one = new ShopArticle(
            new HashItem(Material.IRON_SWORD)
                .addLore("sex {price}")
                .build(),
            15,
            this.main
        );

        ShopArticle two = new ShopArticle(
            new HashItem(Material.REDSTONE)
                .addLore("sex45 {price}")
                .build(),
            1500,
            this.main
        );

        ShopArticle three = new ShopArticle(
            new HashItem(Material.DIRT)
                .addLore("xxdxddxdd {price}")
                .build(),
            69,
            this.main
        );

        for (int k = 0; k < 27; k++) {
            this.articles.add(one);
            this.articles.add(two);
            this.articles.add(three);
        }
    }
}
