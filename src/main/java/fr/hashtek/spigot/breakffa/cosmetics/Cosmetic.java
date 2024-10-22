package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @param   <T>     Cosmetic type
 */
public class Cosmetic<T extends AbstractCosmetic>
{

    public enum CosmeticAvailability
    {

        OBTAINABLE, // Can be obtainable at any time.
        LIMITED,    // Can't be obtainable at any time but can be re-obtainable in the future.
        EXCLUSIVE   // Can be obtainable once (e.g. during a certain period or something) and then can't be ever obtainable.

    }


    private final Material material;
    private final String name;
    private final String description;
    private final int price;
    private final T cosmetic;
    private final CosmeticAvailability availability;


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
        this(
            material,
            name,
            description,
            price,
            cosmetic,
            CosmeticAvailability.OBTAINABLE
        );
    }

    /**
     * Creates a new Cosmetic.
     *
     * @param   name            Name
     * @param   description     Description
     * @param   price           Price
     * @param   cosmetic        Cosmetic
     * @param   availability    Cosmetic's availability
     */
    public Cosmetic(
        Material material,
        String name,
        String description,
        int price,
        T cosmetic,
        CosmeticAvailability availability
    )
    {
        this.material = material;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cosmetic = cosmetic;
        this.availability = availability;
    }


    /**
     * @param   player  Player to test
     * @return  True if cosmetic can be bought
     */
    public boolean canBeBought(Player player)
    {
        if (this.availability == CosmeticAvailability.OBTAINABLE) {
            return true;
        }
        return this.buyConditions(player);
    }

    /**
     * Used in the case where availability of cosmetic is either
     * {@link CosmeticAvailability#LIMITED} or {@link CosmeticAvailability#EXCLUSIVE}.
     * <p>
     * Return <code>true</code> when one or multiple conditions are satisfied,
     * otherwise <code>false</code> and item won't be available for purchase.
     * </p>
     * <p>
     * By default, this returns <code>true</code> everytime.
     * </p>
     *
     * @param   player  Player to test
     * @return  True if cosmetic can be bought
     */
    protected boolean buyConditions(Player player)
    {
        return true;
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

    /**
     * @return  Cosmetic's availability
     */
    public CosmeticAvailability getAvailability()
    {
        return this.availability;
    }

}
