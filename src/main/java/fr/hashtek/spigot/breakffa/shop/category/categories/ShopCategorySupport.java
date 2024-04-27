package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShopCategorySupport extends ShopCategory
{

    public enum Articles implements ShopCategoryArticles
    {

        GOLD_PACKAGE (
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
                2
            )
            .setBuyAction((Player player, ShopArticle a) -> {
                player.getInventory().addItem(KitStarter.Items.GOLDEN_APPLES.getItem().getItemStack());
            })
            .build(BreakFFA.getInstance())
        ),

        OPTIMISED_PICKAXE (
            new ShopArticle(
                new HashItem(Material.IRON_PICKAXE)
                    .setName(ChatColor.GREEN + "Pioche Optimisée")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Procure un outil amélioré, " + ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et",
                        ChatColor.DARK_PURPLE + "enchanté" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Efficacité III" + ChatColor.GRAY + "."
                    ))
                    .addEnchant(Enchantment.DIG_SPEED, 3)
                    .setUnbreakable(true)
                    .build(),
                3,
                true
            )
            .build(BreakFFA.getInstance())
        ),

        COMBUSTOR (
            new ShopArticle(
                new HashItem(Material.FLINT_AND_STEEL)
                    .setName(ChatColor.GREEN + "Combusteur")
                    .setLore(Arrays.asList(
                        "",
                        ChatColor.GRAY + "Procure un dispositif à " + ChatColor.YELLOW + "3 utilisations",
                        ChatColor.GRAY + "permettant " + ChatColor.GOLD + "d'enflammer" + ChatColor.GRAY + " une " + ChatColor.RED + "cible" + ChatColor.GRAY + " proche " + ChatColor.DARK_GRAY + "(3 blocs)",
                        ChatColor.GRAY + "tout en la " + ChatColor.AQUA + "repoussant" + ChatColor.GRAY + " légèrement."
                    ))
                    .addInteractHandler(
                        new InteractHandler()
                            .addInteractTypes(Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR)
                            .setInteractAction((Player player, ItemStack item, int slot) -> {
//                                final int shots = 3;
//
//                                final short itemMaxDurability = item.getType().getMaxDurability();
//                                final short itemDurability = (short) (itemMaxDurability - item.getDurability());
//                                final short finalDurability = (short) (itemDurability - (itemMaxDurability / shots));
//
//                                item.setDurability((short) (itemMaxDurability - finalDurability));
//
//                                if (finalDurability <= 1)
//                                    player.getInventory().setItem(slot, null);
//
//                                // TODO: particles gaming
//
//                                final Player victim = HashUtils.getAimedPlayer(player);
//
//                                if (victim == null)
//                                    return;
//
//                                final Location playerLocation = player.getLocation();
//                                final Location victimLocation = victim.getLocation();
//                                final Vector kb = victimLocation.toVector().subtract(playerLocation.toVector()).normalize();
//
//                                victim.setFireTicks(20 * 3);
//                                victim.setVelocity(kb.multiply(1.5));
                            })
                    )
                    .build(BreakFFA.getInstance().getGuiManager()),
                5,
                true
            )
            .build(BreakFFA.getInstance())
        ),

        BASEBALL_BAT (
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
                    .addEnchant(Enchantment.KNOCKBACK, 4)
                    .setDurability((short) 0)
                    .build(),
                6,
                true
            )
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
                .clearFlags()
                .build()
                .getItemStack();

            final ItemStack article = new HashItem(this.article.getArticle())
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
            super.addArticle(article.getArticle());
    }

}
