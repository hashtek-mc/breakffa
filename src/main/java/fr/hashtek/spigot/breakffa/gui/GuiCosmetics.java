package fr.hashtek.spigot.breakffa.gui;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.gui.category.categories.GuiCosmeticsCustomHelmet;
import fr.hashtek.spigot.breakffa.gui.category.categories.GuiCosmeticsKSFX;
import fr.hashtek.spigot.breakffa.player.PlayerData;
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


    private enum Items
    {
        ORANGE_SEP (HashItem.separator(Material.ORANGE_STAINED_GLASS_PANE, BreakFFA.getInstance().getGuiManager())),

        TITLE (new HashItem(Material.DARK_OAK_SIGN)
            .setName(Component.text("cosmétiques mdr"))
            .setUntakable(true)
            .build(GUI_MANAGER)
        ),

        // TODO: Maybe put the current kill sfx item.
        KILL_SFX (new HashItem(Material.IRON_SWORD)
            .setName(Component.text("kill sfx"))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        final CosmeticManager playerCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

                        new GuiCosmeticsKSFX(playerCosmeticManager).open(player);
                    })
            )
            .build(GUI_MANAGER)
        ),

        // TODO: Maybe put the current custom helmet item.
        CUSTOM_HELMET (new HashItem(Material.PLAYER_HEAD)
            .setName(Component.text("Custom helmets"))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        final CosmeticManager playerCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

                        new GuiCosmeticsCustomHelmet(playerCosmeticManager).open(player);
                    })
            )
            .build(GUI_MANAGER)
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


    public GuiCosmetics(Player player)
    {
        super(
            Component.text("cosmetics!!!!"),
            6
        );

        final BreakFFA main = BreakFFA.getInstance();
        final GameManager gameManager = main.getGameManager();
        final PlayerData playerData = gameManager.getPlayerManager(player).getData();

//        CosmeticManager cosmeticManager = playerData.getPlayerManager().getCosmeticManager();

        this.createGui();
    }


    private void createGui()
    {
        Mask mask = new Mask(this);

        mask
            .setItem('T', Items.TITLE.getItem())
            .setItem('K', Items.KILL_SFX.getItem())
            .setItem('H', Items.CUSTOM_HELMET.getItem())
            .setItem('o', Items.ORANGE_SEP.getItem());

        mask
            .pattern("ToKH     ");

        mask.apply();
    }

}
