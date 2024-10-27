package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerDamage
    implements Listener
{

    /**
     * Called when an entity takes damage.
     * In this case, we only process players.
     * FIXME: For THORNS damages, detection only applies to chestplates, as the
     *        only armor piece that will have the THORNS enchantment
     *        are chestplates.
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        /* If damage cause is unwanted, cancel the event. */
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL ||
            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            event.setCancelled(true);
            return;
        }

        final PlayerManager playerManager = BreakFFA.getInstance().getGameManager().getPlayerManager(player);

        /* If player is going to die, custom kill. */
        if (player.getHealth() - event.getFinalDamage() <= 0) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                return;
            }

            if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                playerManager.setLastDamagerWeapon(playerManager.getLastDamager().getInventory().getChestplate());
            }

            event.setCancelled(true);
            playerManager.kill(DeathReason.fromDamageCause(event.getCause()));
        }
    }

}
