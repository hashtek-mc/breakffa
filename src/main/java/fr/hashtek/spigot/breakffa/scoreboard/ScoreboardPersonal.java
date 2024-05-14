package fr.hashtek.spigot.breakffa.scoreboard;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashboard.HashBoard;
import fr.hashtek.spigot.hashboard.HashSideBar;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class ScoreboardPersonal
{

    private final BreakFFA main;
    private final Player player;

    private final HashBoard board;
    private final HashSideBar sidebar;
    private final NumberFormat numberFormatter;

    private static final String sidebarTitle =
        ChatColor.DARK_RED + "⚕" + ChatColor.BOLD + ChatColor.RED + " BreakFFA " + ChatColor.DARK_RED + "⚕";


    public ScoreboardPersonal(BreakFFA main, Player player, HashBoard board)
    {
        this.main = main;
        this.player = player;
        this.board = board;
        this.sidebar = new HashSideBar(this.board);
        this.numberFormatter = NumberFormat.getInstance();
    }


    private List<String> createSidebarBody(PlayerData playerData)
    {
        return Arrays.asList(
            " ",
            ChatColor.WHITE + "Éliminations: " + this.numberFormatter.format(playerData.getTotalKills()),
            ChatColor.WHITE + "Série d'éliminations: " + this.numberFormatter.format(playerData.getKillStreak()),
            "  ",
            ChatColor.WHITE + "Nexus brisés: " + this.numberFormatter.format(playerData.getNexusBreaks()),
            ChatColor.WHITE + "Série de Nexus brisés: " + this.numberFormatter.format(playerData.getNexusBreaksStreak()),
            ChatColor.WHITE + "Shards: " + this.numberFormatter.format(playerData.getShards()),
            "   ",
            ChatColor.RED + "mc.hashtek.fr"
        );
    }

    private void drawSidebar(HashSideBar sidebar, List<String> body)
    {
        for (int k = 0; k < body.size(); k++)
            sidebar.setLine(body.size() - k, body.get(k));
    }

    public void refreshSidebar()
    {
        final PlayerData playerData = this.main.getGameManager().getPlayerData(this.player);
        final List<String> body = this.createSidebarBody(playerData);

        this.sidebar.flush();
        this.sidebar.setTitle(sidebarTitle);
        this.drawSidebar(this.sidebar, body);
    }

}
