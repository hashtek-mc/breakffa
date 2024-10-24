package fr.hashtek.spigot.breakffa.cosmetics;

public enum CosmeticRarity
{

    COMMON          ("Common", "#FFFFFF"),
    RARE            ("Rare", "#FFFFFF"),
    EPIC            ("Epic", "#FFFFFF"),
    LEGENDARY       ("Legendary", "#FFFFFF"),
    MYSTIC          ("Mystic", "#FFFFFF"),
    DIVINE          ("Divine", "#FFFFFF"),
    TRANSCENDANT    ("Transcendant", "#FFFFFF"),
    SPECIAL         ("Special", "#FFFFFF");


    private final String name;
    private final String hex;


    CosmeticRarity(String name, String hex)
    {
        this.name = name;
        this.hex = hex;
    }


    public String getName()
    {
        return this.name;
    }

    public String getHexColor()
    {
        return this.hex;
    }

}
