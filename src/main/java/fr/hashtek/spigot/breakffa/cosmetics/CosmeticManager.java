package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKillSFX;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

//    /**
//     * Function used to get the cosmetic getter according to a Player's Cosmetic Manager.
//     *
//     * @param   <T>     Cosmetic
//     */
//    public interface OwnedCosmeticsGetter<T extends Cosmetic<? extends AbstractCosmetic>>
//    {
//
//        /**
//         * @param   manager     Cosmetic Manager of the player who clicked
//         */
//        Supplier<List<T>> getOwnedGetter(CosmeticManager manager);
//
//    }


    private final List<Cosmetic<? extends AbstractCosmetic>> ownedCosmetics;

    private Cosmetic<CosmeticTypeKillSFX> currentKillSfx;
    private Cosmetic<CosmeticTypeHat> currentHat;


    /**
     * Creates a new instance of CosmeticManager.
     */
    public CosmeticManager()
    {
        this.ownedCosmetics = new ArrayList<Cosmetic<? extends AbstractCosmetic>>();
        this.loadData();
    }

    /**
     * Loads the data from the database.
     * TODO: Finish this.
     */
    public void loadData()
    {
        // ...
    }

    public static List<Cosmetic<? extends AbstractCosmetic>> getCosmetics()
    {
        final List<Cosmetic<? extends AbstractCosmetic>> cosmetics = new ArrayList<Cosmetic<? extends AbstractCosmetic>>();

        for (CosmeticTypeHat.Hat hat : CosmeticTypeHat.Hat.values()) {
            cosmetics.add(hat.getCosmetic());
        }

        for (CosmeticTypeKillSFX.KillSfx killSfx : CosmeticTypeKillSFX.KillSfx.values()) {
            cosmetics.add(killSfx.getCosmetic());
        }

        return cosmetics;
    }

    /**
     * @return  Cosmetic that player owns
     */
    public List<Cosmetic<? extends AbstractCosmetic>> getOwnedCosmetics()
    {
        return this.ownedCosmetics;
    }

    /* Kill SFXs ---------------------------------------------------------- */
    /**
     * @return  Kill SFXs that player owns
     */
    @SuppressWarnings("unchecked")
    public List<Cosmetic<CosmeticTypeKillSFX>> getOwnedKillSfxs()
    {
        return this.ownedCosmetics.stream()
            .filter(cosmetic -> cosmetic.getCosmetic() instanceof CosmeticTypeKillSFX)
            .map(cosmetic -> (Cosmetic<CosmeticTypeKillSFX>) cosmetic)
            .collect(Collectors.toList());
    }

    /**
     * @return  Current Kill SFX
     */
    public Cosmetic<CosmeticTypeKillSFX> getCurrentKillSfx()
    {
        return currentKillSfx;
    }

    /**
     * @param   sfx     New current Kill SFX
     */
    public void setCurrentKillSfx(Cosmetic<CosmeticTypeKillSFX> sfx)
    {
        this.currentKillSfx = sfx;
    }
    /* -------------------------------------------------------------------- */

    /* Hats --------------------------------------------------------------- */
    /**
     * @return  Hat that players owns
     */
    @SuppressWarnings("unchecked")
    public List<Cosmetic<CosmeticTypeHat>> getOwnedHats()
    {
        return this.ownedCosmetics.stream()
            .filter(cosmetic -> cosmetic.getCosmetic() instanceof CosmeticTypeHat)
            .map(cosmetic -> (Cosmetic<CosmeticTypeHat>) cosmetic)
            .collect(Collectors.toList());
    }

    /**
     * @return  Current hat
     */
    public Cosmetic<CosmeticTypeHat> getCurrentHat()
    {
        return this.currentHat;
    }

    /**
     * @param   hat     New current hat
     */
    public void setCurrentHat(Cosmetic<CosmeticTypeHat> hat)
    {
        this.currentHat = hat;
    }
    /* -------------------------------------------------------------------- */

}
