package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;

public class CosmeticTypeKillParticles
    extends AbstractCosmetic
{

    public enum KillParticles
        implements CosmeticCategoryArticles<CosmeticTypeKillParticles>
    {

        ;


        private final Cosmetic<CosmeticTypeKillParticles> cosmetic;


        /**
         * Creates a new Kill particles (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        KillParticles(Cosmetic<CosmeticTypeKillParticles> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeKillParticles> getCosmetic()
        {
            return this.cosmetic;
        }

    }

    /**
     * Creates a new Kill particles (object).
     */
    public CosmeticTypeKillParticles()
    {
        // ...
    }

}
