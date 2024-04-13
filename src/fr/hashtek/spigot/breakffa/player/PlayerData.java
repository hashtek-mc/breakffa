package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.entity.Player;

public class PlayerData
{

    private final Player player;
    private PlayerState state;
    private final GameManager gameManager;
    private final PlayerManager playerManager;

    private int totalKills;
    private int killStreak;
    private int coreBreaks;
    private int coreBreaksStreak;


    public PlayerData(Player player, GameManager gameManager)
    {
        this.player = player;
        this.state = PlayerState.AT_LOBBY;
        this.gameManager = gameManager;
        this.playerManager = new PlayerManager(this);

        this.totalKills = 0;
        this.killStreak = 0;
        this.coreBreaks = 0;
        this.coreBreaksStreak = 0;
    }


    public Player getPlayer()
    {
        return this.player;
    }

    public PlayerState getState()
    {
        return this.state;
    }

    public GameManager getGameManager()
    {
        return this.gameManager;
    }

    public PlayerManager getPlayerManager()
    {
        return this.playerManager;
    }

    public int getTotalKills()
    {
        return this.totalKills;
    }

    public int getKillStreak()
    {
        return this.killStreak;
    }

    public int getCoreBreaks()
    {
        return this.coreBreaks;
    }

    public int getCoreBreaksStreak()
    {
        return this.coreBreaksStreak;
    }

    public void setState(PlayerState state)
    {
        this.state = state;
    }

}
