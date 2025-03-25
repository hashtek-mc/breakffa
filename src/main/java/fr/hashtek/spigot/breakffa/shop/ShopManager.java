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

public class ShopManager {

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();
    private HashGui gui;

    /**
     * Creates a new instance of ShopManager
     */
    public ShopManager() {
        this.createGui();
    }

    /**
     * Creates the base shop GUIs.
     */
    private void createGui() {
        this.gui = new HashGui(Component.text("" + ChatColor.AQUA + ChatColor.BOLD + "Shop ● CATEGORIES"), 3);
        initializeCategories();
    }

    /**
     * Initializes shop categories and adds them to the GUI.
     */
    private void initializeCategories() {
        final HashItem darkBlueGlass = HashItem.separator(Material.BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem blueGlass = HashItem.separator(Material.LIGHT_BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem orangeGlass = HashItem.separator(Material.ORANGE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem redGlass = HashItem.separator(Material.RED_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem greenGlass = HashItem.separator(Material.LIME_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem darkGreenGlass = HashItem.separator(Material.GREEN_STAINED_GLASS_PANE, GUI_MANAGER);

        addCategoryToGui(new ShopCategoryItem(
            Material.CHAINMAIL_CHESTPLATE,
            Component.text(ChatColor.BLUE + "Defensive Category"),
            Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "This category contains items to protect you."),
                Component.text(""),
                Component.text("" + ChatColor.BLUE + ChatColor.BOLD + "LEFT CLICK TO OPEN")
            ),
            (Player p, HashGui gui, ItemStack item, int slot) -> new ShopCategoryDefensive(p).open()
        ), darkBlueGlass);

        addCategoryToGui(new ShopCategoryItem(
            Material.IRON_SWORD,
            Component.text(ChatColor.RED + "Offensive Category"),
            Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "This category contains weapons to attack."),
                Component.text(""),
                Component.text("" + ChatColor.RED + ChatColor.BOLD + "LEFT CLICK TO OPEN")
            ),
            (Player p, HashGui gui, ItemStack item, int slot) -> new ShopCategoryOffensive(p).open()
        ), redGlass);

        addCategoryToGui(new ShopCategoryItem(
            Material.GOLDEN_APPLE,
            Component.text(ChatColor.GREEN + "Supportive Category"),
            Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "This category contains supportive items."),
                Component.text(""),
                Component.text("" + ChatColor.GREEN + ChatColor.BOLD + "LEFT CLICK TO OPEN")
            ),
            (Player p, HashGui gui, ItemStack item, int slot) -> new ShopCategorySupport(p).open()
        ), greenGlass);
    }

    /**
     * Adds a category item to the GUI.
     *
     * @param categoryItem The category item to add
     * @param separator    The separator item to use
     */
    private void addCategoryToGui(ShopCategoryItem categoryItem, HashItem separator) {
        final Mask mask = new Mask(this.gui);
        mask.setItem('c', separator)
            .setItem('i', categoryItem.build(GUI_MANAGER));

        mask.pattern("cic");
        mask.apply();
    }

    /**
     * Builds the shop item.
     *
     * @param playerData Player's data
     * @param close      Should item close the shop or open it
     * @return Built item.
     */
    public HashItem createShopItem(PlayerData playerData, boolean close) {
        return new HashItem(Material.NETHER_STAR)
            .setName(Component.text(ChatColor.AQUA + "Shop Nexus" + ChatColor.GRAY + " (left click)"))
            .setLore(Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "You currently have " + ChatColor.AQUA + NumberFormat.getInstance().format(playerData.getShards()) + " shard" + (playerData.getShards() == 1 ? "" : "s") + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text("" + ChatColor.AQUA + ChatColor.BOLD + "LEFT CLICK TO " + (close ? "CLOSE" : "OPEN"))
            ))
            .addClickHandler(new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player p, HashGui gui, ItemStack item, int slot) -> this.gui.open(p)))
            .build(GUI_MANAGER);
    }

    /**
     * Gives the shop to a player in their inventory.
     */
    public void giveShop(Player player) {
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

        mask.pattern("ddbmwmbd")
            .pattern("dbmwswmbd")
            .pattern("ddbmwmbd");

        mask.apply();
    }

    /**
     * Opens the shop GUI for a player.
     *
     * @param player Player to open the shop for
     */
    public void openShopForPlayer(Player player) {
        this.gui.open(player);
    }

    /**
     * Refreshes the shop GUI for a player.
     *
     * @param player Player to refresh the shop for
     */
    public void refreshShopForPlayer(Player player) {
        // Logic to refresh the shop GUI for the player
        this.gui.update(player);
    }

    /**
     * Closes the shop GUI for a player.
     *
     * @param player Player to close the shop for
     */
    public void closeShopForPlayer(Player player) {
        this.gui.close(player);
    }
}
