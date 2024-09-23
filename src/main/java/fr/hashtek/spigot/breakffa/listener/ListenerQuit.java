package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.hashboard.exceptions.StrangeException;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.tablist.TablistManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerQuit implements Listener, HashLoggable
{

    private final BreakFFA main;
    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerQuit.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerQuit(BreakFFA main)
    {
        this.main = main;
        this.gameManager = main.getGameManager();
    }


    /**
     * Called when a player leaves the server.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        final TablistManager tablist = this.main.getTablistManager();

        event.quitMessage(null);

        this.gameManager.removePlayerData(player);

        try {
            tablist.refresh(true);
            tablist.update();
        } catch (StrangeException exception) {
            // TODO: Write error handling (even if none of them should happen).
        }
    }

}
