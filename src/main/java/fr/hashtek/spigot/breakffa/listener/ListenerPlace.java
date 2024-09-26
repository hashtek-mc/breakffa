package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.game.Nexus;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ListenerPlace implements Listener
{

    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerPlace.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerPlace(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


    /**
     * Called when a player places a block.
     * TODO: Split this function's content into multiple other functions.
     */
    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlockPlaced();
        final Location blockLocation = block.getLocation();
        final Material blockReplacedType = event.getBlockReplacedState().getType();
        final World world = block.getWorld();
        final Nexus nexus = this.gameManager.getNexus();
        final Location nexusLocation = nexus.getBlock().getLocation();

        /* If block is being placed where there's already a block, cancel the event. */
        if (blockReplacedType != Material.AIR) {
            event.setCancelled(true);
            return;
        }

        /* If block is in the spawn protection, cancel the event. */
        Location nearestSpawn = null;
        double nearestDistanceSquared = Double.MAX_VALUE;

        for (Location location : this.gameManager.getSpawnLocations()) {
            final double distanceSquared = location.distanceSquared(blockLocation);

            if (distanceSquared < nearestDistanceSquared) {
                nearestSpawn = location;
                nearestDistanceSquared = distanceSquared;
            }
        }

        if (nexusLocation.distanceSquared(blockLocation) < nearestDistanceSquared) {
            nearestSpawn = nexusLocation;
            nearestDistanceSquared = nexusLocation.distanceSquared(blockLocation);
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

            tnt.setCustomName(player.getName());
            tnt.setCustomNameVisible(false);

            tnt.setFuseTicks(0);
            return;
        }

        /* If block is above the max height limit, cancel the event. */
        if (blockLocation.getBlockY() >= this.gameManager.getGameSettings().getMaxHeight()) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Vous ne pouvez pas construire plus haut.");
        }

        /* If neither of the above cases, keep the item in hand and add the block to placed blocks list. */
        final ItemStack item = event.getItemInHand();
        final PlayerInventory inv = player.getInventory();

        if (event.getHand() == EquipmentSlot.OFF_HAND) { // Second hand handling
            inv.setItemInOffHand(item);
        } else {
            inv.setItem(player.getInventory().getHeldItemSlot(), item);
        }

        this.gameManager.getPlacedBlocks().add(block);
    }

}
