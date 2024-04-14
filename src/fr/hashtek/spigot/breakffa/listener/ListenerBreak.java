package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBreak implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    public ListenerBreak(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        final Block block = event.getBlock();

        if (block.equals(this.gameManager.getNexus())) {
            final Player player = event.getPlayer();
            final PlayerData playerData = this.gameManager.getPlayerData(player);

            this.main.getServer().broadcastMessage("nexus cassé par " + player.getDisplayName() + " wéééé");

            for (Player p : this.main.getServer().getOnlinePlayers()) {
                final PlayerData pData = this.gameManager.getPlayerData(p);

                // title (deprecated)
                p.sendTitle(player.getDisplayName(), "a cassé le nexus wula (il est à " + playerData.getNexusBreaks() + ")");

                if (pData.getPlayer().equals(player))
                    continue;

                pData.setNexusBreaksStreak(0);
            }

            event.setCancelled(true);
            this.gameManager.reset();

            playerData.addNexusBreaks(1);
            playerData.addNexusBreaksStreak(1);

            for (PlayerData pData : this.gameManager.getPlayersData().values()) {

            }
            return;
        }

        if (!this.gameManager.getPlacedBlocks().contains(block))
            event.setCancelled(true);
    }

}
