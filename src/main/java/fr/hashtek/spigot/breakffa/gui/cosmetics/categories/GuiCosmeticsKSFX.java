package fr.hashtek.spigot.breakffa.gui.cosmetics.categories;

import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKSFX;
import fr.hashtek.spigot.breakffa.gui.GuiCosmetics;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.cosmetics.GuiCosmeticsCategory;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GuiCosmeticsKSFX
    extends GuiCosmeticsCategory<CosmeticTypeKSFX, CosmeticTypeKSFX.KillSfx>
{

    private static final GuiCosmeticsCategoryAttributes ATTRIBUTES =
        new GuiCosmeticsCategoryAttributes(
            "kill sfx!!!",
            Material.WHITE_STAINED_GLASS_PANE,
            Material.RED_STAINED_GLASS_PANE,
            ChatColor.RED
        );

    public static final HashItem TITLE_ITEM =
        new HashItem(Material.IRON_SWORD)
            .setName(Component.text("KSFX"));


    /**
     * Creates a new instance of GuiCosmeticsKSFX.
     */
    public GuiCosmeticsKSFX(
        GuiCosmetics parentGui,
        CosmeticManager cosmeticManager
    )
    {
        super(
            parentGui,
            cosmeticManager,
            ATTRIBUTES,
            CosmeticTypeKSFX.KillSfx.class,
            cManager -> cManager::getOwnedKillSfxs,
            cManager -> cManager::getCurrentKillSfx,
            cManager -> cManager::setCurrentKillSfx
        );
    }


    @Override
    public HashItem getCategoryTitleItem()
    {
        return TITLE_ITEM;
    }

}
