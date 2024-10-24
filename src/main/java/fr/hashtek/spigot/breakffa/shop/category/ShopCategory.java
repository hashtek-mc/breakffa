package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashgui.page.Page;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShopCategory
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();

    private final ShopManager shopManager;

    private final Player player;
    private final PlayerData playerData;

    private PaginatedHashGui gui;

    private final ShopCategoryAttributes attributes;
    private final List<ShopArticle> articles;


    /**
     * Creates a new Shop category
     *
     * @param   player          Player
     * @param   attributes      Category attributes
     */
    public ShopCategory(
        Player player,
        ShopCategoryAttributes attributes
    )
    {
        this.shopManager = MAIN.getShopManager();

        this.player = player;
        this.playerData = MAIN.getGameManager().getPlayerManager(player).getData();

        this.attributes = attributes;
        this.articles = new ArrayList<ShopArticle>();

        this.createGui();
    }


    /**
     * Creates the category's base GUI.
     */
    public void createGui()
    {
        final ShopCategoryAttributes attributes = this.attributes;

        this.gui = new PaginatedHashGui(
            Component.text("" + attributes.getColor() + ChatColor.BOLD + "Marché " + ChatColor.WHITE + ChatColor.BOLD + "●" + attributes.getColor() + ChatColor.BOLD + " " + attributes.getName()),
            6,
            GUI_MANAGER
        );

        final HashItem primaryGlass = HashItem.separator(attributes.getPrimaryColor(), GUI_MANAGER);
        final HashItem secondaryGlass = HashItem.separator(attributes.getSecondaryColor(), GUI_MANAGER);

        final HashItem previousPage = new HashItem(Material.ARROW)
            .setName(Component.text(attributes.getColor() + "Page précédente"))
            .addLore(Component.text(ChatColor.GRAY + "Cliquez pour afficher la page précédente."));

        final HashItem nextPage = new HashItem(Material.ARROW)
            .setName(Component.text(attributes.getColor() + "Page suivante"))
            .addLore(Component.text(ChatColor.GRAY + "Cliquez pour afficher la page suivante."));

        final HashItem shopItem = this.shopManager.createShopItem(this.playerData, true);

        this.gui.setPreviousPageItem(previousPage);
        this.gui.setNextPageItem(nextPage);

        final Mask mask = new Mask(this.gui);

        mask.setItem('p', primaryGlass)
            .setItem('s', secondaryGlass)
            .setItem('N', nextPage)
            .setItem('P', previousPage)
            .setItem('S', shopItem);

        mask.pattern("ppssssspp")
            .pattern("pss   ssp")
            .pattern("ss     ss")
            .pattern("ss     ss")
            .pattern("pss   ssp")
            .pattern("ppsPSNspp");

        mask.apply();
    }

    /**
     * Opens the GUI.
     */
    public void open()
    {
        this.open(this.player);
    }

    /**
     * Opens the GUI to a player.
     *
     * @param   player    Player
     */
    public void open(Player player)
    {
        this.gui.open(player);
        this.gui.update(player);
    }

    /**
     * Loads articles.
     * TODO: Auto loader.
     *
     * @apiNote MUST be overwritten by child classes.
     */
    public void loadArticles() {}

    /**
     * Adds an article to the first free slot in the GUI.
     * If current page is full, a new one will be created.
     *
     * @param   article     Article to add
     */
    public void addArticle(ShopArticle article)
    {
        final Page lastPage = this.gui.getLastPage();

        try {
            lastPage.addItem(article.getShopArticle());
        } catch (IllegalArgumentException unused) {
            this.gui.createNewPage();
            this.addArticle(article);
        }
    }

    /**
     * Adds a list of articles in the shop category articles.
     *
     * @param   articles    List of articles
     */
    public void addArticles(List<ShopArticle> articles)
    {
        for (ShopArticle article : articles) {
            this.addArticle(article);
        }
    }


    /**
     * @return  Shop manager
     */
    public ShopManager getShopManager()
    {
        return this.shopManager;
    }

    /**
     * @return  Player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * @return  Gui
     */
    public PaginatedHashGui getGui()
    {
        return this.gui;
    }

    /**
     * @return  Category's attributes
     */
    public ShopCategoryAttributes getAttributes()
    {
        return this.attributes;
    }

    /**
     * @return  List of articles
     */
    public List<ShopArticle> getArticles()
    {
        return this.articles;
    }

}
