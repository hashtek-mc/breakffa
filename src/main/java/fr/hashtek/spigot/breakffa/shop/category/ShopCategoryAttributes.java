package fr.hashtek.spigot.breakffa.shop.category;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ShopCategoryAttributes
{

    private final String name;
    private final ChatColor color;
    private final Material primaryColor;
    private final Material secondaryColor;


    /**
     * Creates a new instance of ShopCategoryAttributes.
     *
     * @param   name            Category title
     * @param   color           Color (for strings)
     * @param   primaryColor    Primary color
     * @param   secondaryColor  Secondary color
     */
    public ShopCategoryAttributes(
        String name,
        ChatColor color,
        Material primaryColor,
        Material secondaryColor
    )
    {
        this.name = name;
        this.color = color;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
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
