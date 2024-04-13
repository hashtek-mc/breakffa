package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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

        this.gameManager.getPlacedBlocks().add(block);
    }

}
