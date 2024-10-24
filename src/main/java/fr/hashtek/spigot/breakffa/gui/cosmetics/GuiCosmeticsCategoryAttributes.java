package fr.hashtek.spigot.breakffa.gui.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticAvailability;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.hashitem.HashItem;
import fr.hashtek.spigot.hashitem.HashSkull;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class GuiCosmeticsCategoryAttributes
{

    private final String skullTexture;
    private final Component name;
    private final List<Component> description;
    private final Material primaryColor;
    private final Material secondaryColor;
    private final ChatColor color;


    /**
     * Creates a new instance of GuiCosmeticCategoryAttributes.
     *
     * @param   name            Category title
     * @param   color           Color (for strings)
     * @param   primaryColor    Primary color
     * @param   secondaryColor  Secondary color
     */
    public GuiCosmeticsCategoryAttributes(
        String skullTexture,
        Component name,
        List<Component> description,
        Material primaryColor,
        Material secondaryColor,
        ChatColor color
    )
    {
        this.skullTexture = skullTexture;
        this.name = name;
        this.description = description;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.color = color;
    }


    public static String getCosmeticRatio(
        CosmeticManager playerCosmeticManager,
        CosmeticAvailability cosmeticAvailability
    )
    {
        return getCosmeticRatio(playerCosmeticManager, cosmeticAvailability, null);
    }

    public static String getCosmeticRatio(
        CosmeticManager playerCosmeticManager,
        CosmeticAvailability cosmeticAvailability,
        Class<? extends AbstractCosmetic> cosmeticType
    )
    {
        final NumberFormat numberFormatter = NumberFormat.getInstance();

        final int a, b;

        a = (int) playerCosmeticManager.getOwnedCosmetics().stream().filter(c -> {
            if (cosmeticType != null && !c.getCosmetic().getClass().getName().equals(cosmeticType.getName())) {
                return false;
            }
            return c.getAvailability() == cosmeticAvailability;
        }).count();

        b = (int) CosmeticManager.getCosmetics().stream().filter(c -> {
            if (cosmeticType != null && !c.getCosmetic().getClass().getName().equals(cosmeticType.getName())) {
                return false;
            }
            return c.getAvailability() == cosmeticAvailability;
        }).count();

        return
            cosmeticAvailability.getColor() + numberFormatter.format(a) +
            ChatColor.WHITE + ChatColor.BOLD + "/" +
            cosmeticAvailability.getColor() + numberFormatter.format(b);
    }

    public HashItem createTitleItem(
        CosmeticManager playerCosmeticManager,
        Class<? extends AbstractCosmetic> cosmeticType
    )
    {
        final HashItem item = new HashSkull()
            .setTexture(this.skullTexture)
            .setName(this.name)
            .addLore(description)
            .addLore(Arrays.asList(
                Component.text(""),
                Component.text("" + ChatColor.GRAY + ChatColor.BOLD + "→ " + ChatColor.YELLOW + ChatColor.BOLD + "Vous" + ChatColor.GRAY + " possédez actuellement :")
            ));

        for (CosmeticAvailability availability : CosmeticAvailability.values()) {
            item.addLore(Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "► " + getCosmeticRatio(playerCosmeticManager, availability, cosmeticType) + ChatColor.GRAY + " " + availability.getName()));
        }

        return item;
    }


    /**
     * @return  Category skull texture
     */
    public String getSkullTexture()
    {
        return this.skullTexture;
    }

    /**
     * @return  Category name
     */
    public Component getName()
    {
        return this.name;
    }

    /**
     * @return  Category description
     */
    public List<Component> getDescription()
    {
        return this.description;
    }

    /**
     * @return  Category color
     */
    public ChatColor getColor()
    {
        return this.color;
    }

    /**
     * @return  Category primary color
     */
    public Material getPrimaryColor()
    {
        return this.primaryColor;
    }

    /**
     * @return  Category secondary color
     */
    public Material getSecondaryColor()
    {
        return this.secondaryColor;
    }

}
