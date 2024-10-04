package fr.hashtek.spigot.breakffa.gui.cosmetics;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class GuiCosmeticBuy<
    T extends AbstractCosmetic,
    E extends Enum<E> & CosmeticCategoryArticles<T>
> extends HashGui
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();


    private enum Items
    {

        ORANGE_SEP (HashItem.separator(Material.ORANGE_STAINED_GLASS_PANE, BreakFFA.getInstance().getGuiManager())),
        RED_SEP (HashItem.separator(Material.RED_STAINED_GLASS_PANE, BreakFFA.getInstance().getGuiManager()));


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


    private static final Component TITLE =
        Component.text("buy???");
    private static final int SIZE = 3;


    private final GuiCosmeticsCategory<T, E> parentGui;
    private final Supplier<List<Cosmetic<T>>> ownedCosmeticsGetter;
    private final CosmeticManager cosmeticManager;


    public GuiCosmeticBuy(
        GuiCosmeticsCategory<T, E> parentGui,
        Cosmetic<T> cosmetic,
        Supplier<List<Cosmetic<T>>> ownedCosmeticsGetter,
        CosmeticManager cosmeticManager,
        CosmeticManager.CosmeticSetter<Cosmetic<T>> currentCosmeticSetter
    )
    {
        super(TITLE, SIZE);

        this.parentGui = parentGui;
        this.ownedCosmeticsGetter = ownedCosmeticsGetter;
        this.cosmeticManager = cosmeticManager;

        this.createGui(cosmetic);
    }


    private void createGui(Cosmetic<T> cosmetic)
    {
        final HashItem cosmeticItem = new HashItem(cosmetic.getMaterial())
            .setName(Component.text(cosmetic.getName()))
            .addLore(Component.text(cosmetic.getDescription()))
            .setUntakable(true)
            .build(this, GUI_MANAGER);

        final HashItem buyItem = new HashItem(Material.LIME_DYE)
            .setName(Component.text("buy!!"))
            .addLore(Component.text("it will only cost " + cosmetic.getPrice()))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        this.ownedCosmeticsGetter.get().add(cosmetic);

                        // set current cosmetic

                        parentGui.reloadGui(parentGui, cosmeticManager);
                        parentGui.open(player);
                    })
            )
            .build(this, GUI_MANAGER);

        final HashItem abortItem = new HashItem(Material.RED_DYE)
            .setName(Component.text("NO!!!"))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        parentGui.open(player);
                    })
            )
            .build(this, GUI_MANAGER);

        final Mask mask = new Mask(this);

        mask.setItem('o', Items.ORANGE_SEP.getItem())
            .setItem('r', Items.RED_SEP.getItem())
            .setItem('C', cosmeticItem)
            .setItem('B', buyItem)
            .setItem('A', abortItem);

        mask.pattern("oorrorroo")
            .pattern("orArCrBro")
            .pattern("oorrorroo");

        mask.apply();
    }
}
