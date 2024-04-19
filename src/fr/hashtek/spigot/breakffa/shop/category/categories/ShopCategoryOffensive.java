package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
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

public class ShopCategoryOffensive extends ShopCategory
{

    public enum Articles implements ShopCategoryArticles
    {

        CHARMED_SWORD (
            new HashItem(Material.IRON_SWORD)
                .setName(ChatColor.RED + "Epée Charmée")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Procure une épée " + ChatColor.LIGHT_PURPLE + "charmée" + ChatColor.GRAY + " par les",
                    ChatColor.GRAY + "forces de la " + ChatColor.AQUA + "célérité" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée",
                    ChatColor.GRAY + "avec " + ChatColor.DARK_AQUA + "Tranchant I" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+7.25)",
                    "",
                    ChatColor.GRAY + "Chaque élimination vous " + ChatColor.YELLOW + "imprègne" + ChatColor.GRAY + " avec l'effet",
                    ChatColor.AQUA + "Vitesse I" + ChatColor.GRAY + " pendant " + ChatColor.YELLOW + "5 secondes" + ChatColor.GRAY + "."
                ))
                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                .setUnbreakable(true)
                .build(),
            3
        ),

        CLAYMORE (
            new HashItem(Material.DIAMOND_SWORD)
                .setName(ChatColor.RED + "Claymore")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Procure une " + ChatColor.RED + "Claymore" + ChatColor.GRAY + " en " + ChatColor.AQUA + "diamant" + ChatColor.GRAY + ",",
                        ChatColor.GRAY + "une épée lourde. " + ChatColor.DARK_GRAY + "(+7)",
                        "",
                        ChatColor.GRAY + "Quand vous êtes " + ChatColor.DARK_GRAY + "immobile" + ChatColor.GRAY + ", cette arme",
                        ChatColor.GRAY + "inflige " + ChatColor.RED + "+2 dégâts" + ChatColor.GRAY + " supplémentaires.",
                        "",
                        ChatColor.GRAY + "Cette arme est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", elle",
                        ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + " !"
                    ))
                .build(),
            4
        ),

        REINFORCED_SWORD (
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
            3
        ),

        SHINY_SWORD (
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
            8
        ),

        PERFECT_SWORD (
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
            15
        ),

        HASH_ACTIVE (
            new HashItem(Material.IRON_AXE)
                .setName(ChatColor.RED + "Hach-active")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Obtenez la " + ChatColor.RED + "Hach-active" + ChatColor.GRAY + ", une arme au",
                    ChatColor.GRAY + "fonctionnement " + ChatColor.BLUE + "complexe" + ChatColor.GRAY + ".",
                    "",
                    ChatColor.GRAY + "Elle possède " + ChatColor.YELLOW + "deux modes" + ChatColor.GRAY + " :",
                    ChatColor.GRAY + "  ● Mode " + ChatColor.DARK_GREEN + "Actif " + ChatColor.GRAY + "(⚔)",
                    ChatColor.GRAY + "  ● Mode " + ChatColor.RED + "Inactif " + ChatColor.GRAY + "(" + ChatColor.DARK_GRAY + "⚔" + ChatColor.GRAY + ")",
                    "",
                    ChatColor.GRAY + "Durant le mode " + ChatColor.DARK_GREEN + "actif" + ChatColor.GRAY + ", la hache est en " + ChatColor.WHITE + "fer" + ChatColor.GRAY + " et",
                    ChatColor.GRAY + "inflige " + ChatColor.RED + "12 dégâts" + ChatColor.GRAY + " et " + ChatColor.AQUA + "bouscule" + ChatColor.GRAY + " la " + ChatColor.RED + "cible" + ChatColor.GRAY + " touchée.",
                    ChatColor.GRAY + "Durant le mode " + ChatColor.RED + "inactif" + ChatColor.GRAY + ", elle est en " + ChatColor.DARK_GRAY + "pierre",
                    ChatColor.GRAY + "et inflige seulement " + ChatColor.RED + "4 dégâts" + ChatColor.GRAY + ".",
                    "",
                    ChatColor.GRAY + "Cependant, le mode ⚔ s'applique pour " + ChatColor.RED + "une seule",
                    ChatColor.RED + "attaque" + ChatColor.GRAY + ".",
                    ChatColor.GRAY + "Vous devrez attaquer " + ChatColor.YELLOW + "3 fois " + ChatColor.GRAY + "avec le mode " + ChatColor.DARK_GRAY + "⚔",
                    ChatColor.GRAY + "pour réobtenir les " + ChatColor.DARK_PURPLE + "pouvoirs" + ChatColor.GRAY + " du mode ⚔",
                    "",
                    ChatColor.GRAY + "Cette arme est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", elle",
                    ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + "!"
                ))
                .build(),
            20
        );

        private final HashItem article;
        private final int price;

        Articles(HashItem article, int price)
        {
            this.article = article;
            this.price = price;
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
        "OFFENSIF",
        ChatColor.RED,
        (byte) 14,
        (byte) 1
    );


    /**
     * Creates a new instance of ShopCategoryOffensive.
     *
     * @param   main    BreakFFA main
     * @param   player  Player
     */
    public ShopCategoryOffensive(BreakFFA main, Player player)
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
