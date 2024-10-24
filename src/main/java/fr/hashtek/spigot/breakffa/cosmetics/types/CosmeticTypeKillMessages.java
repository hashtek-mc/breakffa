package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;

public class CosmeticTypeKillMessages
    extends AbstractCosmetic
{

    public enum KillMessages
        implements CosmeticCategoryArticles<CosmeticTypeKillMessages>
    {

        ;


        private final Cosmetic<CosmeticTypeKillMessages> cosmetic;


        /**
         * Creates a new Kill message (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        KillMessages(Cosmetic<CosmeticTypeKillMessages> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeKillMessages> getCosmetic()
        {
            return this.cosmetic;
        }

    }

    /**
     * Creates a new Kill message (object).
     */
    public CosmeticTypeKillMessages()
    {
        // ...
    }

}
