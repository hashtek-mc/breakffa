package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class ShopCategoryOffensive
    extends ShopCategory
{

    public enum Articles
        implements ShopCategoryArticles
    {

        CHARMED_SWORD (
            new ShopArticle(
                new HashItem(Material.IRON_SWORD)
                    .setName(Component.text(ChatColor.RED + "Epée Charmée"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure une épée " + ChatColor.LIGHT_PURPLE + "charmée" + ChatColor.GRAY + " par les"),
                        Component.text(ChatColor.GRAY + "forces de la " + ChatColor.AQUA + "célérité" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "incassable" + ChatColor.GRAY + ","),
                        Component.text(ChatColor.GRAY + "et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant I" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+7.25)"),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Chaque élimination vous " + ChatColor.YELLOW + "imprègne" + ChatColor.GRAY + " avec l'effet"),
                        Component.text(ChatColor.AQUA + "Vitesse I" + ChatColor.GRAY + " pendant " + ChatColor.YELLOW + "5 secondes" + ChatColor.GRAY + ".")
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 1)
                    .setUnbreakable(true)
                    .build(),
                3,
                true
            )
            .setKillAction((Player attacker, Player victim, ItemStack item) -> {
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1), true);
            })
        ),

        CLAYMORE (
            new ShopArticle(
                new HashItem(Material.DIAMOND_SWORD)
                    .setName(Component.text(ChatColor.RED + "Claymore"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure une " + ChatColor.RED + "Claymore" + ChatColor.GRAY + " en " + ChatColor.AQUA + "diamant" + ChatColor.GRAY + ","),
                        Component.text(ChatColor.GRAY + "une épée lourde. " + ChatColor.DARK_GRAY + "(+7)"),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Quand vous êtes " + ChatColor.DARK_GRAY + "immobile" + ChatColor.GRAY + ", cette arme"),
                        Component.text(ChatColor.GRAY + "inflige " + ChatColor.RED + "+2 dégâts" + ChatColor.GRAY + " supplémentaires.")
                    ))
                    .build(),
                4,
                true
            )
        ),

        REINFORCED_SWORD (
            new ShopArticle(
                new HashItem(Material.IRON_SWORD)
                    .setName(Component.text(ChatColor.RED + "Epée Renforcée"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure une épée renforcée " + ChatColor.YELLOW + "incassable"),
                        Component.text(ChatColor.GRAY + "et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant II" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+8.5)")
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 2)
                    .setUnbreakable(true)
                    .build(),
                3,
                true
            )
        ),

        SHINY_SWORD (
            new ShopArticle(
                new HashItem(Material.DIAMOND_SWORD)
                    .setName(Component.text(ChatColor.RED + "Epée Brillante"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure une épée brillante " + ChatColor.YELLOW + "incassable"),
                        Component.text(ChatColor.GRAY + "en " + ChatColor.AQUA + "diamant" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant II" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+9.5)")
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 2)
                    .setUnbreakable(true)
                    .build(),
                8,
                true
            )
        ),

        PERFECT_SWORD (
            new ShopArticle(
                new HashItem(Material.GOLDEN_SWORD)
                    .setName(Component.text(ChatColor.RED + "Epée Parfaite"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure l'épée " + ChatColor.GOLD + "parfaite" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "incassable"),
                        Component.text(ChatColor.GRAY + "en " + ChatColor.GOLD + "or" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Tranchant IV" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+9)"),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Cette épée " + ChatColor.YELLOW + "offre" + ChatColor.AQUA + " 1 shard " + ChatColor.GRAY + "supplémentaire"),
                        Component.text(ChatColor.GRAY + "à chaque " + ChatColor.RED + "élimination" + ChatColor.GRAY + " !")
                    ))
                    .addEnchant(Enchantment.DAMAGE_ALL, 4)
                    .setUnbreakable(true)
                    .build(),
                12,
                true
            )
        ),

        HASH_ACTIVE (
            new ShopArticle(
                new HashItem(Material.IRON_AXE)
                    .setName(Component.text(ChatColor.RED + "Hach-active"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Obtenez la " + ChatColor.RED + "Hach-active" + ChatColor.GRAY + ", une arme au"),
                        Component.text(ChatColor.GRAY + "fonctionnement " + ChatColor.BLUE + "complexe" + ChatColor.GRAY + "."),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Elle possède " + ChatColor.YELLOW + "deux modes" + ChatColor.GRAY + " :"),
                        Component.text(ChatColor.GRAY + "  ● Mode " + ChatColor.DARK_GREEN + "Actif " + ChatColor.GRAY + "(⚔)"),
                        Component.text(ChatColor.GRAY + "  ● Mode " + ChatColor.RED + "Inactif " + ChatColor.GRAY + "(" + ChatColor.DARK_GRAY + "⚔" + ChatColor.GRAY + ")"),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Durant le mode " + ChatColor.DARK_GREEN + "actif" + ChatColor.GRAY + ", la hache est en " + ChatColor.WHITE + "fer" + ChatColor.GRAY + " et"),
                        Component.text(ChatColor.GRAY + "inflige " + ChatColor.RED + "12 dégâts" + ChatColor.GRAY + " et " + ChatColor.AQUA + "bouscule" + ChatColor.GRAY + " la " + ChatColor.RED + "cible" + ChatColor.GRAY + " touchée."),
                        Component.text(ChatColor.GRAY + "Durant le mode " + ChatColor.RED + "inactif" + ChatColor.GRAY + ", elle est en " + ChatColor.DARK_GRAY + "pierre"),
                        Component.text(ChatColor.GRAY + "et inflige seulement " + ChatColor.RED + "4 dégâts" + ChatColor.GRAY + "."),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Cependant, le mode ⚔ s'applique pour " + ChatColor.RED + "une seule"),
                        Component.text(ChatColor.RED + "attaque" + ChatColor.GRAY + "."),
                        Component.text(ChatColor.GRAY + "Vous devrez attaquer " + ChatColor.YELLOW + "3 fois " + ChatColor.GRAY + "avec le mode " + ChatColor.DARK_GRAY + "⚔"),
                        Component.text(ChatColor.GRAY + "pour réobtenir les " + ChatColor.DARK_PURPLE + "pouvoirs" + ChatColor.GRAY + " du mode ⚔")
                    ))
                    .build(),
                20,
                true
            )
        );

        private final ShopArticle article;


        Articles(ShopArticle article)
        {
            this.article = article;

            this.article.build(BreakFFA.getInstance());
        }


        @Override
        public ShopArticle getArticle()
        {
            return this.article;
        }

        @Override
        public boolean equals(ItemStack item)
        {
            if (item == null || item.getType() == Material.AIR) {
                return false;
            }

            final ItemStack i = new HashItem(item)
                .build()
                .getItemStack();

            final ItemStack article = new HashItem(this.article.getArticle())
                .clearLore()
                .build()
                .getItemStack();

            return i.equals(article);
        }

    }

    private static final ShopCategoryAttributes attributes = new ShopCategoryAttributes(
        "OFFENSIF",
        ChatColor.RED,
        Material.RED_STAINED_GLASS_PANE,
        Material.ORANGE_STAINED_GLASS_PANE
    );


    /**
     * Creates a new instance of ShopCategoryOffensive.
     *
     * @param   player  Player
     */
    public ShopCategoryOffensive(Player player)
    {
        super(player, attributes);
        this.loadArticles();
    }

    /**
     * Loads articles.
     */
    @Override
    public void loadArticles()
    {
        for (Articles article : Articles.values()) {
            super.addArticle(article.getArticle());
        }
    }
}
