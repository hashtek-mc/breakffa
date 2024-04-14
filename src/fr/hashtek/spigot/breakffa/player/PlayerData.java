package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import org.bukkit.entity.Player;

public class PlayerData
{

    private final BreakFFA main;
    private final Player player;
    private PlayerState state;
    private final GameManager gameManager;
    private final PlayerManager playerManager;

    private int totalKills;
    private int killStreak;
    private int nexusBreaks;
    private int nexusBreaksStreak;

    private Player lastDamager;


    public PlayerData(BreakFFA main, Player player, GameManager gameManager)
    {
        this.main = main;
        this.player = player;
        this.state = PlayerState.AT_LOBBY;
        this.gameManager = gameManager;
        this.playerManager = new PlayerManager(this.main, this);

        this.totalKills = 0;
        this.killStreak = 0;
        this.nexusBreaks = 0;
        this.nexusBreaksStreak = 0;
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

    public int getNexusBreaks()
    {
        return this.nexusBreaks;
    }

    public int getNexusBreaksStreak()
    {
        return this.nexusBreaksStreak;
    }

    public Player getLastDamager()
    {
        return this.lastDamager;
    }

    public void setState(PlayerState state)
    {
        this.state = state;
    }

    public void setLastDamager(Player damager)
    {
        this.lastDamager = damager;
    }

    public void setTotalKills(int totalKills)
    {
        this.totalKills = totalKills;
    }

    public void setKillStreak(int killStreak)
    {
        this.killStreak = killStreak;
    }

    public void setNexusBreaks(int nexusBreaks)
    {
        this.nexusBreaks = nexusBreaks;
    }

    public void setNexusBreaksStreak(int nexusBreaksStreak)
    {
        this.nexusBreaksStreak = nexusBreaksStreak;
    }

    public void addTotalKills(int amount)
    {
        this.totalKills += amount;
    }

    public void addKillStreak(int amount)
    {
        this.killStreak += amount;
    }

    public void addNexusBreaks(int amount)
    {
        this.nexusBreaks += amount;
    }

    public void addNexusBreaksStreak(int amount)
    {
        this.nexusBreaksStreak += amount;
    }

}
