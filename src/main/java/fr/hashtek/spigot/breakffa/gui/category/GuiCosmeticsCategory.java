package fr.hashtek.spigot.breakffa.gui.category;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.PaginatedHashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashgui.page.Page;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GuiCosmeticsCategory<
    T extends AbstractCosmetic,
    E extends Enum<E> & CosmeticCategoryArticles<T>
> extends PaginatedHashGui
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();

    private static final int SIZE = 6;


    private final CosmeticManager.CosmeticGetter<Cosmetic<T>> currentCosmeticGetter;
    private final CosmeticManager.CosmeticSetter<Cosmetic<T>> currentCosmeticSetter;
    private final Class<E> cosmetics;

    private final Mask mask;
    private final GuiCosmeticsCategoryAttributes attributes;


    /**
     * Creates a new instance of AbstractGuiCosmeticsCategory.
     *
     * @apiNote titleItGuiCosmeticsCategory<T, E>em should not be built. Just create it, we'll build it for ya ;)
     * @param   titleItem   Item that will serve as title
     * @param   attributes  Category attributes
     * @param   cosmetics   Cosmetic class
     */
    public GuiCosmeticsCategory(
        CosmeticManager playerCosmeticManager,
        HashItem titleItem,
        GuiCosmeticsCategoryAttributes attributes,
        Class<E> cosmetics,
        CosmeticManager.CosmeticGetter<Cosmetic<T>> cosmeticGetter,
        CosmeticManager.CosmeticSetter<Cosmetic<T>> cosmeticSetter
    )
    {
        super(
            Component.text(attributes.getName()),
            SIZE,
            GUI_MANAGER
        );

        this.currentCosmeticGetter = cosmeticGetter;
        this.currentCosmeticSetter = cosmeticSetter;

        this.cosmetics = cosmetics;

        this.mask = new Mask(this);
        this.attributes = attributes;

        this.initializeGui(titleItem);
        this.reloadGui(this, playerCosmeticManager);
    }


    private void initializeGui(HashItem titleItem)
    {
        final HashItem primarySep = HashItem.separator(this.attributes.getPrimaryColor(), GUI_MANAGER);
        final HashItem secondarySep = HashItem.separator(this.attributes.getSecondaryColor(), GUI_MANAGER);

        final HashItem previousPageItem = new HashItem(Material.ARROW)
            .setName(Component.text(this.attributes.getColor() + "Page précédente"))
            .addLore(Component.text(ChatColor.GRAY + "Cliquez pour afficher la page précédente."));

        final HashItem nextPageItem = new HashItem(Material.ARROW)
            .setName(Component.text(attributes.getColor() + "Page suivante"))
            .addLore(Component.text(ChatColor.GRAY + "Cliquez pour afficher la page suivante."));

        final HashItem backItem = new HashItem(Material.DARK_OAK_DOOR); // TODO: Finish this lol

        super.setPreviousPageItem(previousPageItem);
        super.setNextPageItem(nextPageItem);

        titleItem
            .setUntakable(true)
            .build(GUI_MANAGER);

        this.mask
            .setItem('.', primarySep)
            .setItem(':', secondarySep)
            .setItem('T', titleItem)
            .setItem('P', previousPageItem)
            .setItem('N', nextPageItem)
            .setItem('B', backItem);

        this.mask
            .pattern("::..T..::")
            .pattern(":.     .:")
            .pattern(".       .")
            .pattern(".       .")
            .pattern(":.     .:")
            .pattern("::.PBN.::");

        this.mask.apply();
    }

    private void reloadGui(GuiCosmeticsCategory<T, E> gui, CosmeticManager manager)
    {
        gui.clearPages();
        gui.addCosmetics(gui.getCosmeticsClass(), manager);
    }

    private void addCosmetic(Cosmetic<T> cosmetic, CosmeticManager manager)
    {
        final Page lastPage = this.getLastPage();

        final HashItem cosmeticItem = new HashItem(Material.STONE)
            .setName(Component.text(cosmetic.getName()))
            .addLore(Component.text(cosmetic.getDescription()));

        if (manager.hasCosmetic(cosmetic)) {
            cosmeticItem.addLore(Component.text("YOU GOT THIS COSMETIC!!!"));
        }

        if (currentCosmeticGetter.getGetter(manager).get().equals(cosmetic)) {
            cosmeticItem.addLore(Component.text(ChatColor.GREEN + "Selected!!!"));
            cosmeticItem.addEnchant(Enchantment.DURABILITY, 1);
        }

        cosmeticItem
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui hashGui, ItemStack item, int slot) -> {
                        if (!(hashGui instanceof GuiCosmeticsCategory<?, ?> gui)) {
                            return;
                        }

                        final CosmeticManager playerCosmeticManager =
                            MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

                        currentCosmeticSetter.getSetter(playerCosmeticManager).accept(cosmetic);

                        /*              ️ This invalid cast is "okay" because there is no way that this fires in another gui, */
                        /*              ⬇ thanks to the gui whitelist when building an item.                                  */
                        this.reloadGui((GuiCosmeticsCategory<T, E>) gui, playerCosmeticManager);

                        gui.update(player);
                    })
            )
            .build(this, GUI_MANAGER);

        try {
            lastPage.addItem(cosmeticItem);
        } catch (IllegalArgumentException unused) {
            this.createNewPage();
            this.addCosmetic(cosmetic, manager);
        }
    }

    private void addCosmetics(Class<E> enumClass, CosmeticManager manager)
    {
        for (E enumConstant : enumClass.getEnumConstants()) {
            this.addCosmetic(enumConstant.getCosmetic(), manager);
        }
    }

    public Class<E> getCosmeticsClass()
    {
        return this.cosmetics;
    }

}
