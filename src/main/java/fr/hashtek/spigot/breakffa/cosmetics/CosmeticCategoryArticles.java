package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.AbstractCosmetic;

/**
 * Interface for articles stored in a Cosmetic category.
 */
public interface CosmeticCategoryArticles<T extends AbstractCosmetic>
{

    /**
     * @return  Cosmetic
     */
    Cosmetic<T> getCosmetic();

}
