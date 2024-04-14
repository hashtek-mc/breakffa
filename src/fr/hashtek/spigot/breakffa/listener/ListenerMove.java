package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerMove implements Listener
{

    private final GameManager gameManager;


    public ListenerMove(BreakFFA main)
    {
        this.gameManager = main.getGameManager();
    }


    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        if (event.getTo().getBlockY() >= this.gameManager.getGameSettings().getMinHeight())
            return;

        final Player player = event.getPlayer();
        final PlayerData playerData = this.gameManager.getPlayerData(player);
        final PlayerManager playerManager = playerData.getPlayerManager();

        playerManager.kill();
    }

}
