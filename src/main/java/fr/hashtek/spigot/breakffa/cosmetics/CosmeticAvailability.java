package fr.hashtek.spigot.breakffa.cosmetics;

import org.bukkit.ChatColor;

public enum CosmeticAvailability
{

    OBTAINABLE  (ChatColor.AQUA,            "obtensibles"), // Can be obtainable at any time.
    LIMITED     (ChatColor.YELLOW,          "limités"),     // Can't be obtainable at any time but can be re-obtainable in the future.
    EXCLUSIVE   (ChatColor.LIGHT_PURPLE,    "exclusifs");   // Can be obtainable once (e.g. during a certain period or something) and then can't be ever obtainable.


    private final ChatColor color;
    private final String name;


    CosmeticAvailability(ChatColor color, String name)
    {
        this.color = color;
        this.name = name;
    }


    /**
     * @return  Color
     */
    public ChatColor getColor()
    {
        return this.color;
    }

    /**
     * @return  Name
     */
    public String getName()
    {
        return this.name;
    }

}
