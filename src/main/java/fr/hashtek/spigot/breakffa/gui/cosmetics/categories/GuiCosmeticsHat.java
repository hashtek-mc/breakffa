package fr.hashtek.spigot.breakffa.gui.cosmetics.categories;

import fr.hashtek.spigot.breakffa.constants.Skulls;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.gui.GuiCosmetics;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import fr.hashtek.spigot.hashitem.HashSkull;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class GuiCosmeticsHat
    extends GuiCosmeticsCategory<CosmeticTypeHat, CosmeticTypeHat.Hat>
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
            CosmeticTypeHat.Hat.class,
            cManager -> cManager::getOwnedHat,
            cManager -> cManager::getCurrentHat,
            cManager -> cManager::setCurrentHat
        );
    }


    /**
     * @apiNote Item should not be built. Just create it, we'll build it for ya ;)
     * @return  Item that will serve as the Gui title (at the top)
     */
    public static HashItem getCategoryItem(CosmeticManager playerCosmeticManager)
    {
        return new HashSkull()
            .setTexture(Skulls.COSMETIC_HATS_CATEGORY)
            .setName(Component.text("" + ChatColor.AQUA + ChatColor.BOLD + "Chapeaux"))
            .setLore(Arrays.asList(
                Component.text(ChatColor.GRAY + "Sélectionnez un " + ChatColor.BLUE + "bloc" + ChatColor.GRAY + " ou un " + ChatColor.BLUE + "objet" + ChatColor.GRAY + " à mettre"),
                Component.text(ChatColor.GRAY + "sur votre tête. Visible par " + ChatColor.BLUE + "tous les joueurs" + ChatColor.GRAY + ".")
            ));
    }

}
