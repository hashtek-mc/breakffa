package fr.hashtek.spigot.breakffa.shop;

import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShopManager
{

    private final Player player;
    private final PlayerData playerData;
    private final PlayerManager playerManager;

    private HashGui gui;

    private final HashGuiManager guiManager;


    public ShopManager(PlayerManager playerManager)
    {
        this.playerManager = playerManager;
        this.playerData = this.playerManager.getPlayerData();
        this.player = this.playerManager.getPlayer();
        this.guiManager = this.playerData.getMain().getGuiManager();

        this.createGui();
    }

    private void createGui()
    {
        this.gui = new HashGui("" + ChatColor.AQUA + ChatColor.BOLD + "Marché", 3);

        final HashItem defensiveCategory = new HashItem(Material.DIAMOND_HELMET)
            .setName("défensif lol")
            .build(this.guiManager);

        final HashItem offensiveCategory = new HashItem(Material.DIAMOND_HELMET)
            .setName("offensif lol")
            .build(this.guiManager);

        final HashItem supportCategory = new HashItem(Material.DIAMOND_HELMET)
            .setName("support lol")
            .build(this.guiManager);

        final Mask mask = new Mask(this.gui);

        mask.setItem('d', defensiveCategory)
            .setItem('o', offensiveCategory)
            .setItem('s', supportCategory);

        mask.pattern(2, "  d o s  ");

        mask.apply();
    }

    public HashItem getShopItem(boolean close)
    {
        return new HashItem(Material.NETHER_STAR)
            .setName(ChatColor.AQUA + "Marché Nexus" + ChatColor.GRAY + " (clic droit)")
            .setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "Vous possédez actuellement " + ChatColor.AQUA + this.playerData.getShards() + " shards" + ChatColor.GRAY + ".",
                ChatColor.GRAY + "Vos " + ChatColor.AQUA + "shards" + ChatColor.GRAY + " ne sont pas conservables.",
                "",
                ChatColor.GRAY + "Vous pouvez acheter des " + ChatColor.BLUE + "objets divers",
                ChatColor.GRAY + "grâce aux " + ChatColor.AQUA + "shards" + ChatColor.GRAY + " que vos obtenez",
                ChatColor.GRAY + "durant votre session.",
                "",
                ChatColor.GRAY + "Les objets procurent des " + ChatColor.YELLOW + "avantages" + ChatColor.GRAY + " et",
                ChatColor.GRAY + "sont " + ChatColor.YELLOW + "non-permanents" + ChatColor.GRAY + ".",
                ChatColor.GRAY + "Leur " + ChatColor.DARK_GREEN + "prix" + ChatColor.GRAY + " ne représente " + ChatColor.RED + "pas" + ChatColor.GRAY + " leur efficacité",
                ChatColor.GRAY + "et/ou leur utilité en " + ChatColor.WHITE + "jeu" + ChatColor.GRAY + ".",
                "",
                "" + ChatColor.AQUA + ChatColor.BOLD + "CLIC DROIT POUR " + (close ? "FERMER" : "OUVRIR")
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui hashGui, ItemStack item, int slot) -> {
                        this.gui.open(p);
                    })
            )
            .build(this.guiManager);
    }

}
