package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            "omori",
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


    private final List<Sound> sfxs;


    /**
     * Creates a Kill SFX (object).
     *
     * @param   sfxs    SFX to play when killing someone
     */
    public CosmeticTypeKSFX(Sound... sfxs)
    {
        this.sfxs = new ArrayList<Sound>();
        this.sfxs.addAll(Arrays.asList(sfxs));
    }


    @Override
    public void preview(Player player)
    {
        player.stopSound(SoundStop.all());

        this.sfxs.forEach((Sound sound) ->
            player.playSound(player.getLocation(), sound, 1, 1)
        );
    }

    @Override
    public boolean canBePreviewed()
    {
        return true;
    }


    /**
     * @return  SFXs
     */
    public List<Sound> getSfxs()
    {
        return this.sfxs;
    }

}
