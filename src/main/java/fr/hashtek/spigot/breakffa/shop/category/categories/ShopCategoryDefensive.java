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
            new HashItem(Material.CHAINMAIL_CHESTPLATE)
                .setName(ChatColor.BLUE + "Côte de Maille")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Equipez-vous d'une côte de maille",
                    ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec",
                    ChatColor.DARK_AQUA + "Protection contre les projectiles II" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+16%)",
                    "",
                    ChatColor.GRAY + "Cette armure est  " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ",",
                    ChatColor.GRAY + "elle disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + "!"
                ))
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, 2)
                .setUnbreakable(true)
                .build(),
            3,
            (Player player, ShopArticle a) -> {
                player.getInventory().setChestplate(a.getArticle().getItemStack());
            }
        ),

        LUCKY_BOOTS (
            new HashItem(Material.LEATHER_BOOTS)
                .setName(ChatColor.BLUE + "Bottes Bonheurs")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Mettez toutes les " + ChatColor.DARK_GREEN + "chances" + ChatColor.GRAY + " de votre côté !",
                    ChatColor.GRAY + "Ces bottes " + ChatColor.YELLOW + "offrent" + ChatColor.GRAY + " un léger bonus de " + ChatColor.AQUA + "vitesse" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+10%)",
                    "",
                    ChatColor.GRAY + "Chaque " + ChatColor.DARK_RED + "Nexus" + ChatColor.GRAY + " cassé avec ces bottes " + ChatColor.YELLOW + "à vos pieds",
                    ChatColor.GRAY + "vous donnera " + ChatColor.AQUA + "3 shards" + ChatColor.GRAY + " bonus !"
                ))
                .setUnbreakable(true)
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
            (Player player, ShopArticle a) -> {
                player.getInventory().setBoots(a.getArticle().getItemStack());
                BreakFFA.getInstance().getGuiManager().getHoldManager().refreshArmorState(player);
            }
        ),

        SILVERMAIL (
            new HashItem(Material.CHAINMAIL_CHESTPLATE)
                .setName(ChatColor.BLUE + "Côte d'Argent")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Equipez-vous d'une côte d'" + ChatColor.WHITE + "argent",
                    ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et " + ChatColor.DARK_PURPLE + "enchantée" + ChatColor.GRAY + " avec",
                    ChatColor.DARK_AQUA + "Protection contre les projectiles IV" + ChatColor.DARK_GRAY + " (+32%)",
                    ChatColor.GRAY + "et " + ChatColor.DARK_AQUA + "Epines I" + ChatColor.GRAY + "." + ChatColor.DARK_GRAY + " (+15%)",
                    "",
                    ChatColor.GRAY + "Cette armure est  " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ",",
                    ChatColor.GRAY + "elle disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + "!"
                ))
                .addEnchant(Enchantment.PROTECTION_PROJECTILE, 4)
                .addEnchant(Enchantment.THORNS, 1)
                .setUnbreakable(true)
                .build(),
            8,
            (Player player, ShopArticle a) -> {
                player.getInventory().setChestplate(a.getArticle().getItemStack());
            }
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
                    article.getBuyAction(),
                    super.getMain()
                )
            );
    }

}
