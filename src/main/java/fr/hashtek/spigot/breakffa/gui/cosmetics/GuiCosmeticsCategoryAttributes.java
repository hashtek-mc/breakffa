package fr.hashtek.spigot.breakffa.gui.cosmetics;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GuiCosmeticsCategoryAttributes
{

    private final String name;
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
        String name,
        Material primaryColor,
        Material secondaryColor,
        ChatColor color
    )
    {
        this.name = name;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.color = color;
    }


    /**
     * @return  Category name
     */
    public String getName()
    {
        return this.name;
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
