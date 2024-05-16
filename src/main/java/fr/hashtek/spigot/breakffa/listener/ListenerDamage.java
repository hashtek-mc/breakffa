package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerDamage implements Listener
{

    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerDamager.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerDamage(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


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
        if (!(event.getEntity() instanceof Player))
            return;

        /* If damage cause is unwanted, cancel the event. */
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL ||
            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            event.setCancelled(true);
            return;
        }

        final Player player = (Player) event.getEntity();
        final PlayerData playerData = this.gameManager.getPlayerData(player);
        final PlayerManager playerManager = playerData.getPlayerManager();

        /* If player is going to die, custom kill. */
        if (player.getHealth() - event.getFinalDamage() <= 0) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                return;

            if (event.getCause() == EntityDamageEvent.DamageCause.THORNS)
                playerData.setLastDamagerWeapon(playerData.getLastDamager().getInventory().getChestplate());

            event.setCancelled(true);
            playerManager.kill(DeathReason.fromDamageCause(event.getCause()));
        }
    }

}
