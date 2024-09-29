package fr.hashtek.spigot.breakffa.cosmetics.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.AbstractCosmetic;
import org.bukkit.Sound;

public class KillSfx extends AbstractCosmetic
{

    public enum Cosmetic
    {

        NINE_MM (new KillSfx(
            "9mm",
            "heras",
            1,
            Sound.BLOCK_ANVIL_LAND
        )),

        TOILET (new KillSfx(
            "toilets",
            "léa",
            155,
            Sound.BLOCK_WATER_AMBIENT
        ));


        private final KillSfx cosmetic;


        Cosmetic(KillSfx cosmetic)
        {
            this.cosmetic = cosmetic;
        }


        public KillSfx getCosmetic()
        {
            return this.cosmetic;
        }

    }


    private final Sound sfx;


    public KillSfx(
        String name,
        String description,
        int price,
        Sound sfx
    )
    {
        super(name, description, price);
        this.sfx = sfx;
    }


    public Sound getSfx()
    {
        return this.sfx;
    }

}
