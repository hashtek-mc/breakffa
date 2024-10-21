package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerJoin implements Listener, HashLoggable
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    /**
     * Called when a player joins the server.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final GameManager gameManager = MAIN.getGameManager();
        final PlayerManager playerManager = gameManager.createPlayerManager(player);
//        final ScoreboardManager scoreboardManager = this.main.getBoardManager();
//        final TablistManager tablistManager = this.main.getTablistManager();

        event.joinMessage(null);

        playerManager.backToLobby();

//        this.main.getBoardManager().getBoard().setToPlayers(player);
//
//        try {
//            final fr.hashtek.tekore.common.player.PlayerData pData =
//                this.main.getCore().getPlayerManager(player).getData();
//
//            this.main.getRankTeams().get(pData.getRank().getUuid()).add(player);
//            tablistManager.refresh();
//            tablistManager.update();
//        } catch (AlreadyInTeamException | TeamFullException | StrangeException exception) {
//            // TODO: Write error handling (even if none of them should happen).
//        }
//
//        try {
//            scoreboardManager.addPlayerSidebar(player).refreshSidebar();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            // TODO: Write proper error handling
//        }
    }

}
