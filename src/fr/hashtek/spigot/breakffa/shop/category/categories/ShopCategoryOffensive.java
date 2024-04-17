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

public class ShopCategoryOffensive extends ShopCategory
{

    public ShopCategoryOffensive(BreakFFA main, Player player)
    {
        super(main, player, "OFFENSIF", ChatColor.RED, (byte) 14, (byte) 1);

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
                new HashItem(Material.IRON_SWORD)
                    .setName(ChatColor.RED + "Epée Renforcée")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Procure une épée renforcée " + ChatColor.YELLOW + "incassable",
                        ChatColor.GRAY + "et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant II" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+8.5)",
                        "",
                        ChatColor.GRAY + "Cette arme est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", elle",
                        ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + "!"
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 2)
                    .setUnbreakable(true)
                    .build(),
                3,
                super.getMain()
            ),

            new ShopArticle(
                new HashItem(Material.DIAMOND_SWORD)
                    .setName(ChatColor.RED + "Epée Brillante")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Procure une épée brillante " + ChatColor.YELLOW + "incassable",
                        ChatColor.GRAY + "en " + ChatColor.AQUA + "diamant" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant II" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+9.5)",
                        "",
                        ChatColor.GRAY + "Cette arme est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", elle",
                        ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + "!"
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 2)
                    .setUnbreakable(true)
                    .build(),
                8,
                super.getMain()
            ),

            new ShopArticle(
                new HashItem(Material.GOLD_SWORD)
                    .setName(ChatColor.RED + "Epée Parfaite")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Procure l'épée " + ChatColor.GOLD + "parfaite" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "incassable",
                        ChatColor.GRAY + "en " + ChatColor.GOLD + "or" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant V" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+10.25)",
                        "",
                        ChatColor.GRAY + "Cette épée " + ChatColor.YELLOW + "double" + ChatColor.GRAY + " vos gains de " + ChatColor.AQUA + "shards",
                        ChatColor.GRAY + "à chaque " + ChatColor.RED + "élimination" + ChatColor.GRAY + "!",
                        "",
                        ChatColor.GRAY + "Cette arme est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", elle",
                        ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + "!"
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 5)
                    .setUnbreakable(true)
                    .build(),
                15,
                super.getMain()
            )
        );

        super.addArticles(articles);
    }
}
