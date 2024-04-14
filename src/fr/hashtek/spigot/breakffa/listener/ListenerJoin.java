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


    public ListenerJoin(BreakFFA main)
    {
        this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final GameManager gameManager = this.main.getGameManager();
        final PlayerData playerData = new PlayerData(this.main, player, gameManager);

        gameManager.addPlayerData(player, playerData);

        event.setJoinMessage(null);

        playerData.getPlayerManager().backToLobby();

        this.main.getBoard().setToPlayers(player);

        try {
            final fr.hashtek.tekore.common.player.PlayerData pData = this.main.getCore().getPlayerData(player);
            main.getTablist().update(player);
            main.getRankTeams().get(pData.getRank().getUuid()).add(player);
        } catch (AlreadyInTeamException | TeamSizeException | StrangeException exception) {
            // TODO: Write error handling (even if none of them should happen).
        }
    }

}
