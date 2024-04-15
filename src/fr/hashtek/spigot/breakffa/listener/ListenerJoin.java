package fr.hashtek.spigot.breakffa.listener;

import fr.hashktek.spigot.hashboard.exceptions.AlreadyInTeamException;
import fr.hashktek.spigot.hashboard.exceptions.StrangeException;
import fr.hashktek.spigot.hashboard.exceptions.TeamSizeException;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerJoin implements Listener, HashLoggable
{

    private final BreakFFA main;
    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerJoin.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerJoin(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    /**
     * Called when a player joins the server.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerData playerData = new PlayerData(this.main, player, this.gameManager);

        this.gameManager.addPlayerData(player, playerData);

        event.setJoinMessage(null);

        playerData.getPlayerManager().backToLobby();

        this.main.getBoard().setToPlayers(player);

        try {
            final fr.hashtek.tekore.common.player.PlayerData pData = this.main.getCore().getPlayerData(player);
            this.main.getTablist().update(player);
            this.main.getRankTeams().get(pData.getRank().getUuid()).add(player);
        } catch (AlreadyInTeamException | TeamSizeException | StrangeException exception) {
            // TODO: Write error handling (even if none of them should happen).
        }
    }

}
