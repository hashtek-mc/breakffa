package fr.hashtek.spigot.breakffa.shop.category;

import org.bukkit.ChatColor;

public class ShopCategoryAttributes
{

    private final String name;
    private final ChatColor color;
    private final Byte primaryColor;
    private final Byte secondaryColor;


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
        Byte primaryColor,
        Byte secondaryColor
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
    public Byte getPrimaryColor()
    {
        return this.primaryColor;
    }

    /**
     * @return  Category secondary color
     */
    public Byte getSecondaryColor()
    {
        return this.secondaryColor;
    }

}
