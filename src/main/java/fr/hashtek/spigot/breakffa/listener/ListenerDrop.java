package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerDrop implements Listener
{

    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerDrop.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerDrop(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


    /**
     * Called when a player drops an item.
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.gameManager.getPlayerData(player);

        /* If player is in the lobby, cancel the event. */
        if (playerData.getState() == PlayerState.AT_LOBBY) {
            event.setCancelled(true);
        }
    }

}
