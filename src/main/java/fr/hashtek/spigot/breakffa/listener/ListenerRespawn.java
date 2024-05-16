package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerRespawn implements Listener
{

    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerRespawn.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerRespawn(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


    /**
     * Called when a player respawns.
     * Should never fire, but we're preventive ;)
     */
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.gameManager.getPlayerData(player);

        event.setRespawnLocation(this.gameManager.getLobbySpawnLocation());

        playerData.getPlayerManager().backToLobby();
    }

}
