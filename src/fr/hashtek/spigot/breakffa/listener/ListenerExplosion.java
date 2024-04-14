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

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent event)
    {
        if (event.getEntityType() != EntityType.PRIMED_TNT)
            return;

        final TNTPrimed tnt = (TNTPrimed) event.getEntity();
        final Location tntLocation = tnt.getLocation();
        final double radius = 5.0;

        event.blockList().clear();

        for (Entity entity : tnt.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player)) // TODO: Maybe useless.
                continue;

            final Player player = (Player) entity;
            final Vector direction = player.getLocation().toVector().subtract(tntLocation.toVector()).normalize();

            direction.multiply(1.75);
            player.setVelocity(direction);
        }
    }

}
