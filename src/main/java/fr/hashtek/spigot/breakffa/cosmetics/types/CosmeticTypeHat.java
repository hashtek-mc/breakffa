package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;

public class CosmeticTypeHat extends AbstractCosmetic
{

    public enum CustomHelmet implements CosmeticCategoryArticles<CosmeticTypeHat>
    {

        ANVIL (new Cosmetic<CosmeticTypeHat>(
            Material.ANVIL,
            "anvil lol",
            "miau",
            447,
            new CosmeticTypeHat(new HashItem(Material.ANVIL))
        ));


        private final Cosmetic<CosmeticTypeHat> cosmetic;


        /**
         * Creates a new Hat (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        CustomHelmet(Cosmetic<CosmeticTypeHat> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeHat> getCosmetic()
        {
            return cosmetic;
        }

    }


    private final HashItem hat;


    /**
     * Creates a new Hat (object).
     *
     * @param   hat     Hat
     */
    public CosmeticTypeHat(HashItem hat)
    {
        this.hat = hat;
    }


    /**
     * @return  Hat
     */
    public HashItem getHat()
    {
        return this.hat;
    }

}
