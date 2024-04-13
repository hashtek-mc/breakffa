package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ListenerDeath implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    public ListenerDeath(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        final Player player = event.getEntity();
        final PlayerData playerData = this.gameManager.getPlayerData(player);

        event.setKeepInventory(true);
        event.setDeathMessage("haha " + player.getDisplayName() + " est mort xdlol quelle merde ce type");
        player.setHealth(player.getMaxHealth());
        playerData.getPlayerManager().respawn();
    }

}