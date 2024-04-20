package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.hashutils.HashUtils;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticleBuyAction;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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
            2,
            (Player player, ShopArticle a) -> {
                player.getInventory().addItem(KitStarter.Items.GOLDEN_APPLES.getItem().getItemStack());
            }
        ),

        OPTIMISED_PICKAXE (
            new HashItem(Material.IRON_PICKAXE)
                .setName(ChatColor.GREEN + "Pioche optimisée")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Procure un outil amélioré, " + ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et",
                    ChatColor.DARK_PURPLE + "enchanté" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Efficacité III" + ChatColor.GRAY + ".",
                    "",
                    ChatColor.GRAY + "Cet outil est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ",",
                    ChatColor.GRAY + "il disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + " !"
                ))
                .build(),
            3
        ),

        COMBUSTOR (
            new HashItem(Material.FLINT_AND_STEEL)
                .setName(ChatColor.GREEN + "Combusteur")
                .setLore(Arrays.asList(
                    "",
                    ChatColor.GRAY + "Procure un dispositif à " + ChatColor.YELLOW + "3 utilisations",
                    ChatColor.GRAY + "permettant " + ChatColor.GOLD + "d'enflammer" + ChatColor.GRAY + " une " + ChatColor.RED + "cible" + ChatColor.GRAY + " proche " + ChatColor.DARK_GRAY + "(3 blocs)",
                    ChatColor.GRAY + "tout en la " + ChatColor.AQUA + "repoussant" + ChatColor.GRAY + " légèrement.",
                    "",
                    ChatColor.GRAY + "Disparaît à votre  " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + ", en cas",
                    ChatColor.GRAY + "de non utilisation."
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addInteractTypes(Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR)
                            .setInteractAction((Player player, ItemStack item, int slot) -> {
                                final int shots = 3;

                                final short itemMaxDurability = item.getType().getMaxDurability();
                                final short itemDurability = (short) (itemMaxDurability - item.getDurability());
                                final short finalDurability = (short) (itemDurability - (itemMaxDurability / shots));

                                item.setDurability((short) (itemMaxDurability - finalDurability));

                                if (finalDurability <= 1)
                                    player.getInventory().setItem(slot, null);

                                // TODO: particles gaming

                                final Player victim = HashUtils.getAimedPlayer(player);

                                if (victim == null)
                                    return;

                                final Location playerLocation = player.getLocation();
                                final Location victimLocation = victim.getLocation();
                                final Vector kb = victimLocation.toVector().subtract(playerLocation.toVector()).normalize();

                                victim.setFireTicks(20 * 3);
                                victim.setVelocity(kb.multiply(1.5));
                            })
                )
                .build(BreakFFA.getInstance().getGuiManager()),
            5
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
                .addEnchant(Enchantment.KNOCKBACK, 4)
                .setDurability((short) 0)
                .build(),
            6
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
