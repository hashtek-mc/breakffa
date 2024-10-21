package fr.hashtek.spigot.breakffa.gui;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.gui.cosmetics.categories.GuiCosmeticsCustomHelmet;
import fr.hashtek.spigot.breakffa.gui.cosmetics.categories.GuiCosmeticsKSFX;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GuiCosmetics extends HashGui
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();

    private static final Component GUI_TITLE = Component.text("cosmetics!!!!");
    private static final int SIZE = 6;


    private enum Items
    {

        ORANGE_SEP (HashItem.separator(Material.ORANGE_STAINED_GLASS_PANE, BreakFFA.getInstance().getGuiManager())),

        TITLE (new HashItem(Material.DARK_OAK_SIGN)
            .setName(Component.text("cosmétiques mdr"))
            .setUntakable(true)
            .build(GUI_MANAGER)
        ),

        KILL_SFX (GuiCosmeticsKSFX.TITLE_ITEM
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui hashGui, ItemStack item, int slot) -> {
                        if (!(hashGui instanceof GuiCosmetics gui)) {
                            return;
                        }

                        final CosmeticManager playerCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

                        new GuiCosmeticsKSFX(gui, playerCosmeticManager).open(player);
                    })
            )
            .build(GUI_TITLE, GUI_MANAGER)
        ),

        CUSTOM_HELMET (GuiCosmeticsCustomHelmet.TITLE_ITEM
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui hashGui, ItemStack item, int slot) -> {
                        if (!(hashGui instanceof GuiCosmetics gui)) {
                            return;
                        }

                        final CosmeticManager playerCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

                        new GuiCosmeticsCustomHelmet(gui, playerCosmeticManager).open(player);
                    })
            )
            .build(GUI_TITLE, GUI_MANAGER)
        );


        private final HashItem item;


        Items(HashItem item)
        {
            this.item = item;
        }


        public HashItem getItem()
        {
            return this.item;
        }

    }


    public GuiCosmetics()
    {
        super(GUI_TITLE, SIZE);
        this.createGui();
    }


    private void createGui()
    {
        final Mask mask = new Mask(this);

        mask
            .setItem('T', Items.TITLE.getItem())
            .setItem('o', Items.ORANGE_SEP.getItem())

            .setItem('K', Items.KILL_SFX.getItem())
            .setItem('H', Items.CUSTOM_HELMET.getItem());

        mask
            .pattern("ToKH     ");

        mask.apply();
    }

}
