package fr.hashtek.spigot.breakffa.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerDamage implements Listener
{

    /**
     * Called when an entity takes damage.
     * In this case, we only process players.
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        /* If damage cause is unwanted, cancel the event. */
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL ||
            event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
            event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            event.setCancelled(true);
    }

}
