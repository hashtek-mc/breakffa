package fr.hashtek.spigot.breakffa.shop.article;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * TODO:
 *   - buy() function
 */
public class ShopArticle
{

    private final HashItem article;
    private final int price;

    private final BreakFFA main;


    public ShopArticle(HashItem article, int price, BreakFFA main)
    {
        this.article = article;
        this.price = price;

        this.main = main;

        this.updateArticleLore();
        this.setArticleClickHandler();
    }


    public void buy(PlayerData playerData)
    {
        final Player player = playerData.getPlayer();
        final int playerShards = playerData.getShards();

        if (playerShards < this.price) {
            player.sendMessage(ChatColor.RED + "Vous ne possédez pas assez de " + ChatColor.AQUA + "shards" + ChatColor.RED + ".");
            return;
        }

        playerData.setShards(playerShards - this.price);
        player.getInventory().addItem(this.article.getItemStack());
        player.sendMessage(ChatColor.AQUA + "Vous avez acheté " + this.article.getItemMeta().getDisplayName() + ChatColor.AQUA + ".");
    }

    private void updateArticleLore()
    {
        final List<String> articleLore = this.article.getItemMeta().getLore();

        articleLore.replaceAll((String line) -> line.replace("{price}", String.valueOf(price)));

        this.article.setLore(articleLore);
        this.article.build();
    }

    private void setArticleClickHandler()
    {
        this.article.addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                    final PlayerData playerData = this.main.getGameManager().getPlayerData(player);

                    this.buy(playerData);
                })
        );

        this.article.build(this.main.getGuiManager());
    }

    public HashItem getShopArticle()
    {
        return this.article;
    }

    public HashItem getArticle()
    {
        final HashItem article = new HashItem(this.article)
            .clearLore()
            .build();

        return this.article;
    }

    public int getPrice()
    {
        return this.price;
    }

}
