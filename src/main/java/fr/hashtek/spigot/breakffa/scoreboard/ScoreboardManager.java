package fr.hashtek.spigot.breakffa.scoreboard;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.hashboard.HashBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private final BreakFFA main;
    private final HashBoard board;
    private final Map<Player, ScoreboardPersonal> sidebars;

    /**
     * Creates a new instance of ScoreboardManager.
     *
     * @param main BreakFFA instance
     */
    public ScoreboardManager(BreakFFA main) {
        this.main = main;
        this.board = new HashBoard();
        this.sidebars = new HashMap<>();
    }

    /**
     * @return HashBoard
     */
    public HashBoard getBoard() {
        return this.board;
    }

    /**
     * @return Sidebars
     */
    public Map<Player, ScoreboardPersonal> getSidebars() {
        return this.sidebars;
    }

    /**
     * Creates a sidebar for a player.
     *
     * @param player Player
     * @return ScoreboardPersonal instance
     * @throws Exception if an error occurs while creating the sidebar
     */
    public ScoreboardPersonal addPlayerSidebar(Player player) throws Exception {
        ScoreboardPersonal sidebar = new ScoreboardPersonal(this.main, player);
        this.sidebars.put(player, sidebar);
        return sidebar;
    }

    /**
     * Removes the sidebar of a player.
     *
     * @param player Player
     */
    public void removePlayerSidebar(Player player) {
        this.sidebars.remove(player);
    }

    /**
     * @param player Player
     * @return Player's sidebar
     */
    public ScoreboardPersonal getPlayerSidebar(Player player) {
        return this.sidebars.get(player);
    }

    /**
     * Updates the scoreboard for a player with shop information.
     *
     * @param player Player to update
     * @param shards Current shards of the player
     */
    public void updateScoreboardWithShopInfo(Player player, int shards) {
        ScoreboardPersonal sidebar = getPlayerSidebar(player);
        if (sidebar != null) {
            sidebar.refreshSidebarWithShopInfo(shards);
        }
    }

    /**
     * Refreshes the scoreboard for all players.
     */
    public void refreshAllScoreboards() {
        for (Player player : sidebars.keySet()) {
            ScoreboardPersonal sidebar = getPlayerSidebar(player);
            if (sidebar != null) {
                sidebar.refreshSidebar();
            }
        }
    }

    /**
     * Refreshes the sidebar for a specific player.
     *
     * @param player Player to refresh
     */
    public void refreshSidebarForPlayer(Player player) {
        ScoreboardPersonal sidebar = getPlayerSidebar(player);
        if (sidebar != null) {
            sidebar.refreshSidebar();
        }
    }
}
