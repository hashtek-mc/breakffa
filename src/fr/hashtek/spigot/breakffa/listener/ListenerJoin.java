package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
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
        Player player = event.getPlayer();
        GameManager gameManager = this.main.getGameManager();

        event.setJoinMessage(null);

        player.teleport(gameManager.getLobbySpawnLocation());
    }

}
