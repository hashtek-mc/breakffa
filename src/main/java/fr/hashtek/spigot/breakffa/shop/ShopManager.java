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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class ShopManager
{

    private final BreakFFA main;
    private final HashGuiManager guiManager;

    private HashGui gui;


    /**
     * Creates a new instance of ShopManager
     *
     * @param   main    BreakFFA instance
     */
    public ShopManager(BreakFFA main)
    {
        this.main = main;
        this.guiManager = this.main.getGuiManager();

        this.createGui();
    }


    /**
     * Creates the base shop GUIs.
     */
    private void createGui()
    {
        this.gui = new HashGui("" + ChatColor.AQUA + ChatColor.BOLD + "Marché ● CATÉGORIES", 3);

        final HashItem darkBlueGlass = HashItem.separator((byte) 11, this.guiManager);
        final HashItem blueGlass = HashItem.separator((byte) 3, this.guiManager);
        final HashItem orangeGlass = HashItem.separator((byte) 1, this.guiManager);
        final HashItem redGlass = HashItem.separator((byte) 14, this.guiManager);
        final HashItem greenGlass = HashItem.separator((byte) 5, this.guiManager);
        final HashItem darkGreenGlass = HashItem.separator((byte) 13, this.guiManager);

        final HashItem defensiveCategory = new ShopCategoryItem(
            Material.CHAINMAIL_CHESTPLATE,
            ChatColor.BLUE + "Catégorie Défensive",
            Arrays.asList(
                "",
                ChatColor.GRAY + "Cette catégorie contient l'ensemble des objets",
                ChatColor.GRAY + "visant à vous " + ChatColor.BLUE + "protéger" + ChatColor.GRAY + ", que cela soit",
                ChatColor.GRAY + "des types d'" + ChatColor.YELLOW + "armures" + ChatColor.GRAY + ", des " + ChatColor.YELLOW + "structures" + ChatColor.GRAY + ", ou autre.",
                "",
                "" + ChatColor.BLUE + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR"
            ),
            (Player p, HashGui gui, ItemStack item, int slot) -> {
                new ShopCategoryDefensive(this.main, p).open();
            }
        ).build(this.guiManager);

        final HashItem offensiveCategory = new ShopCategoryItem(
            Material.IRON_SWORD,
            ChatColor.RED + "Catégorie Offensive",
            Arrays.asList(
                "",
                ChatColor.GRAY + "Cette catégorie contient l'ensemble des " + ChatColor.RED + "armes" + ChatColor.GRAY + ",",
                ChatColor.YELLOW + "blanches" + ChatColor.GRAY + " ou à " + ChatColor.YELLOW + "distance" + ChatColor.GRAY + ", et autres objets ayant",
                ChatColor.GRAY + "pour but de " + ChatColor.RED + "blesser" + ChatColor.GRAY + " votre " + ChatColor.WHITE + "cible" + ChatColor.GRAY + ".",
                "",
                "" + ChatColor.RED + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR"
            ),
            (Player p, HashGui gui, ItemStack item, int slot) -> {
                new ShopCategoryOffensive(this.main, p).open();
            }
        ).build(this.guiManager);

        final HashItem supportCategory = new ShopCategoryItem(
            Material.GOLDEN_APPLE,
            ChatColor.GREEN + "Catégorie Supportive",
            Arrays.asList(
                "",
                ChatColor.GRAY + "Cette catégorie représente les objets",
                ChatColor.GRAY + "impliqués dans le " + ChatColor.GREEN + "support" + ChatColor.GRAY + ", permettant donc",
                ChatColor.GRAY + "de vous aider " + ChatColor.DARK_GREEN + "vous ou vos alliés" + ChatColor.GRAY + ".",
                "",
                ChatColor.GRAY + "On y retrouve également le " + ChatColor.BLUE + "reste" + ChatColor.GRAY + " des",
                ChatColor.GRAY + "objets " + ChatColor.YELLOW + "divers" + ChatColor.GRAY + " et " + ChatColor.YELLOW + "variés" + ChatColor.GRAY + ".",
                "",
                "" + ChatColor.GREEN + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR"
            ),
            (Player p, HashGui gui, ItemStack item, int slot) -> {
                new ShopCategorySupport(this.main, p).open();
            }
        ).build(this.guiManager);

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
     * @return  Returns built item.
     */
    public HashItem createShopItem(PlayerData playerData, boolean close)
    {
        return new HashItem(Material.NETHER_STAR)
            .setName(ChatColor.AQUA + "Marché Nexus" + ChatColor.GRAY + " (clic gauche)")
            .setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "Vous possédez actuellement " + ChatColor.AQUA + playerData.getShards() + " shards" + ChatColor.GRAY + ".",
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

    /**
     * Gives the shop to a player in its inventory.
     *
     * @param   playerData  Player's data
     */
    public void giveShop(PlayerData playerData)
    {
        final Player player = playerData.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final HashGuiManager guiManager = this.main.getGuiManager();

        final HashItem darkBlueGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 9)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem blueGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 3)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem magentaGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 2)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

        final HashItem whiteGlass =
            new HashItem(Material.STAINED_GLASS_PANE, 1, (byte) 0)
                .setName("")
                .setUntakable(true)
                .build(guiManager);

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
