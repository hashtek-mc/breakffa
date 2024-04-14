package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerDamageEvent implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    public ListenerDamageEvent(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;

        final Player victim = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();
        final PlayerData victimData = this.gameManager.getPlayerData(victim);
        final PlayerData damagerData = this.gameManager.getPlayerData(damager);

        if (victimData.getState() != PlayerState.PLAYING || damagerData.getState() != PlayerState.PLAYING)
            event.setCancelled(true);

        victimData.setLastDamager(damager);

        if (victim.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            victimData.getPlayerManager().kill();
        }
    }

}
