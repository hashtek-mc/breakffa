package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticAvailability;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CosmeticTypeHat
    extends AbstractCosmetic
{

    public enum Hat
        implements CosmeticCategoryArticles<CosmeticTypeHat>
    {

        ANVIL (
            new Cosmetic<CosmeticTypeHat>(
                "ANVILLOL",
                Material.ANVIL,
                "anvil lol",
                "miau",
                447,
                new CosmeticTypeHat(new HashItem(Material.ANVIL)),
                CosmeticAvailability.EXCLUSIVE
            ) {
                @Override
                protected boolean buyConditions(Player player) {
                    if (player.getName().endsWith("y")) {
                        return true;
                    }
                    return false;
                }
            }
        );


        private final Cosmetic<CosmeticTypeHat> cosmetic;


        /**
         * Creates a new Hat (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        Hat(Cosmetic<CosmeticTypeHat> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeHat> getCosmetic()
        {
            return this.cosmetic;
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
