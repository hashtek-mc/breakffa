package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerRespawn
    implements Listener
{

    /**
     * Called when a player respawns.
     * Should never fire, but we're preventive ;)
     */
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        final Player player = event.getPlayer();
        final GameManager gameManager = BreakFFA.getInstance().getGameManager();
        final PlayerManager playerManager = gameManager.getPlayerManager(player);

        event.setRespawnLocation(gameManager.getLobbySpawnLocation());

        playerManager.backToLobby();
    }

}
