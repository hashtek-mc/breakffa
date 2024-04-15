package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.tekore.common.Rank;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Nexus
{

    private final BreakFFA main;
    private final GameManager gameManager;
    private Block block;


    /**
     * Creates a new Nexus.
     *
     * @param   main    BreakFFA instance
     */
    public Nexus(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    /**
     * Breaks the Nexus.
     * This function is called "breqk" because "break" is
     * a Java keyword so, can't name it "break" :(
     *
     * @param   player  Player who broke the Nexus.
     */
    public void breqk(Player player)
    {
        final PlayerData playerData = this.gameManager.getPlayerData(player);
        final fr.hashtek.tekore.common.player.PlayerData corePlayerData = playerData.getCorePlayerData();
        final Rank playerRank = corePlayerData.getRank();

        this.main.getServer().broadcastMessage(ChatColor.WHITE + "Le Nexus a été cassé par " + playerRank.getColor() + playerRank.getShortName() + corePlayerData.getUsername() + ChatColor.WHITE + " !");

        for (Player p : this.main.getServer().getOnlinePlayers()) {
            final PlayerData pData = this.gameManager.getPlayerData(p);

            // TODO: Send title.

            if (pData.getPlayer().equals(player))
                continue;

            pData.setNexusBreaksStreak(0);
        }

        this.gameManager.reset();

        playerData.addNexusBreaks(1);
        playerData.addNexusBreaksStreak(1);
    }


    /**
     * @return  Block
     */
    public Block getBlock()
    {
        return this.block;
    }

    /**
     * @param   block   Block
     */
    public void setBlock(Block block)
    {
        this.block = block;
    }

}
