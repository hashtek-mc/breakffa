package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;
import org.bukkit.Material;

/**
 * @param   <T>     Cosmetic type
 */
public class Cosmetic<T extends AbstractCosmetic>
{

    private final Material material;
    private final String name;
    private final String description;
    private final int price;
    private final T cosmetic;


    /**
     * Creates a new Cosmetic.
     *
     * @param   name            Name
     * @param   description     Description
     * @param   price           Price
     * @param   cosmetic        Cosmetic
     */
    public Cosmetic(
        Material material,
        String name,
        String description,
        int price,
        T cosmetic
    )
    {
        this.material = material;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cosmetic = cosmetic;
    }


    /**
     * @return  Material
     */
    public Material getMaterial()
    {
        return this.material;
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
