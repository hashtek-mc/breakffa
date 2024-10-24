package fr.hashtek.spigot.breakffa.cosmetics;

import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKillSFX;
import org.bukkit.entity.Player;

public abstract class AbstractCosmetic
{

    /**
     * Previews the cosmetic to a player.
     * <br>
     * Some cosmetic can't be previewed (like custom helmets) so,
     * by default, this function is empty.
     * <br>
     * For example, see {@link CosmeticTypeKillSFX#preview(Player)}.
     * <br>
     *
     * @param   player  Player
     */
    public void preview(Player player) {}

    /**
     * @return  True if cosmetic can be previewed.
     */
    public boolean canBePreviewed()
    {
        return false;
    }

}
