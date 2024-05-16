package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.scoreboard.ScoreboardManager;
import fr.hashtek.spigot.hashboard.exceptions.AlreadyInTeamException;
import fr.hashtek.spigot.hashboard.exceptions.StrangeException;
import fr.hashtek.spigot.hashboard.exceptions.TeamSizeException;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.tablist.TablistManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerJoin implements Listener, HashLoggable
{

    private final BreakFFA main;


    /**
     * Creates a new instance of ListenerJoin.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerJoin(BreakFFA main)
    {
        this.main = main;
    }


    /**
     * Called when a player joins the server.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerData playerData = new PlayerData(this.main, player);
        final GameManager gameManager = this.main.getGameManager();
        final ScoreboardManager scoreboardManager = this.main.getBoardManager();
        final TablistManager tablistManager = this.main.getTablistManager();

        event.setJoinMessage(null);

        gameManager.addPlayerData(player, playerData);

        playerData.getPlayerManager().backToLobby();

        this.main.getBoardManager().getBoard().setToPlayers(player);

        try {
            final fr.hashtek.tekore.common.player.PlayerData pData = this.main.getCore().getPlayerData(player);

            this.main.getRankTeams().get(pData.getRank().getUuid()).add(player);
            tablistManager.refresh();
            tablistManager.update();
        } catch (AlreadyInTeamException | TeamSizeException | StrangeException exception) {
            // TODO: Write error handling (even if none of them should happen).
        }

        scoreboardManager.addPlayerSidebar(player).refreshSidebar();
    }

}
