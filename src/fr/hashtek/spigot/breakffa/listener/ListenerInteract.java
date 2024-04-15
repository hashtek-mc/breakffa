package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerInteract implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerInteract.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerInteract(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    /**
     * Called when a player interacts with something in the world.
     * Here, we cancel the event when player right-clicks on the nexus
     * (to avoid beacon GUI popup).
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && this.gameManager.getNexus().equals(block))
            event.setCancelled(true);
    }

}
