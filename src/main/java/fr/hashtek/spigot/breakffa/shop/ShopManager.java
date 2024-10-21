package fr.hashtek.spigot.breakffa.shop;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryItem;
import fr.hashtek.spigot.breakffa.shop.category.categories.ShopCategoryDefensive;
import fr.hashtek.spigot.breakffa.shop.category.categories.ShopCategoryOffensive;
import fr.hashtek.spigot.breakffa.shop.category.categories.ShopCategorySupport;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.text.NumberFormat;
import java.util.Arrays;

public class ShopManager
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();

    private HashGui gui;


    /**
     * Creates a new instance of ShopManager
     */
    public ShopManager()
    {
        this.createGui();
    }


    /**
     * Creates the base shop GUIs.
     * TODO: Put all static items in static enum
     */
    private void createGui()
    {
        this.gui = new HashGui(Component.text("" + ChatColor.AQUA + ChatColor.BOLD + "Marché ● CATÉGORIES"), 3);

        final HashItem darkBlueGlass = HashItem.separator(Material.BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem blueGlass = HashItem.separator(Material.LIGHT_BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem orangeGlass = HashItem.separator(Material.ORANGE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem redGlass = HashItem.separator(Material.RED_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem greenGlass = HashItem.separator(Material.LIME_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem darkGreenGlass = HashItem.separator(Material.GREEN_STAINED_GLASS_PANE, GUI_MANAGER);

        final HashItem defensiveCategory = new ShopCategoryItem(
            Material.CHAINMAIL_CHESTPLATE,
            Component.text(ChatColor.BLUE + "Catégorie Défensive"),
            Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "Cette catégorie contient l'ensemble des objets"),
                Component.text(ChatColor.GRAY + "visant à vous " + ChatColor.BLUE + "protéger" + ChatColor.GRAY + ", que cela soit"),
                Component.text(ChatColor.GRAY + "des types d'" + ChatColor.YELLOW + "armures" + ChatColor.GRAY + ", des " + ChatColor.YELLOW + "structures" + ChatColor.GRAY + ", ou autre."),
                Component.text(""),
                Component.text("" + ChatColor.BLUE + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR")
            ),
            (Player p, HashGui gui, ItemStack item, int slot) ->
                new ShopCategoryDefensive(p).open()
        ).build(GUI_MANAGER);

        final HashItem offensiveCategory = new ShopCategoryItem(
            Material.IRON_SWORD,
            Component.text(ChatColor.RED + "Catégorie Offensive"),
            Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "Cette catégorie contient l'ensemble des " + ChatColor.RED + "armes" + ChatColor.GRAY + ","),
                Component.text(ChatColor.YELLOW + "blanches" + ChatColor.GRAY + " ou à " + ChatColor.YELLOW + "distance" + ChatColor.GRAY + ", et autres objets ayant"),
                Component.text(ChatColor.GRAY + "pour but de " + ChatColor.RED + "blesser" + ChatColor.GRAY + " votre " + ChatColor.WHITE + "cible" + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text("" + ChatColor.RED + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR")
            ),
            (Player p, HashGui gui, ItemStack item, int slot) ->
                new ShopCategoryOffensive(p).open()
        ).build(GUI_MANAGER);

        final HashItem supportCategory = new ShopCategoryItem(
            Material.GOLDEN_APPLE,
                Component.text(ChatColor.GREEN + "Catégorie Supportive"),
            Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "Cette catégorie représente les objets"),
                Component.text(ChatColor.GRAY + "impliqués dans le " + ChatColor.GREEN + "support" + ChatColor.GRAY + ", permettant donc"),
                Component.text(ChatColor.GRAY + "de vous aider " + ChatColor.DARK_GREEN + "vous ou vos alliés" + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text(ChatColor.GRAY + "On y retrouve également le " + ChatColor.BLUE + "reste" + ChatColor.GRAY + " des"),
                Component.text(ChatColor.GRAY + "objets " + ChatColor.YELLOW + "divers" + ChatColor.GRAY + " et " + ChatColor.YELLOW + "variés" + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text("" + ChatColor.GREEN + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR")
            ),
            (Player p, HashGui gui, ItemStack item, int slot) ->
                new ShopCategorySupport(p).open()
        ).build(GUI_MANAGER);

        final Mask mask = new Mask(this.gui);

        mask.setItem('B', darkBlueGlass)
            .setItem('b', blueGlass)
            .setItem('O', orangeGlass)
            .setItem('r', redGlass)
            .setItem('g', greenGlass)
            .setItem('G', darkGreenGlass)
            .setItem('d', defensiveCategory)
            .setItem('o', offensiveCategory)
            .setItem('s', supportCategory);

        mask.pattern("BbBbOrOgG")
            .pattern("bBdOoOsGg")
            .pattern("BbOrOgGgG");

        mask.apply();
    }

    /**
     * Builds the shop item.
     *
     * @param   playerData  Player's data
     * @param   close       Should item close the shop or open it (only visual, in the item's lore)
     * @return  Built item.
     */
    public HashItem createShopItem(PlayerData playerData, boolean close)
    {
        return new HashItem(Material.NETHER_STAR)
            .setName(Component.text(ChatColor.AQUA + "Marché Nexus" + ChatColor.GRAY + " (clic gauche)"))
            .setLore(Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "Vous possédez actuellement " + ChatColor.AQUA + NumberFormat.getInstance().format(playerData.getShards()) + " shard" + (playerData.getShards() == 1 ? "" : "s") + ChatColor.GRAY + "."),
                Component.text(ChatColor.GRAY + "Vos " + ChatColor.AQUA + "shards" + ChatColor.GRAY + " ne sont pas conservables."),
                Component.text(""),
                Component.text(ChatColor.GRAY + "Vous pouvez acheter des " + ChatColor.BLUE + "objets divers"),
                Component.text(ChatColor.GRAY + "grâce aux " + ChatColor.AQUA + "shards" + ChatColor.GRAY + " que vos obtenez"),
                Component.text(ChatColor.GRAY + "durant votre session."),
                Component.text(""),
                Component.text(ChatColor.GRAY + "Les objets procurent des " + ChatColor.YELLOW + "avantages" + ChatColor.GRAY + " et"),
                Component.text(ChatColor.GRAY + "sont " + ChatColor.YELLOW + "non-permanents" + ChatColor.GRAY + "."),
                Component.text(ChatColor.GRAY + "Leur " + ChatColor.DARK_GREEN + "prix" + ChatColor.GRAY + " ne représente " + ChatColor.RED + "pas" + ChatColor.GRAY + " leur efficacité"),
                Component.text(ChatColor.GRAY + "et/ou leur utilité en " + ChatColor.WHITE + "jeu" + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text("" + ChatColor.AQUA + ChatColor.BOLD + "CLIC GAUCHE POUR " + (close ? "FERMER" : "OUVRIR"))
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui hashGui, ItemStack item, int slot) -> {
                        this.gui.open(p);
                    })
            )
            .build(GUI_MANAGER);
    }

    /**
     * Gives the shop to a player in its inventory.
     */
    public void giveShop(Player player)
    {
        final PlayerData playerData = MAIN.getGameManager().getPlayerManager(player).getData();
        final PlayerInventory inventory = player.getInventory();

        final HashItem darkBlueGlass = HashItem.separator(Material.BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem blueGlass = HashItem.separator(Material.LIGHT_BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem magentaGlass = HashItem.separator(Material.MAGENTA_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem whiteGlass = HashItem.separator(Material.WHITE_STAINED_GLASS_PANE, GUI_MANAGER);

        final HashItem shopItem = this.createShopItem(playerData, false);

        final Mask mask = new Mask(inventory);

        mask.setItem('d', darkBlueGlass)
            .setItem('b', blueGlass)
            .setItem('m', magentaGlass)
            .setItem('w', whiteGlass)
            .setItem('s', shopItem);

        mask.pattern(2, "ddbmwmbdd")
            .pattern(3, "dbmwswmbd")
            .pattern(4, "ddbmwmbdd");

        mask.apply();
    }

}
