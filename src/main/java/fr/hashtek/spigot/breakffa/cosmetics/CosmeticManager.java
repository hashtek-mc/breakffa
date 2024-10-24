package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CosmeticManager
{

    /**
     * Function used to get the cosmetic setter according to a Player's Cosmetic Manager.
     *
     * @param   <T>     Cosmetic
     */
    public interface CurrentCosmeticSetter<T extends Cosmetic<? extends AbstractCosmetic>>
    {

        /**
         * @param   manager     Cosmetic Manager of the player who clicked
         */
        Consumer<T> getSetter(CosmeticManager manager);

    }

    /**
     * Function used to get the cosmetic getter according to a Player's Cosmetic Manager.
     *
     * @param   <T>     Cosmetic
     */
    public interface CurrentCosmeticGetter<T extends Cosmetic<? extends AbstractCosmetic>>
    {

        /**
         * @param   manager     Cosmetic Manager of the player who clicked
         */
        Supplier<T> getGetter(CosmeticManager manager);

    }


    private final List<Cosmetic<? extends AbstractCosmetic>> ownedCosmetics;

    private Cosmetic<CosmeticTypeBlock> currentBlock;
    private Cosmetic<CosmeticTypeEmblem> currentEmblem;
    private Cosmetic<CosmeticTypeHat> currentHat;
    private Cosmetic<CosmeticTypeKillMessages> currentKillMessages;
    private Cosmetic<CosmeticTypeKillParticles> currentKillParticles;
    private Cosmetic<CosmeticTypeKillSfx> currentKillSfx;
    private Cosmetic<CosmeticTypeNexusSfx> currentNexusSfx;


    /**
     * Creates a new instance of CosmeticManager.
     */
    public CosmeticManager()
    {
        this.ownedCosmetics = new ArrayList<Cosmetic<? extends AbstractCosmetic>>();
        this.loadData();
    }

    /**
     * Loads the data from the Redis database.
     * TODO: Finish this.
     */
    public void loadData()
    {
        // ...
    }

    /**
     * @return  Every cosmetic that exists
     */
    @SuppressWarnings("unchecked")
    public static List<Cosmetic<? extends AbstractCosmetic>> getCosmetics()
    {
        Class<? extends Enum<? extends CosmeticCategoryArticles<? extends AbstractCosmetic>>>[] cosmeticEnums =
            new Class[] {
                CosmeticTypeHat.Hat.class,
                CosmeticTypeKillSfx.KillSfx.class,
                CosmeticTypeBlock.Block.class,
                CosmeticTypeEmblem.Emblem.class,
                CosmeticTypeKillMessages.KillMessages.class,
                CosmeticTypeNexusSfx.NexusSfx.class,
                CosmeticTypeKillParticles.KillParticles.class
            };

        return Arrays.stream(cosmeticEnums)
            .flatMap(enumClass -> Arrays.stream(enumClass.getEnumConstants())
            .map(enumValue -> ((CosmeticCategoryArticles<? extends AbstractCosmetic>) enumValue).getCosmetic()))
            .collect(Collectors.toList());
    }

    /**
     * @return  Cosmetic that player owns
     */
    public List<Cosmetic<? extends AbstractCosmetic>> getOwnedCosmetics()
    {
        return this.ownedCosmetics;
    }

    /**
     * @return  Current Nexus Sfx
     */
    public Cosmetic<CosmeticTypeNexusSfx> getCurrentNexusSfx()
    {
        return this.currentNexusSfx;
    }

    /**
     * @return  Current Kill Sfx
     */
    public Cosmetic<CosmeticTypeKillSfx> getCurrentKillSfx()
    {
        return this.currentKillSfx;
    }

    /**
     * @return  Current Kill Particles
     */
    public Cosmetic<CosmeticTypeKillParticles> getCurrentKillParticles()
    {
        return this.currentKillParticles;
    }

    /**
     * @return  Current Kill Messages
     */
    public Cosmetic<CosmeticTypeKillMessages> getCurrentKillMessages()
    {
        return this.currentKillMessages;
    }

    /**
     * @return  Current Hat
     */
    public Cosmetic<CosmeticTypeHat> getCurrentHat()
    {
        return this.currentHat;
    }

    /**
     * @return  Current Emblem
     */
    public Cosmetic<CosmeticTypeEmblem> getCurrentEmblem()
    {
        return this.currentEmblem;
    }

    /**
     * @return  Current Block
     */
    public Cosmetic<CosmeticTypeBlock> getCurrentBlock()
    {
        return this.currentBlock;
    }

    /**
     * @param   currentBlock    New Current Block
     */
    public void setCurrentBlock(Cosmetic<CosmeticTypeBlock> currentBlock)
    {
        this.currentBlock = currentBlock;
    }

    /**
     * @param   currentEmblem   New Current Emblem
     */
    public void setCurrentEmblem(Cosmetic<CosmeticTypeEmblem> currentEmblem)
    {
        this.currentEmblem = currentEmblem;
    }

    /**
     * @param   currentHat  New Current Hat
     */
    public void setCurrentHat(Cosmetic<CosmeticTypeHat> currentHat)
    {
        this.currentHat = currentHat;
    }

    /**
     * @param   currentKillMessages New Current Kill Messages
     */
    public void setCurrentKillMessage(Cosmetic<CosmeticTypeKillMessages> currentKillMessages)
    {
        this.currentKillMessages = currentKillMessages;
    }

    /**
     * @param   currentKillParticles    New Current Kill Particles
     */
    public void setCurrentKillParticles(Cosmetic<CosmeticTypeKillParticles> currentKillParticles)
    {
        this.currentKillParticles = currentKillParticles;
    }

    /**
     * @param   currentKillSfx  New Current Kill Sfx
     */
    public void setCurrentKillSfx(Cosmetic<CosmeticTypeKillSfx> currentKillSfx)
    {
        this.currentKillSfx = currentKillSfx;
    }

    /**
     * @param   currentNexusSfx New Current Nexus Sfx
     */
    public void setCurrentNexusSfx(Cosmetic<CosmeticTypeNexusSfx> currentNexusSfx)
    {
        this.currentNexusSfx = currentNexusSfx;
    }

}
