package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashutils.HashUtils;
import fr.hashtek.hashutils.Title;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import fr.hashtek.tekore.common.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Nexus implements HashLoggable
{

    private final BreakFFA main;
    private final GameManager gameManager;
    private Block block;


    /**
     * Creates a new Nexus.
     *
     * @param   main    BreakFFA instance
     */
    public Nexus(BreakFFA main, GameManager gameManager)
    {
        this.main = main;
        this.gameManager = gameManager;
    }


    /**
     * Destroys the Nexus.
     *
     * @param   player  Player who destroyed the Nexus.
     */
    public void destroy(Player player)
    {
        final PlayerData playerData = this.gameManager.getPlayerData(player);
        final fr.hashtek.tekore.common.player.PlayerData corePlayerData = playerData.getCorePlayerData();
        final Rank playerRank = corePlayerData.getRank();
        final Title title = new Title();

        /* Clears all drops. */
        new HashUtils.World(this.main.getWorld()).clearItems();

        for (Player p : this.main.getServer().getOnlinePlayers()) {
            final PlayerData pData = this.gameManager.getPlayerData(p);

            if (pData.getState() != PlayerState.PLAYING)
                continue;

            /* Nexus breaking SFX / VFX */
            p.playSound(p.getLocation(), Sound.IRONGOLEM_DEATH, 100, 0);
            p.playSound(p.getLocation(), Sound.EXPLODE, 100, 0);
            p.playSound(p.getLocation(), "mob.guardian.curse", 100, 0);
            p.playSound(p.getLocation(), Sound.WITHER_DEATH, 100, 0);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 0), false);

            /* Nexus breaking messages */
            p.sendMessage(
                "" + ChatColor.DARK_RED + ChatColor.UNDERLINE + "⚕" + ChatColor.RESET + " " +
                playerRank.getColor() + playerRank.getShortName() + " " + corePlayerData.getUsername() + " " +
                ChatColor.RED + "a brisé le " + ChatColor.DARK_RED + ChatColor.BOLD + "Nexus" + ChatColor.RESET + ChatColor.RED + " !"
            );

            try {
                title.send(
                    p,
                    0,
                    20,
                    40,
                    ChatColor.RED + "⚕ " + ChatColor.DARK_RED + ChatColor.BOLD + "NEXUS" + ChatColor.RED + " ⚕",
                    ChatColor.RED + "Brisé par " + playerRank.getColor() + corePlayerData.getUsername()
                );
            } catch (Exception exception) {
                HashError.SRV_PACKET_SEND_FAIL
                    .log(this.main.getHashLogger(), this, exception)
                    .sendToPlayer(p);
            }

            /* Resetting player's nexus break streak */
            if (!pData.getPlayer().equals(player))
                pData.setNexusBreaksStreak(0);
        }

        /* Resetting game */
        this.gameManager.reset();

        /* Updating player's data */
        playerData.addNexusBreaks(1);
        playerData.addNexusBreaksStreak(1);
        playerData.addShards(5);
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
