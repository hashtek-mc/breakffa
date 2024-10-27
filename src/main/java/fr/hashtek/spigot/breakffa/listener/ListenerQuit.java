package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerQuit
    implements Listener, HashLoggable
{

    /**
     * Called when a player leaves the server.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        final BreakFFA main = BreakFFA.getInstance();
//        final TablistManager tablist = this.main.getTablistManager();

        event.quitMessage(null);

        main.getGameManager()
            .removePlayerManager(player)
            .pushData(main.getCore().getRedisAccess());

//        try {
//            tablist.refresh(true);
//            tablist.update();
//        } catch (StrangeException exception) {
//            // TODO: Write error handling (even if none of them should happen).
//        }
    }

}
