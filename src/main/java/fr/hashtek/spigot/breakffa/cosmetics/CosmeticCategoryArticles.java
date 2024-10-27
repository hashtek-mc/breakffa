package fr.hashtek.spigot.breakffa.cosmetics;

/**
 * Interface for cosmetics stored in a Cosmetic category.
 */
public interface CosmeticCategoryArticles
    <T extends AbstractCosmetic>
{

    /**
     * @return  Cosmetic
     */
    Cosmetic<T> getCosmetic();

}
