package fr.hashtek.spigot.breakffa.player;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Player's BreakFFA data.
 * <p>
 * Unless you want beef with Redis, NEVER move
 * this file and NEVER rename its parent package.
 * </p>
 */
public class PlayerData
{

    private PlayerState state;

    private final PlayerSettings settings;

    private int shards;
    private int killRewardShards;

    private int totalKills;
    private int killStreak;
    private int nexusBreaks;
    private int nexusBreaksStreak;


    /**
     * Creates a new instance of PlayerData.
     */
    public PlayerData()
    {
        this.state = PlayerState.AT_LOBBY;

        this.settings = new PlayerSettings();

        this.shards = 0;
        this.killRewardShards = 1;

        this.totalKills = 0;
        this.killStreak = 0;
        this.nexusBreaks = 0;
        this.nexusBreaksStreak = 0;
    }


    /**
     * @return  Player's state
     */
    @JsonIgnore
    public PlayerState getState()
    {
        return this.state;
    }

    /**
     * @return  Player's settings
     */
    public PlayerSettings getSettings()
    {
        return this.settings;
    }

    /**
     * @return  Player's shards
     */
    public int getShards()
    {
        return this.shards;
    }

    /**
     * @return  Shards obtained when killing someone
     */
    @JsonIgnore
    public int getKillRewardShards()
    {
        return this.killRewardShards;
    }

    /**
     * @return  Player's total kills
     */
    public int getTotalKills()
    {
        return this.totalKills;
    }

    /**
     * @return  Player's killstreak
     */
    public int getKillStreak()
    {
        return this.killStreak;
    }

    /**
     * @return  Player's nexus break amount
     */
    public int getNexusBreaks()
    {
        return this.nexusBreaks;
    }

    /**
     * @return  Player's nexus break streak
     */

    public int getNexusBreaksStreak()
    {
        return this.nexusBreaksStreak;
    }

    /**
     * @param   state   Player's state
     */
    public void setState(PlayerState state)
    {
        this.state = state;
    }

    /**
     * @param    shards     Player's shards
     */
    public void setShards(int shards)
    {
        this.shards = shards;
    }

    /**
     * @param   killRewardShards    Shards obtained when killing someone
     */
    public void setKillRewardShards(int killRewardShards)
    {
        this.killRewardShards = killRewardShards;
    }

    /**
     * @param    totalKills     Player's total kills
     */
    public void setTotalKills(int totalKills)
    {
        this.totalKills = totalKills;
    }

    /**
     * @param   killStreak  Player's killstreak
     */
    public void setKillStreak(int killStreak)
    {
        this.killStreak = killStreak;
    }

    /**
     * @param   nexusBreaks     Player's nexus breaks
     */
    public void setNexusBreaks(int nexusBreaks)
    {
        this.nexusBreaks = nexusBreaks;
    }

    /**
     * @param   nexusBreaksStreak   Player's nexus breaks streak
     */
    public void setNexusBreaksStreak(int nexusBreaksStreak)
    {
        this.nexusBreaksStreak = nexusBreaksStreak;
    }

    /**
     * @param   amount  Amount to add.
     */
    public void addShards(int amount)
    {
        this.shards += amount;
    }

    /**
     * @param   amount  Amount to add.
     */
    public void addTotalKills(int amount)
    {
        this.totalKills += amount;
    }

    /**
     * @param   amount  Amount to add.
     */
    public void addKillStreak(int amount)
    {
        this.killStreak += amount;
    }

    /**
     * @param   amount  Amount to add.
     */
    public void addNexusBreaks(int amount)
    {
        this.nexusBreaks += amount;
    }

    /**
     * @param   amount  Amount to add.
     */
    public void addNexusBreaksStreak(int amount)
    {
        this.nexusBreaksStreak += amount;
    }

}
