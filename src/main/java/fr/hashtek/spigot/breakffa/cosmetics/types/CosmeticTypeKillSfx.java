package fr.hashtek.spigot.breakffa.cosmetics.types;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticCategoryArticles;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosmeticTypeKillSfx extends AbstractCosmetic
{

    public enum KillSfx implements CosmeticCategoryArticles<CosmeticTypeKillSfx>
    {

        NINE_MM (new Cosmetic<CosmeticTypeKillSfx>(
            Material.GOLDEN_SWORD,
            "9mm",
            "caca",
            100,
            new CosmeticTypeKillSfx(Sound.BLOCK_WATER_AMBIENT)
        )),

        CACA (new Cosmetic<CosmeticTypeKillSfx>(
            Material.FISHING_ROD,
            "huitre",
            "omori",
            1060,
            new CosmeticTypeKillSfx(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS)
        ));


        private final Cosmetic<CosmeticTypeKillSfx> cosmetic;


        /**
         * Creates a new Kill SFX (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        KillSfx(Cosmetic<CosmeticTypeKillSfx> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeKillSfx> getCosmetic()
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
    public CosmeticTypeKillSfx(Sound... sfxs)
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
