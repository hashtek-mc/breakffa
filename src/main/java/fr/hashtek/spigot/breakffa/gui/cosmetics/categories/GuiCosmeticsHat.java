package fr.hashtek.spigot.breakffa.gui.cosmetics.categories;

import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.gui.GuiCosmetics;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GuiCosmeticsHat
    extends GuiCosmeticsCategory<CosmeticTypeHat, CosmeticTypeHat.CustomHelmet>
{

    private static final GuiCosmeticsCategoryAttributes ATTRIBUTES =
        new GuiCosmeticsCategoryAttributes(
            "hats lol",
            Material.WHITE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            ChatColor.RED
        );


    /**
     * Creates a new instance of GuiCosmeticsHat.
     */
    public GuiCosmeticsHat(
        GuiCosmetics parentGui,
        CosmeticManager cosmeticManager
    )
    {
        super(
            parentGui,
            cosmeticManager,
            ATTRIBUTES,
            getCategoryItem(cosmeticManager),
            CosmeticTypeHat.CustomHelmet.class,
            cManager -> cManager::getOwnedCustomHelmets,
            cManager -> cManager::getCurrentCustomHelmet,
            cManager -> cManager::setCurrentCustomHelmet
        );
    }


    /**
     * @apiNote Item should not be built. Just create it, we'll build it for ya ;)
     * @return  Item that will serve as the Gui title (at the top)
     */
    public static HashItem getCategoryItem(CosmeticManager playerCosmeticManager)
    {
        return new HashItem(Material.STONE)
            .setName(Component.text("hax!!!!1")); // say gex
    }

}
