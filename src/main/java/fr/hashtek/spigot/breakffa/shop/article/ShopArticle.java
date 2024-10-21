package fr.hashtek.spigot.breakffa.shop.article;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.destroy.DestroyAction;
import fr.hashtek.spigot.hashgui.handler.destroy.DestroyHandler;
import fr.hashtek.spigot.hashgui.handler.hit.HitAction;
import fr.hashtek.spigot.hashgui.handler.hit.HitHandler;
import fr.hashtek.spigot.hashgui.handler.hold.HoldAction;
import fr.hashtek.spigot.hashgui.handler.hold.HoldHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class ShopArticle
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    private final HashItem shopArticle;
    private final HashItem article;
    private final int price;
    private final boolean isTemporary;
    private final boolean needsNewSlot;

    private ShopArticleBuyAction buyAction;


    /**
     * Creates a new Shop article.
     *
     * @param   article     Article
     * @param   price       Article price
     */
    public ShopArticle(HashItem article, int price)
    {
        this(article, price, false);
    }

    /**
     * Creates a new Shop article.
     *
     * @param   article         Article
     * @param   price           Article price
     * @param   isTemporary     Is the item temporary
     */
    public ShopArticle(HashItem article, int price, boolean isTemporary)
    {
        this(article, price, isTemporary, true);
    }

    /**
     * Creates a new Shop article.
     *
     * @param   article         Article
     * @param   price           Article price
     * @param   isTemporary     Is the item temporary
     * @param   needsNewSlot    Does item needs a new slot in the hotbar
     */
    public ShopArticle(HashItem article, int price, boolean isTemporary, boolean needsNewSlot)
    {
        this.shopArticle = new HashItem(article);
        this.article = new HashItem(article);
        this.price = price;
        this.isTemporary = isTemporary;
        this.needsNewSlot = needsNewSlot;
    }


    /**
     * Initializes articles (shop and the one which will be used by players).
     */
    public ShopArticle build(BreakFFA main)
    {
        String articleName = ((TextComponent) this.getShopArticle().getItemMeta().displayName()).content();
        final String suffix = " " + ChatColor.WHITE + ChatColor.BOLD + "●" + ChatColor.AQUA + " " + this.price + " shards";

        if (!articleName.endsWith(suffix)) {
            articleName += suffix;
        }

        this.getShopArticle().setName(Component.text(articleName));

        if (this.isTemporary) {
            this.getShopArticle()
                .addLore(Arrays.asList(
                    Component.text(""),
                    Component.text(ChatColor.GRAY + "Cet objet est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", il"),
                    Component.text(ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + " !")
                ));
        }

        this.getShopArticle()
            .setFlags(Arrays.asList(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_UNBREAKABLE
            ))
            .clearHandlers()
            .build();

        this.setShopArticleClickHandler(main);

        this.article
            .setFlags(Arrays.asList(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_UNBREAKABLE
            ))
            .clearLore()
            .build(main.getGuiManager());

        return this;
    }

    /**
     * Buys the article for a player.
     *
     * @param   player  Transaction author
     * @param   gui     Active transaction Gui
     */
    public void buy(Player player, PaginatedHashGui gui)
    {
        final PlayerData playerData = MAIN.getGameManager().getPlayerManager(player).getData();
        final int playerShards = playerData.getShards();
        final int articlePrice = this.getPrice();

        final boolean hasEnoughShards = playerShards >= articlePrice;
        final boolean hasEnoughSpace =
            player.getInventory().firstEmpty() != -1 || !this.needsNewSlot;

        /* If item can't be bought, then cancel the transaction. */
        if (!hasEnoughShards || !hasEnoughSpace) {
            String reason = ChatColor.RED + "";

            if (!hasEnoughShards) {
                reason += "Vous ne possédez pas assez de " + ChatColor.AQUA + "shards" + ChatColor.RED + ".";
            }
            if (!hasEnoughSpace) {
                reason += "Vous n'avez plus de place dans votre inventaire.";
            }

            player.sendMessage(reason);

            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 100, 0); // FIXME: Sound was "random.break", verify the new enum value
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 0);

            return;
        }

        /* Makes the transaction. */
        playerData.setShards(playerShards - articlePrice);

        /* Gives the item to the player. */
        if (this.buyAction == null) {
            player.getInventory().addItem(this.getArticle().getItemStack());
        } else {
            this.buyAction.execute(player, this);
        }

        /* Updates the shop items (for shards in lore). */
        final HashItem shopItem = MAIN.getShopManager().createShopItem(playerData, true);
        MAIN.getShopManager().giveShop(player);
        gui.replaceAll(shopItem.getItemMeta().displayName(), shopItem);

//        try {
//            BreakFFA.getInstance().getBoardManager().getPlayerSidebar(player).refreshSidebar();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            // TODO: Write proper error handling
//        }

        /* Plays buy sound to the player. */
        player.playSound(player.getLocation(), Sound.ENTITY_HORSE_AMBIENT, 100, 1); // FIXME: Sound was "mob.horse.leather", verify the new enum value
        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 100, 1);

        /* Sends buy confirmation to the player. */
        player.sendMessage(
            ChatColor.AQUA + "Vous avez acheté " +
            ((TextComponent) Objects.requireNonNull(this.article.getItemMeta().displayName())).content() +
            ChatColor.AQUA + "."
        );
    }

    /**
     * Sets article click handler (for buy click detection).
     * FIXME: If GUI is not an instance of PaginatedHashGui, you may
     *        want to throw an error.
     */
    private void setShopArticleClickHandler(BreakFFA main)
    {
        this.getShopArticle().addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                    if (!(gui instanceof PaginatedHashGui paginatedGui)) {
                        return;
                    }

                    this.buy(player, paginatedGui);
                })
        );

        this.getShopArticle().build(main.getGuiManager());
    }

    /**
     * @param   action  Custom action on buy
     * @return  Returns itself.
     */
    public ShopArticle setBuyAction(ShopArticleBuyAction action)
    {
        this.buyAction = action;
        return this;
    }

    /**
     * @param   action  Custom action on hold
     * @return  Returns itself.
     */
    public ShopArticle setHoldAction(HoldAction action)
    {
        this.getArticle().addHoldHandler(
            new HoldHandler()
                .setHoldAction(action)
        );
        return this;
    }

    /**
     * @param   action  Custom action on not hold
     * @return  Returns itself.
     */
    public ShopArticle setNotHoldAction(HoldAction action)
    {
        this.getArticle().addHoldHandler(
            new HoldHandler()
                .setNotHoldAction(action)
        );
        return this;
    }

    /**
     * @param   action  Custom action on hit
     * @return  Returns itself.
     */
    public ShopArticle setHitAction(HitAction action)
    {
        this.getArticle().addHitHandler(
            new HitHandler()
                .setHitAction(action)
        );
        return this;
    }

    /**
     * @param   action  Custom action on kill
     * @return  Returns itself.
     */
    public ShopArticle setKillAction(HitAction action)
    {
        this.getArticle().addHitHandler(
            new HitHandler()
                .setOnlyKill(true)
                .setHitAction(action)
        );
        return this;
    }

    /**
     * @param   action  Custom action on Nexus break
     * @return  Returns itself.
     */
    public ShopArticle setNexusBreakAction(DestroyAction action)
    {
        this.getArticle().addDestroyHandler(
            new DestroyHandler()
                .setDestroyAction((Player player, ItemStack item, Block block) -> {
                    final Block nexus = MAIN.getGameManager().getNexus().getBlock();

                    if (!block.equals(nexus)) {
                        return;
                    }

                    action.execute(player, item, block);
                })
        );
        return this;
    }

    /**
     * @return  Shop article (with the price in the title, and the lore)
     */
    public HashItem getShopArticle()
    {
        return this.shopArticle;
    }

    /**
     * @return  Article (which will be used by players)
     */
    public HashItem getArticle()
    {
        return this.article;
    }

    /**
     * @return  Article price
     */
    public int getPrice()
    {
        return this.price;
    }

    /**
     * @return  True if the item is temporary
     */
    public boolean isTemporary()
    {
        return this.isTemporary;
    }

    /**
     * @return  True if the item needs a new slot in the hotbar
     */
    public boolean doesNeedNewSlot()
    {
        return this.needsNewSlot;
    }

}
