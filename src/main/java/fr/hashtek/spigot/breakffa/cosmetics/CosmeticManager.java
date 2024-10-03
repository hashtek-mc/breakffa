package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeCustomHelmet;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKSFX;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CosmeticManager
{

    /**
     * Function used to get the cosmetic setter according to a Player's Cosmetic Manager.
     *
     * @param   <T>     Cosmetic
     */
    public interface CosmeticSetter<T extends Cosmetic<? extends AbstractCosmetic>>
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
    public interface CosmeticGetter<T extends Cosmetic<? extends AbstractCosmetic>>
    {

        /**
         * @param   manager     Cosmetic Manager of the player who clicked
         */
        Supplier<T> getGetter(CosmeticManager manager);

    }


    /* Kill SFXs */
    private final List<Cosmetic<CosmeticTypeKSFX>> ownedKillSfxs;
    private Cosmetic<CosmeticTypeKSFX> currentKillSfx;

    /* Custom helmet */
    private final List<Cosmetic<CosmeticTypeCustomHelmet>> ownedCustomHelmets;
    private Cosmetic<CosmeticTypeCustomHelmet> currentCustomHelmet;


    /**
     * Creates a new instance of CosmeticManager.
     *
     * @param   main    BreakFFA instance
     * @param   player  Player linked to this Cosmetic Manager
     */
    public CosmeticManager(BreakFFA main, Player player)
    {
        this.ownedKillSfxs = new ArrayList<Cosmetic<CosmeticTypeKSFX>>();
        this.ownedCustomHelmets = new ArrayList<Cosmetic<CosmeticTypeCustomHelmet>>();

        this.loadData();
    }

    /**
     * Loads the data from the database.
     * TODO: Finish this.
     */
    public void loadData()
    {
        // ...
        this.unlockEverything(); // FIXME: Temporary!
    }

    /**
     * @param   cosmetic    Cosmetic
     * @return  True if player has the cosmetic
     */
    public boolean hasCosmetic(Cosmetic<? extends AbstractCosmetic> cosmetic)
    {
        return
            this.ownedKillSfxs.contains(cosmetic) ||
            this.ownedCustomHelmets.contains(cosmetic);
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
        for (CosmeticTypeCustomHelmet.CustomHelmet helmet : CosmeticTypeCustomHelmet.CustomHelmet.values()) {
            this.ownedCustomHelmets.add(helmet.getCosmetic());
        }
        this.currentCustomHelmet = this.ownedCustomHelmets.get(0);
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


    /* Custom helmets */

    /**
     * @return  Custom helmet that players owns
     */
    public List<Cosmetic<CosmeticTypeCustomHelmet>> getOwnedCustomHelmets()
    {
        return this.ownedCustomHelmets;
    }

    /**
     * @return  Current custom helmet
     */
    public Cosmetic<CosmeticTypeCustomHelmet> getCurrentCustomHelmet()
    {
        return this.currentCustomHelmet;
    }

    public void setCurrentCustomHelmet(Cosmetic<CosmeticTypeCustomHelmet> helmet)
    {
        this.currentCustomHelmet = helmet;
    }
}
