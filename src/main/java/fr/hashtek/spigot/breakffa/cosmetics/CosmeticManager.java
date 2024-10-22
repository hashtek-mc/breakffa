package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKSFX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * TODO:
 *  - Documentation is messy
 *  - Overall code is messy lol
 */
public class CosmeticManager
{

    /**
     * Function used to get the cosmetic setter according to a Player's Cosmetic Manager.
     *
     * @param   <T>     Cosmetic
     */
    public interface CurrentCosmeticSetter<T extends Cosmetic<? extends AbstractCosmetic>>
    {

        /**
         * @param   manager     Cosmetic Manager of the player who clicked
         */
        Consumer<T> getSetter(CosmeticManager manager);

    }

    /**
     * Function used to get the cosmetic getter according to a Player's Cosmetic Manager.
     *
     * @param   <T>     Cosmetic
     */
    public interface CurrentCosmeticGetter<T extends Cosmetic<? extends AbstractCosmetic>>
    {

        /**
         * @param   manager     Cosmetic Manager of the player who clicked
         */
        Supplier<T> getGetter(CosmeticManager manager);

    }

    /**
     * Function used to get the cosmetic getter according to a Player's Cosmetic Manager.
     *
     * @param   <T>     Cosmetic
     */
    public interface OwnedCosmeticsGetter<T extends Cosmetic<? extends AbstractCosmetic>>
    {

        /**
         * @param   manager     Cosmetic Manager of the player who clicked
         */
        Supplier<List<T>> getOwnedGetter(CosmeticManager manager);

    }


    /* Kill SFXs */
    private final List<Cosmetic<CosmeticTypeKSFX>> ownedKillSfxs;
    private Cosmetic<CosmeticTypeKSFX> currentKillSfx;

    /* Custom helmet */
    private final List<Cosmetic<CosmeticTypeHat>> ownedHat;
    private Cosmetic<CosmeticTypeHat> currentHat;


    /**
     * Creates a new instance of CosmeticManager.
     */
    public CosmeticManager()
    {
        this.ownedKillSfxs = new ArrayList<Cosmetic<CosmeticTypeKSFX>>();
        this.ownedHat = new ArrayList<Cosmetic<CosmeticTypeHat>>();

        this.loadData();
    }

    /**
     * Loads the data from the database.
     * TODO: Finish this.
     */
    public void loadData()
    {
        // ...
//        this.unlockEverything(); // FIXME: Temporary!
    }

    /**
     * Unlocks everything. FIXME: TEMPORARY!!!
     *
     * @apiNote not to use pls
     */
    private void unlockEverything()
    {
        /* Kill SFXs */
        for (CosmeticTypeKSFX.KillSfx sfx : CosmeticTypeKSFX.KillSfx.values()) {
            this.ownedKillSfxs.add(sfx.getCosmetic());
        }
        this.currentKillSfx = this.ownedKillSfxs.get(0);

        /* Custom helmets */
        for (CosmeticTypeHat.Hat helmet : CosmeticTypeHat.Hat.values()) {
            this.ownedHat.add(helmet.getCosmetic());
        }
        this.currentHat = this.ownedHat.get(0);
    }


    public int getAmountOfCosmeticsByAvailability(Cosmetic.CosmeticAvailability availability)
    {
        return (int) (
            Arrays.stream(CosmeticTypeKSFX.KillSfx.values()).filter(c -> c.getCosmetic().getAvailability() == availability).count() +
            Arrays.stream(CosmeticTypeHat.Hat.values()).filter(c -> c.getCosmetic().getAvailability() == availability).count()
        );
    }

    public int getAmountOfOwnedCosmeticsByAvailability(Cosmetic.CosmeticAvailability availability)
    {
        return (int) (
            this.ownedKillSfxs.stream().filter(c -> c.getAvailability() == availability).count() +
            this.ownedHat.stream().filter(c -> c.getAvailability() == availability).count()
        );
    }


    /* Kill SFXs */

    /**
     * @return  Kill SFXs that player owns
     */
    public List<Cosmetic<CosmeticTypeKSFX>> getOwnedKillSfxs()
    {
        return this.ownedKillSfxs;
    }

    /**
     * @return  Current Kill SFX
     */
    public Cosmetic<CosmeticTypeKSFX> getCurrentKillSfx()
    {
        return currentKillSfx;
    }

    public void setCurrentKillSfx(Cosmetic<CosmeticTypeKSFX> sfx)
    {
        this.currentKillSfx = sfx;
    }


    /* Hats */

    /**
     * @return  Hat that players owns
     */
    public List<Cosmetic<CosmeticTypeHat>> getOwnedHat()
    {
        return this.ownedHat;
    }

    /**
     * @return  Current hat
     */
    public Cosmetic<CosmeticTypeHat> getCurrentHat()
    {
        return this.currentHat;
    }

    public void setCurrentHat(Cosmetic<CosmeticTypeHat> helmet)
    {
        this.currentHat = helmet;
    }
}
