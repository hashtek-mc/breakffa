package fr.hashtek.spigot.breakffa.cosmetics;

public abstract class AbstractCosmetic
{

    private final String name;
    private final String description;
    private final int price;


    public AbstractCosmetic(String name, String description, int price)
    {
        this.name = name;
        this.description = description;
        this.price = price;
    }


    /**
     * @return  Cosmetic name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return  Cosmetic description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * @return  Cosmetic price
     */
    public int getPrice()
    {
        return this.price;
    }

}
