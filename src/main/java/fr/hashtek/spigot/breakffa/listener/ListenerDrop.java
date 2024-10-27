package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerDrop
    implements Listener
{

    /**
     * Called when a player drops an item.
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerState playerState = BreakFFA.getInstance().getGameManager()
            .getPlayerManager(player).getData().getState();

        /* If player is in the lobby, cancel the event. */
        if (playerState.isInLobby()) {
            event.setCancelled(true);
        }
    }

}
