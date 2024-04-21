package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.game.Nexus;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBreak implements Listener
{

    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerBreak
     *
     * @param   main    BreakFFA instance
     */
    public ListenerBreak(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }

    /**
     * Called when a player breaks a block.
     */
    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        final Nexus nexus = this.gameManager.getNexus();

        /* Nexus break handling */
        if (block.equals(nexus.getBlock())) {
            event.setCancelled(true);
            nexus.destroy(player);
            return;
        }

        /* If block was not placed by a player, cancel the event. */
        if (!this.gameManager.getPlacedBlocks().contains(block))
            event.setCancelled(true);
    }

}
