package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;

/**
 * TODO: Documentation is messy >:(
 */
public class CosmeticTypeCustomHelmet
{

    public enum CustomHelmet implements CosmeticCategoryArticles
    {

        ANVIL (new Cosmetic<CosmeticTypeCustomHelmet>(
            "anvil lol",
            "miau",
            447,
            new CosmeticTypeCustomHelmet(new HashItem(Material.ANVIL))
        ));


        private final Cosmetic<CosmeticTypeCustomHelmet> cosmetic;


        /**
         * Creates a new cosmetic of type Custom Helmet
         *
         * @param   cosmetic    Cosmetic
         */
        CustomHelmet(Cosmetic<CosmeticTypeCustomHelmet> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeCustomHelmet> getCosmetic()
        {
            return cosmetic;
        }

    }


    private final HashItem helmet;


    /**
     * Creates a Custom Helmet.
     *
     * @param   helmet  Custom helmet
     */
    public CosmeticTypeCustomHelmet(HashItem helmet)
    {
        this.helmet = helmet;
    }


    /**
     * @return  Helmet
     */
    public HashItem getHelmet()
    {
        return this.helmet;
    }

}
