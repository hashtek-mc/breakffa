package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.tekore.bukkit.Tekore;
import fr.hashtek.tekore.common.Rank;
import fr.hashtek.tekore.common.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChat implements Listener
{

    private final Tekore core;


    public ListenerChat(BreakFFA main)
    {
        this.core = main.getCore();
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.core.getPlayerData(player);
        final Rank playerRank = playerData.getRank();

        event.setFormat(
            playerRank.getColor() + player.getName() + "@" + playerRank.getName()
                + ChatColor.DARK_GRAY + " $ "
                + ChatColor.WHITE + event.getMessage()
        );
    }

}
