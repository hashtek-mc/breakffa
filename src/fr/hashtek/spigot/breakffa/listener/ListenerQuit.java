package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerQuit implements Listener, HashLoggable
{

    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerQuit.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerQuit(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


    /**
     * Called when a player leaves the server.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();

        event.setQuitMessage(null);

        this.gameManager.removePlayerData(player);
    }

}
