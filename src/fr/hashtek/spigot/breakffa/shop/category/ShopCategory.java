package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashgui.page.Page;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShopCategory
{

    private final BreakFFA main;
    private final HashGuiManager guiManager;
    private final ShopManager shopManager;

    private final Player player;
    private final PlayerData playerData;

    private PaginatedHashGui gui;

    private final String category;
    private final ChatColor color;
    private final Byte primaryColor;
    private final Byte secondaryColor;

    private final List<ShopArticle> articles;


    /**
     * Creates a new Shop category
     *
     * @param   main            BreakFFA instance
     * @param   player          Player
     * @param   category        Category title
     * @param   color           Color (for strings)
     * @param   primaryColor    Primary color
     * @param   secondaryColor  Secondary color
     */
    public ShopCategory(
        BreakFFA main,
        Player player,
        String category,
        ChatColor color,
        Byte primaryColor,
        Byte secondaryColor
    )
    {
        this.main = main;
        this.guiManager = this.main.getGuiManager();
        this.shopManager = this.main.getShopManager();

        this.player = player;
        this.playerData = this.main.getGameManager().getPlayerData(player);

        this.category = category;
        this.color = color;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;

        this.articles = new ArrayList<ShopArticle>();

        this.createGui();
    }


    /**
     * Creates the category's base GUI.
     */
    public void createGui()
    {
        this.gui = new PaginatedHashGui(
            "" + this.color + ChatColor.BOLD + "Marché " + ChatColor.WHITE + ChatColor.BOLD + "●" + this.color + ChatColor.BOLD + " " + this.category,
            6,
            this.guiManager
        );

        final HashItem primaryGlass = HashItem.separator(this.primaryColor, this.guiManager);
        final HashItem secondaryGlass = HashItem.separator(this.secondaryColor, this.guiManager);

        final HashItem previousPage = new HashItem(Material.ARROW)
            .setName(this.color + "Page précédente")
            .addLore(ChatColor.GRAY + "Cliquez pour afficher la page précédente.");

        final HashItem nextPage = new HashItem(Material.ARROW)
            .setName(this.color + "Page suivante")
            .addLore(ChatColor.GRAY + "Cliquez pour afficher la page suivante.");

        final HashItem shopItem = this.shopManager.getShopItem(this.playerData, true);

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
     * Opens the GUI to a player.
     */
    public void open()
    {
        this.gui.open(this.player);
        this.gui.update(this.player);
    }

    /**
     * Loads articles.
     *
     * @apiNote MUST be overwritten by child classes.
     */
    public void loadArticles() {}

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
        for (ShopArticle article : articles)
            this.addArticle(article);
    }


    /**
     * @return  BreakFFA instance
     */
    public BreakFFA getMain()
    {
        return this.main;
    }

    /**
     * @return  Gui manager
     */
    public HashGuiManager getGuiManager()
    {
        return this.guiManager;
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
     * @return  Category title
     */
    public String getCategory()
    {
        return this.category;
    }

    /**
     * @return  Color (for strings)
     */
    public ChatColor getColor()
    {
        return this.color;
    }

    /**
     * @return  Primary color
     */
    public Byte getPrimaryColor()
    {
        return this.primaryColor;
    }

    /**
     * @return  Secondary color
     */
    public Byte getSecondaryColor()
    {
        return this.secondaryColor;
    }

    /**
     * @return  List of articles
     */
    public List<ShopArticle> getArticles()
    {
        return this.articles;
    }

}
