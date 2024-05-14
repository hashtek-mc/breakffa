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
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShopArticle
{

    private final HashItem shopArticle;
    private final HashItem article;
    private final int price;
    private final boolean isTemporary;

    private ShopArticleBuyAction buyAction;


    /**
     * Creates a new Shop article.
     *
     * @param   article     Article
     * @param   price       Price
     */
    public ShopArticle(HashItem article, int price)
    {
        this(article, price, false);
    }

    /**
     * Creates a new Shop article.
     *
     * @param   article         Article
     * @param   price           Price
     * @param   isTemporary     If the item is temporary
     */
    public ShopArticle(HashItem article, int price, boolean isTemporary)
    {
        this.shopArticle = new HashItem(article);
        this.article = new HashItem(article);
        this.price = price;
        this.isTemporary = isTemporary;
    }


    /**
     * Initializes articles (shop and the one which will be used by players).
     */
    public ShopArticle build(BreakFFA main)
    {
        String articleName = this.getShopArticle().getItemMeta().getDisplayName();
        final String suffix = " " + ChatColor.WHITE + ChatColor.BOLD + "●" + ChatColor.AQUA + " " + this.price + " shards";

        if (!articleName.endsWith(suffix))
            articleName += suffix;

        this.getShopArticle().setName(articleName);

        if (this.isTemporary)
            this.getShopArticle()
                .addLore("")
                .addLore(ChatColor.GRAY + "Cet objet est " + ChatColor.RED + "temporaire" + ChatColor.GRAY + ", il")
                .addLore(ChatColor.GRAY + "disparaît à votre " + ChatColor.DARK_RED + "mort" + ChatColor.GRAY + " !");

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
     * @param   playerData  Player's data
     */
    public void buy(PlayerData playerData, PaginatedHashGui gui)
    {
        final Player player = playerData.getPlayer();
        final int playerShards = playerData.getShards();
        final int articlePrice = this.getPrice();

        final boolean hasEnoughShards = playerShards >= articlePrice;
        final boolean hasEnoughSpace = player.getInventory().firstEmpty() != -1;
        final boolean canBuy = hasEnoughShards && hasEnoughSpace;

        /* If item can't be bought, then cancel the transaction. */
        if (!canBuy) {
            String reason = ChatColor.RED + "";

            if (!hasEnoughShards)
                reason += "Vous ne possédez pas assez de " + ChatColor.AQUA + "shards" + ChatColor.RED + ".";
            if (!hasEnoughSpace)
                reason += "Vous n'avez plus de place dans votre inventaire.";

            player.sendMessage(reason);

            player.playSound(player.getLocation(), "random.break", 100, 0);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 100, 0);

            return;
        }

        /* Makes the transaction. */
        playerData.setShards(playerShards - articlePrice);

        /* Gives the item to the player. */
        if (this.buyAction == null)
            player.getInventory().addItem(this.getArticle().getItemStack());
        else
            this.buyAction.execute(player, this);

        /* Updates the shop items (for shards in lore). */
        final HashItem shopItem = playerData.getMain().getShopManager().createShopItem(playerData, true);
        playerData.getMain().getShopManager().giveShop(playerData);
        gui.replaceAll(shopItem.getItemMeta().getDisplayName(), shopItem);
        BreakFFA.getInstance().getBoardManager().getPlayerSidebar(player).refreshSidebar();


        /* Plays buy sound to the player. */
        player.playSound(player.getLocation(), "mob.horse.leather", 100, 1);
        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 100, 1);

        /* Sends buy confirmation to the player. */
        player.sendMessage(ChatColor.AQUA + "Vous avez acheté " + this.article.getItemMeta().getDisplayName() + ChatColor.AQUA + ".");
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
                    if (!(gui instanceof PaginatedHashGui))
                        return;

                    final PaginatedHashGui paginatedGui = (PaginatedHashGui) gui;
                    final PlayerData playerData = main.getGameManager().getPlayerData(player);

                    this.buy(playerData, paginatedGui);
                })
        );

        this.getShopArticle().build(main.getGuiManager());
    }

    public ShopArticle setBuyAction(ShopArticleBuyAction action)
    {
        this.buyAction = action;
        return this;
    }

    public ShopArticle setHoldAction(HoldAction action)
    {
        this.getArticle().addHoldHandler(
            new HoldHandler()
                .setHoldAction(action)
        );
        return this;
    }

    public ShopArticle setNotHoldAction(HoldAction action)
    {
        this.getArticle().addHoldHandler(
            new HoldHandler()
                .setNotHoldAction(action)
        );
        return this;
    }

    public ShopArticle setHitAction(HitAction action)
    {
        this.getArticle().addHitHandler(
            new HitHandler()
                .setHitAction(action)
        );
        return this;
    }

    public ShopArticle setKillAction(HitAction action)
    {
        this.getArticle().addHitHandler(
            new HitHandler()
                .setOnlyKill(true)
                .setHitAction(action)
        );
        return this;
    }

    public ShopArticle setNexusBreakAction(DestroyAction action)
    {
        this.getArticle().addDestroyHandler(
            new DestroyHandler()
                .setDestroyAction((Player player, ItemStack item, Block block) -> {
                    final Block nexus = BreakFFA.getInstance().getGameManager().getNexus().getBlock();

                    if (!block.equals(nexus))
                        return;

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

}
