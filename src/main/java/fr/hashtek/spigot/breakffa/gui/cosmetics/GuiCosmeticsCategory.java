package fr.hashtek.spigot.breakffa.gui.cosmetics;

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
import org.bukkit.Bukkit;
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

    private final Mask mask;
    private final GuiCosmeticsCategoryAttributes attributes;

    private final Class<E> cosmetics;
    private final CosmeticManager.OwnedCosmeticsGetter<Cosmetic<T>> ownedCosmeticsGetter;
    private final CosmeticManager.CurrentCosmeticGetter<Cosmetic<T>> currentCosmeticGetter;
    private final CosmeticManager.CurrentCosmeticSetter<Cosmetic<T>> currentCosmeticSetter;


    /**
     * Creates a new instance of GuiCosmeticsCategory.
     *
     * @param   playerCosmeticManager   Player Cosmetic Manager
     * @param   attributes              Category attributes
     * @param   cosmetics               Cosmetic class
     * @param   ownedCosmeticsGetter    Cosmetics that player currently owns Getter
     * @param   cosmeticGetter          Cosmetic getter (from a CosmeticManager instance)
     * @param   cosmeticSetter          Cosmetic setter (from a CosmeticManager instance)
     */
    public GuiCosmeticsCategory(
        CosmeticManager playerCosmeticManager,
        GuiCosmeticsCategoryAttributes attributes,
        Class<E> cosmetics,
        CosmeticManager.OwnedCosmeticsGetter<Cosmetic<T>> ownedCosmeticsGetter,
        CosmeticManager.CurrentCosmeticGetter<Cosmetic<T>> cosmeticGetter,
        CosmeticManager.CurrentCosmeticSetter<Cosmetic<T>> cosmeticSetter
    )
    {
        super(
            Component.text(attributes.getName()),
            SIZE,
            GUI_MANAGER
        );

        this.mask = new Mask(this);
        this.attributes = attributes;

        this.cosmetics = cosmetics;
        this.ownedCosmeticsGetter = ownedCosmeticsGetter;
        this.currentCosmeticGetter = cosmeticGetter;
        this.currentCosmeticSetter = cosmeticSetter;

        this.initializeGui(this.getCategoryTitleItem());
        this.reloadGui(
            this,
            playerCosmeticManager
        );
    }


    /**
     * Initializes the Gui.
     *
     * @param   titleItem   Item that will serve as the Gui title (at the top)
     */
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

    /**
     * Reloads the Gui. Used at each current cosmetic change.
     *
     * @param   gui                     Gui to update
     * @param   manager                 Cosmetic Manager
     */
    public void reloadGui(
        GuiCosmeticsCategory<T, E> gui,
        CosmeticManager manager
    )
    {
        Bukkit.broadcast(Component.text("Reloading GUI..."));
        gui.clearPages();
        gui.addCosmeticsItems(
            gui.getCosmeticsClass(),
            manager
        );
    }

    /**
     * Creates an item for a cosmetic, with the click handlers for buy,
     * select etc...
     *
     * @param   cosmetic                Cosmetic to add
     * @param   manager                 Cosmetic Manager (for current / possession detection)
     * @return  Built item
     */
    private HashItem createCosmeticItem(
        Cosmetic<T> cosmetic,
        CosmeticManager manager
    )
    {
        Bukkit.broadcast(Component.text("name: " + manager.getPlayer().getName()));

        final Cosmetic<T> currentCosmetic = currentCosmeticGetter.getGetter(manager).get();

        final HashItem item = new HashItem(cosmetic.getMaterial())
            .setName(Component.text(cosmetic.getName()))
            .addLore(Component.text(cosmetic.getDescription()));

        if (ownedCosmeticsGetter.getOwnedGetter(manager).get().contains(cosmetic)) {
            Bukkit.broadcast(Component.text("owned"));
            item.addLore(Component.text("YOU GOT THIS COSMETIC!!!"));
        } else {
            Bukkit.broadcast(Component.text("not owned :("));
            item.addLore(Component.text("price: " + cosmetic.getPrice()));
        }

        if (currentCosmetic != null && currentCosmetic.equals(cosmetic)) {
            Bukkit.broadcast(Component.text("selected"));
            item.addLore(Component.text(ChatColor.GREEN + "Selected!!!"));
            item.addEnchant(Enchantment.DURABILITY, 1);

        } else {
            Bukkit.broadcast(Component.text("not selected :c"));
        }

        item.addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui hashGui, ItemStack i, int slot) -> {
                    if (!(hashGui instanceof GuiCosmeticsCategory<?, ?> guiCosmeticsCategory)) {
                        return;
                    }

                    /*                                        This unchecked cast is "okay" because there is no way that this fires in another gui, */
                    /*                                      ⬇ thanks to the Gui whitelist when building an item.                                    */
                    final GuiCosmeticsCategory<T, E> gui = (GuiCosmeticsCategory<T, E>) guiCosmeticsCategory;

                    final CosmeticManager playerCosmeticManager =
                        MAIN.getGameManager().getPlayerManager(player).getCosmeticManager();

                    System.out.println("Targeted cosmetic is : \"" + cosmetic.getName() + "\"");

                    /* If player doesn't own cosmetic, redirect it to the Buy Gui. */
                    if (!gui.getOwnedCosmeticsGetter().getOwnedGetter(playerCosmeticManager).get().contains(cosmetic)) {
                        new GuiCosmeticBuy<T, E>(
                            gui,
                            playerCosmeticManager,
                            cosmetic,
                            gui.getOwnedCosmeticsGetter(),
                            gui.getCurrentCosmeticGetter(),
                            gui.getCurrentCosmeticSetter()
                        ).open(player);
                        return;
                    }

                    gui.getCurrentCosmeticSetter().getSetter(playerCosmeticManager).accept(cosmetic);

                    gui.reloadGui(
                        gui,
                        playerCosmeticManager
                    );

                    gui.update(player);
                })
        )
        .build(this, GUI_MANAGER);

        return item;
    }

    /**
     * Adds a cosmetic to the Gui.
     *
     * @param   cosmetic                Cosmetic to add
     * @param   manager                 Cosmetic Manager (for current / possession detection)
     *
     * @apiNote TODO: Finish this and beautify !
     */
    private void addCosmeticItem(
        Cosmetic<T> cosmetic,
        CosmeticManager manager
    )
    {
        final Page lastPage = this.getLastPage();

        Bukkit.broadcast(Component.text("Adding cosmetic item " + cosmetic.getName()));

        try {
            lastPage.addItem(
                this.createCosmeticItem(
                    cosmetic,
                    manager
                )
            );
        } catch (IllegalArgumentException unused) {
            this.createNewPage();
            this.addCosmeticItem(
                cosmetic,
                manager
            );
        }
    }

    /**
     * Adds every cosmetic to the Gui.
     *
     * @param   enumClass               Cosmetic enum class
     * @param   manager                 Cosmetic Manager (for current / possession detection)
     */
    private void addCosmeticsItems(
        Class<E> enumClass,
        CosmeticManager manager
    )
    {
        Bukkit.broadcast(Component.text("Adding cosmetics..."));

        for (E enumConstant : enumClass.getEnumConstants()) {
            this.addCosmeticItem(
                enumConstant.getCosmetic(),
                manager
            );
        }
    }


    /* TEMP */
    @Override
    public void open(Player player)
    {
        player.chat("/cosmeticdump");
        super.open(player);
    }


    /**
     * @apiNote Item should not be built. Just create it, we'll build it for ya ;)
     * @apiNote Tip: Create a static variable that stores the item, and make this function return the variable.
     * @return  Item that will serve as the Gui title (at the top)
     */
    public abstract HashItem getCategoryTitleItem();

    public GuiCosmeticsCategoryAttributes getAttributes()
    {
        return this.attributes;
    }

    /**
     * @return  Cosmetic enum class
     */
    public Class<E> getCosmeticsClass()
    {
        return this.cosmetics;
    }

    public CosmeticManager.OwnedCosmeticsGetter<Cosmetic<T>> getOwnedCosmeticsGetter()
    {
        return this.ownedCosmeticsGetter;
    }

    public CosmeticManager.CurrentCosmeticGetter<Cosmetic<T>> getCurrentCosmeticGetter()
    {
        return this.currentCosmeticGetter;
    }

    public CosmeticManager.CurrentCosmeticSetter<Cosmetic<T>> getCurrentCosmeticSetter()
    {
        return this.currentCosmeticSetter;
    }

}
