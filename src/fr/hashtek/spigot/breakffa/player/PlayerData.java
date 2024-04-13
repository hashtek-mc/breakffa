package fr.hashtek.spigot.breakffa.player;

import org.bukkit.entity.Player;

public class PlayerData
{

    private final Player player;

    private final PlayerState state;

    private final int totalKills;
    private final int killStreak;
    private final int coreBreaks;
    private final int coreBreaksStreak;


    public PlayerData(Player player)
    {
        this.player = player;

        this.state = PlayerState.AT_LOBBY;

        this.totalKills = 0;
        this.killStreak = 0;
        this.coreBreaks = 0;
        this.coreBreaksStreak = 0;
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

}
