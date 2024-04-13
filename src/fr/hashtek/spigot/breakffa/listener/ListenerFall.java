package fr.hashtek.spigot.breakffa.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerFall implements Listener
{

    @EventHandler
    public void onFall(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setCancelled(true);
    }

}
