package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.hashitem.HashItem;

public class CosmeticTypeBlock
    extends AbstractCosmetic
{

    public enum Block
        implements CosmeticCategoryArticles<CosmeticTypeBlock>
    {

        ;


        private final Cosmetic<CosmeticTypeBlock> cosmetic;


        /**
         * Creates a new Block (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        Block(Cosmetic<CosmeticTypeBlock> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeBlock> getCosmetic()
        {
            return this.cosmetic;
        }

    }


    private final HashItem block;


    /**
     * Creates a new Block (object).
     *
     * @param   block   Block
     */
    public CosmeticTypeBlock(HashItem block)
    {
        this.block = block;
    }


    /**
     * @return  Block
     */
    public HashItem getBlock()
    {
        return this.block;
    }

}
