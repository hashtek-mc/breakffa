package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticleBuyAction;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopCategoryDefensive extends ShopCategory
{

    public enum Articles implements ShopCategoryArticles
    {

        ;

        private final HashItem article;
        private final int price;
        private final ShopArticleBuyAction buyAction;


        Articles(HashItem article, int price)
        {
            this(article, price, null);
        }

        Articles(HashItem article, int price, ShopArticleBuyAction buyAction)
        {
            this.article = article;
            this.price = price;
            this.buyAction = buyAction;
        }


        @Override
        public HashItem getArticle()
        {
            return this.article;
        }

        @Override
        public int getPrice()
        {
            return this.price;
        }

        @Override
        public ShopArticleBuyAction getBuyAction()
        {
            return this.buyAction;
        }

        @Override
        public boolean equals(ItemStack item)
        {
            final ItemStack i = new HashItem(item)
                .clearFlags()
                .build()
                .getItemStack();

            final ItemStack article = new HashItem(this.article)
                .clearLore()
                .clearFlags()
                .build()
                .getItemStack();

            return i.equals(article);
        }

    }

    private static final ShopCategoryAttributes attributes = new ShopCategoryAttributes(
        "DEFENSIF",
        ChatColor.BLUE,
        (byte) 11,
        (byte) 3
    );


    /**
     * Creates a new instance of ShopCategoryDefensive.
     *
     * @param   main    BreakFFA main
     * @param   player  Player
     */
    public ShopCategoryDefensive(BreakFFA main, Player player)
    {
        super(main, player, attributes);

        this.loadArticles();
    }

    /**
     * Loads articles.
     */
    @Override
    public void loadArticles()
    {
        for (Articles article : Articles.values())
            super.addArticle(
                new ShopArticle(
                    article.getArticle(),
                    article.getPrice(),
                    super.getMain()
                )
            );
    }

}
