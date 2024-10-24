package fr.hashtek.spigot.breakffa.gui;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticAvailability;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKillSfx;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.categories.GuiCosmeticsHat;
import fr.hashtek.spigot.breakffa.gui.cosmetics.categories.GuiCosmeticsKillSfx;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import fr.hashtek.tekore.common.constants.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.Arrays;

public class GuiCosmetics
    extends PaginatedHashGui
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();

    private static final Component GUI_TITLE = Component.text(
        ChatColor.AQUA + "Cosmétiques - Menu"
    );
    private static final int GUI_SIZE = 6;


    public GuiCosmetics(Player player)
    {
        super(GUI_TITLE, GUI_SIZE, MAIN.getGuiManager());

        final CosmeticManager playerCosmeticManager =
            MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

        this.createGui(player, playerCosmeticManager);
    }


    private void createGui(Player player, CosmeticManager playerCosmeticManager)
    {
        final Mask mask = new Mask(this);

        /* Separators -------------------------------------------------------------------------------------- */
        final HashItem whiteSep = HashItem.separator(Material.WHITE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem lightBlueSep = HashItem.separator(Material.LIGHT_BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        final HashItem blueSep = HashItem.separator(Material.BLUE_STAINED_GLASS_PANE, GUI_MANAGER);
        /* ------------------------------------------------------------------------------------------------- */

        /* Previous/Next page item ------------------------------------------------------------------------- */
        final HashItem previousPageItem = new HashItem(Material.ARROW)
            .setName(Component.text(ChatColor.AQUA + "Page précédente"))
            .addLore(Component.text(ChatColor.GRAY + "Cliquez pour afficher la page précédente."));

        final HashItem nextPageItem = new HashItem(Material.ARROW)
            .setName(Component.text(ChatColor.AQUA + "Page suivante"))
            .addLore(Component.text(ChatColor.GRAY + "Cliquez pour afficher la page suivante."));

        super.setPreviousPageItem(previousPageItem);
        super.setNextPageItem(nextPageItem);
        /* ------------------------------------------------------------------------------------------------- */

        /* Cosmetic categories items ----------------------------------------------------------------------- */
        final HashItem ksfxItem = GuiCosmeticsKillSfx.CATEGORY_ATTRIBUTES.createTitleItem(playerCosmeticManager, CosmeticTypeKillSfx.class);
        final HashItem customHelmetItem = GuiCosmeticsHat.CATEGORY_ATTRIBUTES.createTitleItem(playerCosmeticManager, CosmeticTypeHat.class);

        ksfxItem
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui hashGui, ItemStack item, int slot) -> {
                        if (!(hashGui instanceof GuiCosmetics gui)) {
                            return;
                        }

                        final CosmeticManager pCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(p).getCosmeticManager();

                        new GuiCosmeticsKillSfx(gui, pCosmeticManager).open(p);
                    })
            )
            .build(GUI_TITLE, GUI_MANAGER);

        customHelmetItem
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player p, HashGui hashGui, ItemStack item, int slot) -> {
                        if (!(hashGui instanceof GuiCosmetics gui)) {
                            return;
                        }

                        final CosmeticManager pCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(p).getCosmeticManager();

                        new GuiCosmeticsHat(gui, pCosmeticManager).open(p);
                    })
            )
            .build(GUI_TITLE, GUI_MANAGER);
        /* ------------------------------------------------------------------------------------------------- */

        mask.setItem('w', whiteSep)
            .setItem('l', lightBlueSep)
            .setItem('b', blueSep)

            .setItem('<', previousPageItem)
            .setItem('>', nextPageItem)

            .setItem('T', this.getTitleItem(player, playerCosmeticManager));

        mask.pattern("wlbbbbblw")
            .pattern("bwlllllwb")
            .pattern("l       l")
            .pattern("l       l")
            .pattern("bwlllllwb")
            .pattern("wlb<T>blw");

        mask.apply();

        super.getCurrentPage().addItem(ksfxItem);
        super.getCurrentPage().addItem(customHelmetItem);
    }


    private HashItem getTitleItem(Player player, CosmeticManager playerCosmeticManager)
    {
        final String playerHashCoins = NumberFormat.getInstance().format(MAIN.getCore().getPlayerManagersManager().getPlayerManager(player).getAccount().getHashCoins());

        return new HashItem(Material.EMERALD)
            .setName(Component.text("" + ChatColor.AQUA + ChatColor.BOLD + "Cosmétiques du BreakFFA"))
            .addLore(Arrays.asList(
                Component.text(ChatColor.GRAY + "Les " + ChatColor.AQUA + "cosmétiques" + ChatColor.GRAY + " du " + ChatColor.RED + "BreakFFA" + ChatColor.GRAY + " sont obtensibles"),
                Component.text(ChatColor.GRAY + "via des " + ChatColor.GOLD + "Coins" + ChatColor.GRAY + ", " + ChatColor.YELLOW + "HashCoins" + ChatColor.GRAY + " ou lors d'occasions spéciales,"),
                Component.text(ChatColor.GRAY + "comme des " + ChatColor.YELLOW + "évenements" + ChatColor.GRAY + ", des " + ChatColor.GOLD + "offres limitées" + ChatColor.GRAY + " ou"),
                Component.text(ChatColor.GRAY + "lors de " + ChatColor.LIGHT_PURPLE + "giveaways" + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text("" + ChatColor.GRAY + ChatColor.BOLD + "→ " + ChatColor.YELLOW + ChatColor.BOLD + "Vous" + ChatColor.GRAY + " possédez actuellement :"),
                Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "► " + GuiCosmeticsCategoryAttributes.getCosmeticRatio(playerCosmeticManager, CosmeticAvailability.OBTAINABLE) + ChatColor.GRAY + " Cosmétiques obtensibles"),
                Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "► " + GuiCosmeticsCategoryAttributes.getCosmeticRatio(playerCosmeticManager, CosmeticAvailability.LIMITED) +  ChatColor.GRAY + " Cosmétiques limités"),
                Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "► " + GuiCosmeticsCategoryAttributes.getCosmeticRatio(playerCosmeticManager, CosmeticAvailability.EXCLUSIVE) + ChatColor.GRAY + " Cosmétiques exclusifs"),
                Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "► " + ChatColor.GRAY + " Coins"),
                Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "► " + ChatColor.YELLOW + playerHashCoins + " " + Constants.HASHCOINS_SYMBOL + ChatColor.GRAY + " HashCoins"),
                Component.text(""),
                Component.text(ChatColor.DARK_GRAY + "Cliquez pour fermer")
            ))
            .setUntakable(true)
            .build(/*GUI_TITLE, */GUI_MANAGER);
    }

}
