package fr.hashtek.spigot.breakffa.shop.article;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * TODO:
 *   - buy() function
 */
public class ShopArticle
{

    private final BreakFFA main;

    private final HashItem shopArticle;
    private final HashItem article;
    private final int price;


    public ShopArticle(HashItem article, int price, BreakFFA main)
    {
        this.main = main;
        this.article = article;
        this.shopArticle = new HashItem(article);

        this.price = price;

        this.initializeArticles();
    }


    private void initializeArticles()
    {
        String articleName = this.getShopArticle().getItemMeta().getDisplayName();

        articleName += " " + ChatColor.WHITE + ChatColor.BOLD + "●" + ChatColor.AQUA + " " + this.price + " shards";

        this.getShopArticle().setName(articleName);
        this.getShopArticle()
            .setFlags(Arrays.asList(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_UNBREAKABLE
            ))
            .build();

        this.setShopArticleClickHandler();

        this.article
            .setFlags(Arrays.asList(
                ItemFlag.HIDE_UNBREAKABLE,
                ItemFlag.HIDE_ATTRIBUTES
            ))
            .clearLore()
            .build();
    }

    public void buy(PlayerData playerData)
    {
        final Player player = playerData.getPlayer();
        final int playerShards = playerData.getShards();
        final int articlePrice = this.getPrice();

        final boolean hasEnoughShards = playerShards >= articlePrice;
        final boolean hasEnoughSpace = player.getInventory().firstEmpty() != -1;
        final boolean canBuy = hasEnoughShards && hasEnoughSpace;

        if (!canBuy) {
            player.playSound(player.getLocation(), "random.break", 100, 0);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 100, 0);

            if (!hasEnoughShards)
                player.sendMessage(ChatColor.RED + "Vous ne possédez pas assez de " + ChatColor.AQUA + "shards" + ChatColor.RED + ".");
            if (!hasEnoughSpace)
                player.sendMessage(ChatColor.RED + "Vous n'avez plus de place dans votre inventaire.");

            return;
        }

        playerData.setShards(playerShards - articlePrice);
        player.getInventory().addItem(this.getArticle().getItemStack());
        player.playSound(player.getLocation(), "mob.horse.leather", 100, 1);
        player.playSound(player.getLocation(), Sound.WOOD_CLICK, 100, 1);
        player.sendMessage(ChatColor.AQUA + "Vous avez acheté " + this.article.getItemMeta().getDisplayName() + ChatColor.AQUA + ".");
    }

    private void setShopArticleClickHandler()
    {
        this.getShopArticle().addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                    final PlayerData playerData = this.main.getGameManager().getPlayerData(player);

                    this.buy(playerData);
                })
        );

        this.getShopArticle().build(this.main.getGuiManager());
    }

    public HashItem getShopArticle()
    {
        return this.shopArticle;
    }

    public HashItem getArticle()
    {
        return this.article;
    }

    public int getPrice()
    {
        return this.price;
    }

}
