package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ShopCategorySupport extends ShopCategory
{

    public ShopCategorySupport(BreakFFA main, Player player)
    {
        super(main, player, "SUPPORT", ChatColor.GREEN, (byte) 13, (byte) 5);

        this.loadArticles();
    }

    /**
     * Loads articles.
     */
    @Override
    public void loadArticles()
    {
        List<ShopArticle> articles = Arrays.asList(
            new ShopArticle(
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
                super.getMain()
            ),

            new ShopArticle(
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
                8,
                super.getMain()
            )
        );

        super.addArticles(articles);
    }

}
