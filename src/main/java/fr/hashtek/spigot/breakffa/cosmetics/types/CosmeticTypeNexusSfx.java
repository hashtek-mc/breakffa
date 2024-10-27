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

public class CosmeticTypeNexusSfx
    extends AbstractCosmetic
{

    public enum NexusSfx
        implements CosmeticCategoryArticles<CosmeticTypeNexusSfx>
    {

        NINE_MM (new Cosmetic<CosmeticTypeNexusSfx>(
            Material.GOLDEN_SWORD,
            "9mm",
            "caca",
            100,
            new CosmeticTypeNexusSfx(Sound.BLOCK_WATER_AMBIENT)
        )),

        CACA (new Cosmetic<CosmeticTypeNexusSfx>(
            Material.FISHING_ROD,
            "huitre",
            "omori",
            1060,
            new CosmeticTypeNexusSfx(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS)
        ));


        private final Cosmetic<CosmeticTypeNexusSfx> cosmetic;


        /**
         * Creates a new Nexus SFX (cosmetic).
         *
         * @param   cosmetic    Cosmetic
         */
        NexusSfx(Cosmetic<CosmeticTypeNexusSfx> cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        /**
         * @return  Cosmetic
         */
        public Cosmetic<CosmeticTypeNexusSfx> getCosmetic()
        {
            return cosmetic;
        }

    }


    private final List<Sound> sfxs;


    /**
     * Creates a Nexus SFX (object).
     *
     * @param   sfxs    SFXs to play when breaking the Nexus
     */
    public CosmeticTypeNexusSfx(Sound... sfxs)
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
     * @return  SFXs to play when breaking the Nexus
     */
    public List<Sound> getSfxs()
    {
        return this.sfxs;
    }

}
