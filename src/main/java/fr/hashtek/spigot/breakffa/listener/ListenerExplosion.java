package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class ListenerExplosion implements Listener
{

    /**
     * Called when an entity explodes.
     * In this case, we only process TNT (for Instant TNTs).
     */
    @EventHandler
    public void onTNTExplode(EntityExplodeEvent event)
    {
        if (event.getEntityType() != EntityType.PRIMED_TNT) {
            return;
        }

        final BreakFFA main = BreakFFA.getInstance();

        final TNTPrimed tnt = (TNTPrimed) event.getEntity();
        final Player author = main.getServer().getPlayer(Objects.requireNonNull(tnt.getCustomName()));
        final Location tntLocation = tnt.getLocation();
        final double radius = 4.0;

        /* Cancels the damage caused to the map. */
        event.blockList().clear();

        /* Pushes every entity around that TNT. */
        for (Entity entity : tnt.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player player)) {
                continue;
            }

            final PlayerManager playerManager = main.getGameManager().getPlayerManager(player);
            final Vector direction = player.getLocation().toVector().subtract(tntLocation.toVector()).normalize();

            /* Applying knockback to the player. */
            direction.multiply(1.75);
            player.setVelocity(direction);

            /* Setting player's last damager to explosion author. */
            if (!Objects.requireNonNull(author).equals(player)) {
                playerManager.setLastDamager(author);
                playerManager.setLastDamagerWeapon(KitStarter.Items.INSTANT_TNT.getItem().getItemStack());
            }
        }
    }

}
