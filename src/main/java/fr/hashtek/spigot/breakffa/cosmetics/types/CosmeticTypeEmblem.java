package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;

public class CosmeticTypeEmblem
    extends AbstractCosmetic
{

    public enum Emblem
        implements CosmeticCategoryArticles<CosmeticTypeEmblem>
    {

        ;


        private final Cosmetic<CosmeticTypeEmblem> cosmetic;


        /**
         * Creates a new Emblem (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        Emblem(Cosmetic<CosmeticTypeEmblem> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeEmblem> getCosmetic()
        {
            return this.cosmetic;
        }

    }

    /**
     * Creates a new Emblem (object).
     */
    public CosmeticTypeEmblem()
    {
        // ...
    }

}
