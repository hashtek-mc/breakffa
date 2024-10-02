package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;

public class Cosmetic<T>
{

    private final String name;
    private final String description;
    private final int price;
    private final T cosmetic;


    public Cosmetic(
        String name,
        String description,
        int price,
        T cosmetic
    )
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cosmetic = cosmetic;
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

    /**
     * @return  Cosmetic
     */
    public T getCosmetic()
    {
        return this.cosmetic;
    }

}
