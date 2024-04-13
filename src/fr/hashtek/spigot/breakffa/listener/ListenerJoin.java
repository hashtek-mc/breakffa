package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import org.bukkit.GameMode;
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
        final PlayerData playerData = new PlayerData(player, gameManager);

        gameManager.addPlayerData(player, playerData);

        event.setJoinMessage(null);

        player.setGameMode(GameMode.SURVIVAL);
        playerData.getPlayerManager().backToLobby();
    }

}
