package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeCustomHelmet;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKSFX;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CosmeticManager
{

    /* Kill SFXs */
    private final List<Cosmetic<CosmeticTypeKSFX>> ownedKillSfxs;
    private Cosmetic<CosmeticTypeKSFX> currentKillSfx;

    /* Custom helmet */
    private final List<Cosmetic<CosmeticTypeCustomHelmet>> ownedCustomHelmets;
    private Cosmetic<CosmeticTypeCustomHelmet> currentCustomHelmet;


    /**
     * Creates a new instance of CosmeticManager.
     */
    public CosmeticManager(BreakFFA main, Player player)
    {
        this.ownedKillSfxs = new ArrayList<Cosmetic<CosmeticTypeKSFX>>();
        this.ownedCustomHelmets = new ArrayList<Cosmetic<CosmeticTypeCustomHelmet>>();
    }

    /**
     *
     */
    public void loadData()
    {
        // ...
    }


    /* Kill SFXs */

    /**
     * Unlocks a Kill SFX
     *
     * @param   sfx     Kill SFX to unlock
     */
    public void unlockKillSfx(Cosmetic<CosmeticTypeKSFX> sfx)
    {
        this.ownedKillSfxs.add(sfx);
    }

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


    /* Custom helmets */

    /**
     * Unlocks a custom helmet
     *
     * @param   helmet  Custom helmet to unlock
     */
    public void unlockCustomHelmet(Cosmetic<CosmeticTypeCustomHelmet> helmet)
    {
        this.ownedCustomHelmets.add(helmet);
    }

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
}
