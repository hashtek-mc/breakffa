package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.breakffa.shop.article.ShopArticle;
import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashgui.page.HashPage;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopCategory
{

    private final Player player;
    private final PlayerData playerData;
    private final PlayerManager playerManager;
    private final ShopManager shopManager;

    private PaginatedHashGui gui;

    private final HashGuiManager guiManager;

    private final String category;
    private final ChatColor color;
    private final Byte primaryColor;
    private final Byte secondaryColor;


    public ShopCategory(
        PlayerManager playerManager,
        String category,
        ChatColor color,
        Byte primaryColor,
        Byte secondaryColor
    )
    {
        this.playerManager = playerManager;
        this.shopManager = this.playerManager.getShopManager();
        this.playerData = this.playerManager.getPlayerData();
        this.player = this.playerManager.getPlayer();

        this.guiManager = this.playerData.getMain().getGuiManager();

        this.category = category;
        this.color = color;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;

        this.createGui();
    }


    public void createGui()
    {
        this.gui = new PaginatedHashGui(
            "" + this.color + ChatColor.BOLD + "Marché " + ChatColor.WHITE + ChatColor.BOLD + "●" + this.color + ChatColor.BOLD + " " + this.category,
            6
        );

        final HashItem primaryGlass = HashItem.separator(this.primaryColor, this.guiManager);
        final HashItem secondaryGlass = HashItem.separator(this.secondaryColor, this.guiManager);

        final HashItem previousPage = new HashItem(Material.ARROW)
            .setName(this.color + "Page précédente")
            .addLore(ChatColor.GRAY + "Cliquez pour afficher la page précédente.");

        final HashItem nextPage = new HashItem(Material.ARROW)
            .setName(this.color + "Page suivante")
            .addLore(ChatColor.GRAY + "Cliquez pour afficher la page suivante.");

        final HashItem shopItem = this.shopManager.getShopItem(true);

        this.gui.setPreviousPageItem(previousPage, this.guiManager);
        this.gui.setNextPageItem(nextPage, this.guiManager);

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

    public void open()
    {
        this.gui.open(this.player);
        this.gui.update(this.player);
    }

    public void addArticle(ShopArticle article)
    {
        final HashPage lastPage = this.gui.getLastPage();

        try {
            lastPage.addItem(article.getShopArticle());
        } catch (IllegalArgumentException unused) {
            this.gui.createNewPage();
            this.addArticle(article);
        }
    }

    public void addArticles(List<ShopArticle> articles)
    {
        for (ShopArticle article : articles)
            this.addArticle(article);
    }

}
