package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.BreakFFA;
import org.bukkit.entity.Player;

/**
 * Player's BreakFFA data.
 * Not to confuse with Tekore's PlayerData.
 */
public class PlayerData
{

    private final BreakFFA main;

    private final Player player;
    private final fr.hashtek.tekore.common.player.PlayerData corePlayerData;
    private PlayerState state;

    private PlayerSettings settings;
    private final PlayerManager playerManager;

    private int shards;

    private int totalKills;
    private int killStreak;
    private int nexusBreaks;
    private int nexusBreaksStreak;

    private Player lastDamager;


    /**
     * Creates a new instance of PlayerData.
     *
     * @param   main        BreakFFA instance
     * @param   player      Player
     */
    public PlayerData(BreakFFA main, Player player)
    {
        this.main = main;

        this.player = player;
        this.corePlayerData = this.main.getCore().getPlayerData(this.player);
        this.state = PlayerState.AT_LOBBY;

        this.settings = new PlayerSettings();
        this.playerManager = new PlayerManager(this.main, this);

        this.shards = 0;

        this.totalKills = 0;
        this.killStreak = 0;
        this.nexusBreaks = 0;
        this.nexusBreaksStreak = 0;

        this.lastDamager = null;
    }


    /**
     * @return  BreakFFA instance
     */
    public BreakFFA getMain()
    {
        return this.main;
    }

    /**
     * @return  Player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * @return  Tekore's Player data
     */
    public fr.hashtek.tekore.common.player.PlayerData getCorePlayerData()
    {
        return this.corePlayerData;
    }

    /**
     * @return  Player's state
     */
    public PlayerState getState()
    {
        return this.state;
    }

    /**
     * @return  Player's settings
     */
    public PlayerSettings getPlayerSettings()
    {
        return this.settings;
    }

    /**
     * @return  Player's manager
     */
    public PlayerManager getPlayerManager()
    {
        return this.playerManager;
    }

    /**
     * @return  Player's shards
     */
    public int getShards()
    {
        return this.shards;
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
     * @return  Player's last damager
     */
    public Player getLastDamager()
    {
        return this.lastDamager;
    }

    /**
     * @param   state   Player's state
     */
    public void setState(PlayerState state)
    {
        this.state = state;
    }

    /**
     * @param   damager     Player's last damager
     */
    public void setLastDamager(Player damager)
    {
        this.lastDamager = damager;
    }

    /**
     * @param    shards     Player's shards
     */
    public void setShards(int shards)
    {
        this.shards = shards;
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
