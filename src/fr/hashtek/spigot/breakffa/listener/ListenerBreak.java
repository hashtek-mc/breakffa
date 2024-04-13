package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBreak implements Listener
{

    private final GameManager gameManager;


    public ListenerBreak(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();

        if (block != this.gameManager.getNexus()) {
            event.setCancelled(!this.gameManager.getPlacedBlocks().contains(block));
            return;
        }

        // TODO: Nexus break handling.
    }

}
