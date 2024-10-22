package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * KSFX stands for Kill SFX.
 */
public class CosmeticTypeKSFX extends AbstractCosmetic
{

    public enum KillSfx implements CosmeticCategoryArticles<CosmeticTypeKSFX>
    {

        NINE_MM (new Cosmetic<CosmeticTypeKSFX>(
            Material.GOLDEN_SWORD,
            "9mm",
            "caca",
            100,
            new CosmeticTypeKSFX(Sound.BLOCK_WATER_AMBIENT)
        )),

        CACA (new Cosmetic<CosmeticTypeKSFX>(
            Material.FISHING_ROD,
            "huitre",
            "nathan j",
            1060,
            new CosmeticTypeKSFX(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS)
        ));


        private final Cosmetic<CosmeticTypeKSFX> cosmetic;


        /**
         * Creates a new Kill SFX (cosmetic).
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
     * Creates a Kill SFX (object).
     *
     * @param   sfx         SFX to play when killing someone
     */
    public CosmeticTypeKSFX(Sound sfx)
    {
        this.sfx = sfx;
    }


    @Override
    public void preview(Player player)
    {
        player.stopSound(SoundStop.all());
        player.playSound(player.getLocation(), this.getSfx(), 1, 1);
    }

    @Override
    public boolean canBePreviewed()
    {
        return true;
    }


    /**
     * @return  SFX
     */
    public Sound getSfx()
    {
        return this.sfx;
    }

}
