package fr.hashtek.spigot.breakffa.gui.cosmetics.categories;

import fr.hashtek.spigot.breakffa.constants.Skulls;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.gui.GuiCosmetics;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategory;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class GuiCosmeticsHat
    extends GuiCosmeticsCategory<CosmeticTypeHat, CosmeticTypeHat.Hat>
{

    private static final Component CATEGORY_NAME = Component.text(
        "" + ChatColor.AQUA + ChatColor.BOLD + "Chapeaux"
    );

    public static final GuiCosmeticsCategoryAttributes CATEGORY_ATTRIBUTES =
        new GuiCosmeticsCategoryAttributes(
            Skulls.COSMETIC_HATS_CATEGORY,
            CATEGORY_NAME,
            Arrays.asList(
                Component.text(ChatColor.GRAY + "Choisissez un " + ChatColor.BLUE + "bloc" + ChatColor.GRAY + " ou " + ChatColor.BLUE + "objet" + ChatColor.GRAY + " à mettre"),
                Component.text(ChatColor.GRAY + "sur votre tête. Visible par " + ChatColor.BLUE + "tous les joueurs" + ChatColor.GRAY + ".")
            ),
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
            CATEGORY_ATTRIBUTES,
            CosmeticTypeHat.class,
            CosmeticTypeHat.Hat.class,
            cManager -> cManager::getCurrentHat,
            cManager -> cManager::setCurrentHat
        );
    }

}
