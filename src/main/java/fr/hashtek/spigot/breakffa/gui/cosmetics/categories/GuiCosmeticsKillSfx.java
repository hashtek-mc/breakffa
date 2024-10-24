package fr.hashtek.spigot.breakffa.gui.cosmetics.categories;

import fr.hashtek.spigot.breakffa.constants.Skulls;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKillSfx;
import fr.hashtek.spigot.breakffa.gui.GuiCosmetics;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategory;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class GuiCosmeticsKillSfx
    extends GuiCosmeticsCategory<CosmeticTypeKillSfx, CosmeticTypeKillSfx.KillSfx>
{

    private static final Component CATEGORY_NAME = Component.text(
        "" + ChatColor.AQUA + ChatColor.BOLD + "Bruits de mort"
    );

    public static final GuiCosmeticsCategoryAttributes CATEGORY_ATTRIBUTES =
        new GuiCosmeticsCategoryAttributes(
            Skulls.COSMETIC_BLOCK_CATEGORY, // FIXME: modify
            CATEGORY_NAME,
            Arrays.asList(
                Component.text("")
            ),
            Material.WHITE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            ChatColor.RED
        );


    /**
     * Creates a new instance of GuiCosmeticsKSFX.
     */
    public GuiCosmeticsKillSfx(
        GuiCosmetics parentGui,
        CosmeticManager cosmeticManager
    )
    {
        super(
            parentGui,
            cosmeticManager,
            CATEGORY_ATTRIBUTES,
            CosmeticTypeKillSfx.class,
            CosmeticTypeKillSfx.KillSfx.class,
            cManager -> cManager::getCurrentKillSfx,
            cManager -> cManager::setCurrentKillSfx
        );
    }

}
