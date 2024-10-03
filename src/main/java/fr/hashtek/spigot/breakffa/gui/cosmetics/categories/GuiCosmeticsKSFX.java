package fr.hashtek.spigot.breakffa.gui.category.categories;

import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKSFX;
import fr.hashtek.spigot.breakffa.gui.category.GuiCosmeticsCategoryAttributes;
import fr.hashtek.spigot.breakffa.gui.category.GuiCosmeticsCategory;
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
    public GuiCosmeticsKSFX(CosmeticManager cosmeticManager)
    {
        super(
            cosmeticManager,
            ATTRIBUTES,
            CosmeticTypeKSFX.KillSfx.class,
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
