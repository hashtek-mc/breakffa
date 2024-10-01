package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import org.bukkit.Sound;

/**
 * KSFX stands for Kill SFX.
 * TODO: Documentation is messy >:(
 */
public class CosmeticTypeKSFX
{

    public enum KillSfx implements CosmeticCategoryArticles
    {

        NINE_MM (new Cosmetic<CosmeticTypeKSFX>(
            "9mm",
            "caca",
            100,
            new CosmeticTypeKSFX(Sound.BLOCK_WATER_AMBIENT)
        ));


        private final Cosmetic<CosmeticTypeKSFX> cosmetic;


        /**
         * Creates a new cosmetic of type Kill SFX
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
     * Creates a Kill SFX.
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
