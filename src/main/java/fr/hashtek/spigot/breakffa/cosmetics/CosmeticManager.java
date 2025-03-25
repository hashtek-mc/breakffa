package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CosmeticManager {

    public interface CurrentCosmeticSetter<T extends Cosmetic<? extends AbstractCosmetic>> {
        Consumer<T> getSetter(CosmeticManager manager);
    }

    public interface CurrentCosmeticGetter<T extends Cosmetic<? extends AbstractCosmetic>> {
        Supplier<T> getGetter(CosmeticManager manager);
    }

    public static final Map<String, Cosmetic<? extends AbstractCosmetic>> COSMETIC_REGISTRY =
        getCosmetics().stream().collect(Collectors.toMap(Cosmetic::getRawName, cosmetic -> cosmetic));

    private final Set<Cosmetic<? extends AbstractCosmetic>> ownedCosmetics;

    private Cosmetic<CosmeticTypeBlock> currentBlock;
    private Cosmetic<CosmeticTypeEmblem> currentEmblem;
    private Cosmetic<CosmeticTypeHat> currentHat;
    private Cosmetic<CosmeticTypeKillMessages> currentKillMessages;
    private Cosmetic<CosmeticTypeKillParticles> currentKillParticles;
    private Cosmetic<CosmeticTypeKillSfx> currentKillSfx;
    private Cosmetic<CosmeticTypeNexusSfx> currentNexusSfx;

    public CosmeticManager() {
        this.ownedCosmetics = new HashSet<>();
    }

    public static Set<Cosmetic<? extends AbstractCosmetic>> getCosmetics() {
        final Class<? extends Enum<? extends CosmeticCategoryArticles<? extends AbstractCosmetic>>>[] cosmeticEnums =
            new Class[]{
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
            .collect(Collectors.toSet());
    }

    public Set<Cosmetic<? extends AbstractCosmetic>> getOwnedCosmetics() {
        return this.ownedCosmetics;
    }

    public Cosmetic<CosmeticTypeNexusSfx> getCurrentNexusSfx() {
        return this.currentNexusSfx;
    }

    public Cosmetic<CosmeticTypeKillSfx> getCurrentKillSfx() {
        return this.currentKillSfx;
    }

    public Cosmetic<CosmeticTypeKillParticles> getCurrentKillParticles() {
        return this.currentKillParticles;
    }

    public Cosmetic<CosmeticTypeKillMessages> getCurrentKillMessages() {
        return this.currentKillMessages;
    }

    public Cosmetic<CosmeticTypeHat> getCurrentHat() {
        return this.currentHat;
    }

    public Cosmetic<CosmeticTypeEmblem> getCurrentEmblem() {
        return this.currentEmblem;
    }

    public Cosmetic<CosmeticTypeBlock> getCurrentBlock() {
        return this.currentBlock;
    }

    public void setCurrentBlock(Cosmetic<CosmeticTypeBlock> currentBlock) {
        this.currentBlock = currentBlock;
    }

    public void setCurrentEmblem(Cosmetic<CosmeticTypeEmblem> currentEmblem) {
        this.currentEmblem = currentEmblem;
    }

    public void setCurrentHat(Cosmetic<CosmeticTypeHat> currentHat) {
        this.currentHat = currentHat;
    }

    public void setCurrentKillMessage(Cosmetic<CosmeticTypeKillMessages> currentKillMessages) {
        this.currentKillMessages = currentKillMessages;
    }

    public void setCurrentKillParticles(Cosmetic<CosmeticTypeKillParticles> currentKillParticles) {
        this.currentKillParticles = currentKillParticles;
    }

    public void setCurrentKillSfx(Cosmetic<CosmeticTypeKillSfx> currentKillSfx) {
        this.currentKillSfx = currentKillSfx;
    }

    public void setCurrentNexusSfx(Cosmetic<CosmeticTypeNexusSfx> currentNexusSfx) {
        this.currentNexusSfx = currentNexusSfx;
    }
}
