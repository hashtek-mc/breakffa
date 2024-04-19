package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticleBuyAction;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShopCategorySupport extends ShopCategory
{

    public enum Articles implements ShopCategoryArticles
    {

        GOLD_PACKAGE (
            new HashItem(Material.GOLDEN_APPLE)
                .setName(ChatColor.GREEN + "Colis d'Or")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Obtenez 3 " + ChatColor.GOLD + "pommes d'or" + ChatColor.GRAY + " supplémentaires",
                    ChatColor.GRAY + "instantanément, augmentant vos " + ChatColor.GREEN + "chances",
                    ChatColor.GRAY + "de " + ChatColor.DARK_GREEN + "survie" + ChatColor.GRAY + ", jusqu'à votre " + ChatColor.DARK_RED + "mort"
                ))
                .build(),
            3,
            (Player player, ShopArticle a) -> {
                player.getInventory().addItem(KitStarter.Items.GOLDEN_APPLES.getItem().getItemStack());
            }
        ),

        BASEBALL_BAT (
            new HashItem(Material.STONE_SWORD)
                .setName(ChatColor.GREEN + "Batte de Baseball")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Mêlée permettant de " + ChatColor.AQUA + "pousser",
                    ChatColor.GRAY + "une " + ChatColor.RED + "cible" + ChatColor.GRAY + " quelconque à plus de",
                    ChatColor.YELLOW + "5 blocks " + ChatColor.GRAY + "en un seul coup.",
                    "",
                    ChatColor.GRAY + "Cependant, cette batte se " + ChatColor.DARK_RED + "casse",
                    ChatColor.GRAY + "après " + ChatColor.YELLOW + "un seul" + ChatColor.GRAY + " coup!",
                    "",
                    ChatColor.GRAY + "Disparaît également à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + ",",
                    ChatColor.GRAY + "en cas de non utilisation."
                ))
                .addEnchant(Enchantment.KNOCKBACK, 5)
                .setDurability((short) 0)
                .build(),
            8
        );

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
        "SUPPORT",
        ChatColor.GREEN,
        (byte) 13,
        (byte) 5
    );


    /**
     * Creates a new instance of ShopCategorySupport.
     *
     * @param   main    BreakFFA main
     * @param   player  Player
     */
    public ShopCategorySupport(BreakFFA main, Player player)
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
                    article.getBuyAction(),
                    super.getMain()
                )
            );
    }

}
