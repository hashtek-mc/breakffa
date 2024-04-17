package fr.hashtek.spigot.breakffa.shop;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
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

import java.util.Arrays;

public class ShopManager
{

    private final BreakFFA main;
    private final HashGuiManager guiManager;

    private HashGui gui;


    public ShopManager(BreakFFA main)
    {
        this.main = main;
        this.guiManager = this.main.getGuiManager();

        this.createGui();
    }

    private void createGui()
    {
        this.gui = new HashGui("" + ChatColor.AQUA + ChatColor.BOLD + "Marché ● CATÉGORIES", 3);

        final HashItem darkBlueGlass = HashItem.separator((byte) 11, this.guiManager);
        final HashItem blueGlass = HashItem.separator((byte) 3, this.guiManager);
        final HashItem orangeGlass = HashItem.separator((byte) 1, this.guiManager);
        final HashItem redGlass = HashItem.separator((byte) 14, this.guiManager);
        final HashItem greenGlass = HashItem.separator((byte) 5, this.guiManager);
        final HashItem darkGreenGlass = HashItem.separator((byte) 13, this.guiManager);

        final HashItem defensiveCategory = new HashItem(Material.CHAINMAIL_CHESTPLATE)
            .setName(ChatColor.BLUE + "Catégorie Défensive")
            .setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "Cette catégorie contient l'ensemble des objets",
                ChatColor.GRAY + "visant à vous " + ChatColor.BLUE + "protéger" + ChatColor.GRAY + ", que cela soit",
                ChatColor.GRAY + "des types d'" + ChatColor.YELLOW + "arumures" + ChatColor.GRAY + ", des " + ChatColor.YELLOW + "structures" + ChatColor.GRAY + ", ou autre.",
                "",
                "" + ChatColor.BLUE + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR"
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui gui, ItemStack item, int slot) -> {
                        new ShopCategoryDefensive(this.main, p).open();
                    })
            )
            .build(this.guiManager);

        final HashItem offensiveCategory = new HashItem(Material.IRON_SWORD)
            .setName(ChatColor.RED + "Catégorie Offensive")
            .setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "Cette catégorie contient l'ensemble des " + ChatColor.RED + "armes" + ChatColor.GRAY + ",",
                ChatColor.YELLOW + "blanches" + ChatColor.GRAY + " ou à " + ChatColor.YELLOW + "distance" + ChatColor.GRAY + ", et autres objets ayant",
                ChatColor.GRAY + "pour but de " + ChatColor.RED + "blesser" + ChatColor.GRAY + " votre " + ChatColor.WHITE + "cible" + ChatColor.GRAY + ".",
                "",
                "" + ChatColor.RED + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR"
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui gui, ItemStack item, int slot) -> {
                        new ShopCategoryOffensive(this.main, p).open();
                    })
            )
            .build(this.guiManager);

        final HashItem supportCategory = new HashItem(Material.GOLDEN_APPLE)
            .setName(ChatColor.GREEN + "Catégorie Supportive")
            .setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "Cette catégorie représente les objets",
                ChatColor.GRAY + "impliqués dans le " + ChatColor.GREEN + "support" + ChatColor.GRAY + ", permettant donc",
                ChatColor.GRAY + "de vous aider " + ChatColor.DARK_GREEN + "vous ou vos alliés" + ChatColor.GRAY + ".",
                "",
                ChatColor.GRAY + "On y retrouve également le " + ChatColor.BLUE + "reste" + ChatColor.GRAY + " des",
                ChatColor.GRAY + "objets  " + ChatColor.YELLOW + "divers" + ChatColor.GRAY + " et " + ChatColor.YELLOW + "variés" + ChatColor.GRAY + ".",
                "",
                "" + ChatColor.GREEN + ChatColor.BOLD + "CLIC GAUCHE POUR OUVRIR"
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui gui, ItemStack item, int slot) -> {
                        new ShopCategorySupport(this.main, p).open();
                    })
            )
            .build(this.guiManager);

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

    public HashItem getShopItem(PlayerData playerData, boolean close)
    {
        return new HashItem(Material.NETHER_STAR)
            .setName(ChatColor.AQUA + "Marché Nexus" + ChatColor.GRAY + " (clic droit)")
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

}
