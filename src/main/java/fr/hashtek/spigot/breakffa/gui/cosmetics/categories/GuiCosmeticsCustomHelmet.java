package fr.hashtek.spigot.breakffa.gui.cosmetics.categories;

import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeCustomHelmet;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GuiCosmeticsCustomHelmet
    extends GuiCosmeticsCategory<CosmeticTypeCustomHelmet, CosmeticTypeCustomHelmet.CustomHelmet>
{

    private static final GuiCosmeticsCategoryAttributes ATTRIBUTES =
        new GuiCosmeticsCategoryAttributes(
            "custom helmets lol",
            Material.WHITE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            ChatColor.RED
        );

    public static final HashItem TITLE_ITEM =
        new HashItem(Material.BEACON)
            .setName(Component.text("Custom helmet"));


    /**
     * Creates a new instance of AbstractGuiCosmeticsCategory.
     */
    public GuiCosmeticsCustomHelmet(CosmeticManager cosmeticManager)
    {
        super(
            cosmeticManager,
            ATTRIBUTES,
            CosmeticTypeCustomHelmet.CustomHelmet.class,
            cManager -> cManager::getOwnedCustomHelmets,
            cManager -> cManager::getCurrentCustomHelmet,
            cManager -> cManager::setCurrentCustomHelmet
        );
    }


    @Override
    public HashItem getCategoryTitleItem()
    {
        return TITLE_ITEM;
    }

}
