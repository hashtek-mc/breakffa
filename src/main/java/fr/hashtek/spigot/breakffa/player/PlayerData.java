package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.BreakFFA;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    private final PlayerSettings settings; // TODO: Move in PlayerManager

    private int shards;
    private int killRewardShards;

    private int totalKills;
    private int killStreak;
    private int nexusBreaks;
    private int nexusBreaksStreak;

    private Player lastDamagedPlayer;
    private ItemStack lastDamagedWith;

    private Player lastDamager;
    private ItemStack lastDamagerWeapon;


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
        this.corePlayerData = this.main.getCore().getPlayerManager(this.player).getData();
        this.state = PlayerState.AT_LOBBY;

        this.settings = new PlayerSettings();

        this.shards = 1000; // FIXME: TODO: DEBUG, TO REMOVE!!!!
        this.killRewardShards = 1;

        this.totalKills = 0;
        this.killStreak = 0;
        this.nexusBreaks = 0;
        this.nexusBreaksStreak = 0;

        this.lastDamagedPlayer = null;
        this.lastDamagedWith = null;

        this.lastDamager = null;
        this.lastDamagerWeapon = null;
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
    public fr.hashtek.tekore.common.player.PlayerData getCoreData()
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
     * @return  Player's shards
     */
    public int getShards()
    {
        return this.shards;
    }

    /**
     * @return  Shards obtained when killing someone
     */
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
     * @return  Player's last damaged player
     */
    public Player getLastDamagedPlayer()
    {
        return this.lastDamagedPlayer;
    }

    /**
     * @return  Player's last damaged player weapon
     */
    public ItemStack getLastDamagedWith()
    {
        return this.lastDamagedWith;
    }

    /**
     * @return  Player's last damager
     */
    public Player getLastDamager()
    {
        return this.lastDamager;
    }

    /**
     * @return  Player's last damager weapon
     */
    public ItemStack getLastDamagerWeapon()
    {
        return this.lastDamagerWeapon;
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
     * @param   weapon  Player's last damager's weapon
     */
    public void setLastDamagerWeapon(ItemStack weapon)
    {
        this.lastDamagerWeapon = weapon;
    }

    /**
     * @param   damagedPlayer   Player's last damager
     */
    public void setLastDamagedPlayer(Player damagedPlayer)
    {
        this.lastDamagedPlayer = damagedPlayer;
    }

    /**
     * @param   weapon  Player's last damager's weapon
     */
    public void setLastDamagedWith(ItemStack weapon)
    {
        this.lastDamagedWith = weapon;
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
     * @param   amount  Amount to multiply with.
     */
    public void multiplyKillRewardShards(int amount)
    {
        this.killRewardShards *= amount;
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
