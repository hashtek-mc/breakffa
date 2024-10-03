package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import org.bukkit.Sound;

/**
 * KSFX stands for Kill SFX.
 */
public class CosmeticTypeKSFX implements AbstractCosmetic
{

    public enum KillSfx implements CosmeticCategoryArticles<CosmeticTypeKSFX>
    {

        NINE_MM (new Cosmetic<CosmeticTypeKSFX>(
            "9mm",
            "caca",
            100,
            new CosmeticTypeKSFX(Sound.BLOCK_WATER_AMBIENT)
        )),

        CACA (new Cosmetic<CosmeticTypeKSFX>(
            "huitre",
            "nathan j",
            1060,
            new CosmeticTypeKSFX(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS)
        ));


        private final Cosmetic<CosmeticTypeKSFX> cosmetic;


        /**
         * Creates a new Kill SFX (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        KillSfx(Cosmetic<CosmeticTypeKSFX> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeKSFX> getCosmetic()
        {
            return cosmetic;
        }

    }


    private final Sound sfx;


    /**
     * Creates a Kill SFX (object).
     *
     * @param   sfx SFX to play when killing someone
     */
    public CosmeticTypeKSFX(Sound sfx)
    {
        this.sfx = sfx;
    }


    /**
     * @return  SFX
     */
    public Sound getSfx()
    {
        return this.sfx;
    }

}
