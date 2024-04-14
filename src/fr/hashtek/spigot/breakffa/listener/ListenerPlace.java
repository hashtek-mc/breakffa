package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerPlace implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    public ListenerPlace(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlockPlaced();
        final Location blockLocation = block.getLocation();
        final Material blockReplacedType = event.getBlockReplacedState().getType();
        final World world = block.getWorld();

        /* If block is being placed where there's already a block, cancel the event */
        if (blockReplacedType != Material.AIR) {
            event.setCancelled(true);
            return;
        }

        Location nearestSpawn = null;
        double nearestDistanceSquared = Double.MAX_VALUE;

        for (Location location : this.gameManager.getSpawnLocations()) {
            final double distanceSquared = location.distanceSquared(blockLocation);

            if (distanceSquared < nearestDistanceSquared) {
                nearestSpawn = location;
                nearestDistanceSquared = distanceSquared;
            }
        }

        if (nearestSpawn != null) {
            if (Math.sqrt(nearestDistanceSquared) <= 3) {
                event.setCancelled(true);
                return;
            }
        }

        /* Instant TNT handling */
        if (block.getType() == Material.TNT) {
            world.getBlockAt(blockLocation).setType(Material.AIR);
            final TNTPrimed tnt = (TNTPrimed) world.spawnEntity(blockLocation, EntityType.PRIMED_TNT);

            tnt.setFuseTicks(0);
            return;
        }

        /* If neither of the above cases, keep the item in hand and add the block to placedBlocks */
        final ItemStack item = event.getItemInHand();

        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);

        this.gameManager.getPlacedBlocks().add(block);
    }

}
