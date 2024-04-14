package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

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
        final Block block = event.getBlockPlaced();

        if (block.getType() == Material.TNT) { // Insta TNT Handling
            final World world = this.main.getServer().getWorld("breakffa");
            final Location blockLocation = block.getLocation();
            final TNTPrimed tnt = (TNTPrimed) world.spawnEntity(blockLocation, EntityType.PRIMED_TNT);

            tnt.setFuseTicks(0);
            event.setCancelled(true);
            return;
        }

        this.gameManager.getPlacedBlocks().add(block);
    }

}
