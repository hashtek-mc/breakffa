package fr.hashtek.spigot.breakffa.shop.category.categories;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashutils.HashUtils;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategory;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryAttributes;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;

public class ShopCategorySupport
    extends ShopCategory
    implements HashLoggable
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    public enum Articles
        implements ShopCategoryArticles
    {

        GOLD_PACKAGE (
            new ShopArticle(
                new HashItem(Material.GOLDEN_APPLE)
                    .setName(Component.text(ChatColor.GREEN + "Colis d'Or"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Obtenez 3 " + ChatColor.GOLD + "pommes d'or" + ChatColor.GRAY + " supplémentaires"),
                        Component.text(ChatColor.GRAY + "instantanément, augmentant vos " + ChatColor.GREEN + "chances"),
                        Component.text(ChatColor.GRAY + "de " + ChatColor.DARK_GREEN + "survie" + ChatColor.GRAY + ", jusqu'à votre " + ChatColor.DARK_RED + "mort")
                    ))
                    .build(),
                2
            )
            .setBuyAction((Player player, ShopArticle a) -> {
                player.getInventory().addItem(KitStarter.Items.GOLDEN_APPLES.getItem().getItemStack());
            })
        ),

        OPTIMISED_PICKAXE (
            new ShopArticle(
                new HashItem(Material.IRON_PICKAXE)
                    .setName(Component.text(ChatColor.GREEN + "Pioche Optimisée"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure un outil amélioré, " + ChatColor.YELLOW + "incassable" + ChatColor.GRAY + " et"),
                        Component.text(ChatColor.DARK_PURPLE + "enchanté" + ChatColor.GRAY + " avec " + ChatColor.DARK_AQUA + "Efficacité III" + ChatColor.GRAY + ".")
                    ))
                    .addEnchant(Enchantment.DIG_SPEED, 3)
                    .setUnbreakable(true)
                    .build(),
                3,
                true
            )
        ),

        COMBUSTOR (
            new ShopArticle(
                new HashItem(Material.FLINT_AND_STEEL)
                    .setName(Component.text(ChatColor.GREEN + "Combusteur"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Procure un dispositif à " + ChatColor.YELLOW + "3 utilisations"),
                        Component.text(ChatColor.GRAY + "permettant " + ChatColor.GOLD + "d'enflammer" + ChatColor.GRAY + " une " + ChatColor.RED + "cible" + ChatColor.GRAY + " proche " + ChatColor.DARK_GRAY + "(3 blocs)"),
                        Component.text(ChatColor.GRAY + "tout en la " + ChatColor.AQUA + "repoussant" + ChatColor.GRAY + " légèrement.")
                    ))
                    .addInteractHandler(
                        new InteractHandler()
                            .addInteractTypes(Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR)
                            .setInteractAction((Player player, ItemStack item, int slot) -> {
                                final Location playerLocation = player.getEyeLocation();
                                final World world = playerLocation.getWorld();
                                Collection<? extends Player> onlinePlayers = MAIN.getServer().getOnlinePlayers();

                                /* Updates the item's durability. */
                                final int shots = 3;

                                final short itemMaxDurability = item.getType().getMaxDurability();
                                final int itemDurability = itemMaxDurability - ((Damageable) item.getItemMeta()).getDamage();
                                final int finalDurability = itemDurability - (itemMaxDurability / shots);

                                HashItem.setDurability(item, finalDurability);

                                if (finalDurability <= 1) {
                                    player.getInventory().setItem(slot, null);
                                }

                                final int range = 3;

                                for (Player p : onlinePlayers) {
                                    /* Plays sound. */
                                    p.playSound(playerLocation, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 2);
                                    p.playSound(playerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2); // FIXME: FIZZ, not ORB_PICKUP
                                    p.playSound(playerLocation, Sound.ITEM_FIRECHARGE_USE, 1, 2);

                                    /*
                                     * Spawn particles.
                                     *
                                     * /==>     *     *     *
                                     *     └0.25┘└0.25┘└0.25┘
                                     *
                                     * Legend:
                                     * `*`      Particle
                                     * `/==>`   Combustor
                                     */
                                    final double particleDistance = 0.25; // In blocks

                                    for (double k = 1; k < range; k += particleDistance) {
                                        final Location location = playerLocation.clone();
                                        final Vector vector = location.getDirection().multiply(k);
                                        location.add(vector);

                                        world.spawnParticle(
                                            Particle.FLAME,
                                            location,
                                            1,
                                            0, 0, 0,
                                            0
                                        );
                                    }
                                }

                                /* Apply damage to victim if there is one. */
                                final Player victim = HashUtils.getAimedPlayer(player, range);

                                if (victim == null) {
                                    return;
                                }

                                victim.setFireTicks(20 * 3);

                                // TODO: Apply a knockback on the victim.
                            })
                    )
                    .build(BreakFFA.getInstance().getGuiManager()),
                5,
                true
            )
        ),

        BASEBALL_BAT (
            new ShopArticle(
                new HashItem(Material.STONE_SWORD)
                    .setName(Component.text(ChatColor.GREEN + "Batte de Baseball"))
                    .setLore(Arrays.asList(
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Mêlée permettant de " + ChatColor.AQUA + "pousser"),
                        Component.text(ChatColor.GRAY + "une " + ChatColor.RED + "cible" + ChatColor.GRAY + " quelconque à plus de"),
                        Component.text(ChatColor.YELLOW + "5 blocks " + ChatColor.GRAY + "en un seul coup."),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Cependant, cette batte se " + ChatColor.DARK_RED + "casse"),
                        Component.text(ChatColor.GRAY + "après " + ChatColor.YELLOW + "un seul" + ChatColor.GRAY + " coup!"),
                        Component.text(""),
                        Component.text(ChatColor.GRAY + "Disparaît également à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + ","),
                        Component.text(ChatColor.GRAY + "en cas de non utilisation.")
                    ))
                    .addEnchant(Enchantment.KNOCKBACK, 4)
                    .setDurability(0)
                    .build(),
                6,
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

    private static final ShopCategoryAttributes attributes =
        new ShopCategoryAttributes(
            "SUPPORT",
            ChatColor.GREEN,
            Material.GREEN_STAINED_GLASS_PANE,
            Material.LIME_STAINED_GLASS_PANE
        );


    /**
     * Creates a new instance of ShopCategorySupport.
     *
     * @param   player  Player
     */
    public ShopCategorySupport(Player player)
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
