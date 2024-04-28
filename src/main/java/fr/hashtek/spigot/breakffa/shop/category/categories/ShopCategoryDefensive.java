package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticleBuyAction;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashgui.handler.hold.HoldHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class ShopCategoryDefensive extends ShopCategory
{

    public enum Articles implements ShopCategoryArticles
    {

        CHAINMAIL (
            new ShopArticle(
                new HashItem(Material.CHAINMAIL_CHESTPLATE)
                    .setName(ChatColor.BLUE + "Côte de Maille")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Equipez-vous d'une côte de maille",
                        ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec",
                        ChatColor.DARK_AQUA + "Protection contre les projectiles II" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+16%)"
                    ))
                    .addEnchant(Enchantment.PROTECTION_PROJECTILE, 2)
                    .setUnbreakable(true)
                    .build(),
                3,
                true
            )
            .setBuyAction((Player player, ShopArticle a) -> {
                player.getInventory().setChestplate(a.getArticle().getItemStack());
            })
            .build(BreakFFA.getInstance())
        ),

        LUCKY_BOOTS (
            new ShopArticle(
                new HashItem(Material.LEATHER_BOOTS)
                    .setName(ChatColor.BLUE + "Bottes Bonheurs")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Mettez toutes les " + ChatColor.DARK_GREEN + "chances" + ChatColor.GRAY + " de votre côté !",
                        ChatColor.GRAY + "Ces bottes " + ChatColor.YELLOW + "offrent" + ChatColor.GRAY + " un léger bonus de " + ChatColor.AQUA + "vitesse" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+20%)",
                        "",
                        ChatColor.GRAY + "Chaque " + ChatColor.DARK_RED + "Nexus" + ChatColor.GRAY + " cassé avec ces bottes " + ChatColor.YELLOW + "à vos pieds",
                        ChatColor.GRAY + "vous donnera " + ChatColor.AQUA + "3 shards" + ChatColor.GRAY + " bonus !"
                    ))
                    .setUnbreakable(true)
                    .setLeatherArmorColor(Color.fromRGB(52, 249, 81))
                    .addHoldHandler(
                        new HoldHandler()
                            .setHoldAction((Player player, ItemStack itemStack, int slot) -> {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 0), true);
                            })
                            .setNotHoldAction((Player player, ItemStack itemStack, int slot) -> {
                                player.removePotionEffect(PotionEffectType.SPEED);
                            })
                    )
                    .build(BreakFFA.getInstance().getGuiManager()),
                7,
                true
            )
            .setBuyAction((Player player, ShopArticle a) -> {
                final BreakFFA main = BreakFFA.getInstance();

                player.getInventory().setBoots(a.getArticle().getItemStack());

                main.getGuiManager().getHoldManager().refreshArmorState(player);
            })
            .build(BreakFFA.getInstance())
        ),

        SILVERMAIL (
            new ShopArticle(
                new HashItem(Material.CHAINMAIL_CHESTPLATE)
                    .setName(ChatColor.BLUE + "Côte d'Argent")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Equipez-vous d'une côte d'" + ChatColor.WHITE + "argent",
                        ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec",
                        ChatColor.DARK_AQUA + "Protection contre les projectiles IV" + ChatColor.DARK_GRAY + " (+32%)",
                        ChatColor.GRAY + "et " + ChatColor.DARK_AQUA + "Epines I" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+15%)"
                    ))
                    .addEnchant(Enchantment.PROTECTION_PROJECTILE, 4)
                    .addEnchant(Enchantment.THORNS, 1)
                    .setUnbreakable(true)
                    .build(),
                8,
                true
            )
            .setBuyAction((Player player, ShopArticle a) -> {
                player.getInventory().setChestplate(a.getArticle().getItemStack());
            })
            .build(BreakFFA.getInstance())
        );

        private final ShopArticle article;


        Articles(ShopArticle article)
        {
            this.article = article;
        }


        @Override
        public ShopArticle getArticle()
        {
            return this.article;
        }

        @Override
        public boolean equals(ItemStack item)
        {
            if (item == null || item.getType() == Material.AIR)
                return false;

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
            super.addArticle(article.getArticle());
    }

}
