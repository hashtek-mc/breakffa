package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerDamageByEntity implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerDamageByEntity
     *
     * @param   main    BreakFFA instance
     */
    public ListenerDamageByEntity(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    /**
     * Called when an entity takes damage from another entity.
     * In this case, we only process players (PVP).
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;

        final Player victim = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();
        final PlayerData victimData = this.gameManager.getPlayerData(victim);
        final PlayerData damagerData = this.gameManager.getPlayerData(damager);

        /* If one of the two players is not playing, cancel the event. */
        if (victimData.getState() != PlayerState.PLAYING || damagerData.getState() != PlayerState.PLAYING)
            event.setCancelled(true);

        /* For kill author detection. */
        victimData.setLastDamager(damager);

        /* For kill. */
        if (victim.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            victimData.getPlayerManager().kill();
        }
    }

}
