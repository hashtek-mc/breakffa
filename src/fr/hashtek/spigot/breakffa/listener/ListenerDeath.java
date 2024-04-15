package fr.hashtek.spigot.breakffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ListenerDeath implements Listener
{

    /**
     * Called when a player dies.
     * Should never happen, but we're preventive ;)
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        event.setDeathMessage(null);
        event.getDrops().clear();
    }

}
