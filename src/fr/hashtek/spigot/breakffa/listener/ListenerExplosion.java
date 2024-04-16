package fr.hashtek.spigot.breakffa.listener;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class ListenerExplosion implements Listener
{

    /**
     * Called when an entity explodes.
     * In this case, we only process TNT (for Instant TNTs).
     */
    @EventHandler
    public void onTNTExplode(EntityExplodeEvent event)
    {
        if (event.getEntityType() != EntityType.PRIMED_TNT)
            return;

        final TNTPrimed tnt = (TNTPrimed) event.getEntity();
        final Location tntLocation = tnt.getLocation();
        final double radius = 4.0;

        /* Cancels the damage caused to the map. */
        event.blockList().clear();

        /* Pushes every entity around that TNT. */
        for (Entity entity : tnt.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player)) // FIXME: Maybe useless.
                continue;

            final Player player = (Player) entity;
            final Vector direction = player.getLocation().toVector().subtract(tntLocation.toVector()).normalize();

            direction.multiply(1.75);
            player.setVelocity(direction);
        }
    }

}
