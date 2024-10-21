package fr.hashtek.spigot.breakffa.scoreboard;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashboard.HashBoard;
import fr.hashtek.spigot.hashboard.sidebars.HashSidebar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class ScoreboardPersonal
{

    private final BreakFFA main;
    private final Player player;

    private final HashSidebar sidebar;

    private static final String SIDEBAR_TITLE =
        ChatColor.DARK_RED + "⚕" +
        ChatColor.BOLD + ChatColor.RED + " BreakFFA " +
        ChatColor.DARK_RED + "⚕";


    /**
     * Creates a new instance of ScoreboardPersonal.
     *
     * @param   main    BreakFFA instance
     * @param   player  Player
     */
    public ScoreboardPersonal(BreakFFA main, Player player)
        throws Exception
    {
        this.main = main;
        this.player = player;
        this.sidebar = new HashSidebar(player.getUniqueId().toString());
        this.sidebar.addReceiver(player);
    }


    /**
     * Creates the sidebar body using player's data.
     * TODO: Uncomment the lines when HashBoard is fixed.
     *
     * @param   playerData  Player's data
     * @return  Created sidebar body
     */
    private List<String> createSidebarBody(PlayerData playerData)
    {
        final NumberFormat numberFormatter = NumberFormat.getInstance();

        return Arrays.asList(
            " ",
            ChatColor.WHITE + "Éliminations: " + numberFormatter.format(playerData.getTotalKills()),
            ChatColor.WHITE + "Série d'éliminations: " + numberFormatter.format(playerData.getKillStreak()),
            "  ",
            ChatColor.WHITE + "Nexus brisés: " + numberFormatter.format(playerData.getNexusBreaks()),
            ChatColor.WHITE + "Série de Nexus brisés: " + numberFormatter.format(playerData.getNexusBreaksStreak()),
            ChatColor.WHITE + "Shards: " + numberFormatter.format(playerData.getShards()),
            "   ",
            ChatColor.RED + "mc.hashtek.fr"
        );
    }

    /**
     * Puts the body in the sidebar.
     *
     * @param   sidebar     Sidebar
     * @param   body        Sidebar's body to put
     */
    private void drawSidebar(HashSidebar sidebar, List<String> body)
    {
        for (int k = 0; k < body.size(); k++) {
            sidebar.setLine(body.size() - k, body.get(k));
        }
    }

    /**
     * Refreshes the sidebar for the linked player.
     */
    public void refreshSidebar()
        throws Exception
    {
        final PlayerData playerData = this.main.getGameManager().getPlayerManager(this.player).getData();
        final List<String> body = this.createSidebarBody(playerData);

        this.sidebar.clearLines();
        this.sidebar.setTitle(SIDEBAR_TITLE);
        this.drawSidebar(this.sidebar, body);
        this.sidebar.update();
    }

}
