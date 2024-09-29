package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.cosmetics.KillSfx;

import java.util.ArrayList;
import java.util.List;

public class CosmeticManager
{

    private final List<KillSfx> ownedKillSfxs;
    private KillSfx currentKillSfx;


    /**
     * Creates a new instance of CosmeticManager.
     */
    public CosmeticManager()
    {
        this.ownedKillSfxs = new ArrayList<KillSfx>();
    }


    /**
     * @return  Kill SFXs that player owns
     */
    public List<KillSfx> getOwnedKillSfxs()
    {
        return this.ownedKillSfxs;
    }

    /**
     * @return  Current Kill SFX
     */
    public KillSfx getCurrentKillSfx()
    {
        return currentKillSfx;
    }
}
