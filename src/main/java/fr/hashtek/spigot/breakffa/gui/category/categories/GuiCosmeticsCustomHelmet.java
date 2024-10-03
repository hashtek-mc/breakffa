package fr.hashtek.spigot.breakffa.gui.category.categories;

import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeCustomHelmet;
import fr.hashtek.spigot.breakffa.gui.category.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.category.GuiCosmeticsCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GuiCosmeticsCustomHelmet
    extends GuiCosmeticsCategory<CosmeticTypeCustomHelmet, CosmeticTypeCustomHelmet.CustomHelmet>
{

    private static final GuiCosmeticsCategoryAttributes attributes =
        new GuiCosmeticsCategoryAttributes(
            "custom helmets lol",
            Material.WHITE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            ChatColor.RED
        );


    /**
     * Creates a new instance of AbstractGuiCosmeticsCategory.
     */
    public GuiCosmeticsCustomHelmet(CosmeticManager cosmeticManager)
    {
        super(
            cosmeticManager,
            new HashItem(Material.BEACON)
                .setName(Component.text("Custom helmet")),
            attributes,
            CosmeticTypeCustomHelmet.CustomHelmet.class,
            cManager -> cManager::getCurrentCustomHelmet,
            cManager -> cManager::setCurrentCustomHelmet
        );
    }

}
