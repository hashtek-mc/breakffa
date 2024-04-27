package fr.hashtek.spigot.breakffa.death;

import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashutils.ActionBar;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.listener.HashGuiHitListener;
import fr.hashtek.tekore.bukkit.Tekore;
import fr.hashtek.tekore.common.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Death implements HashLoggable
{

    private final BreakFFA main;
    private final GameManager gameManager;

    private final Player victim;
    private final PlayerData victimData;
    private final fr.hashtek.tekore.common.player.PlayerData victimCoreData;
    private final Rank victimRank;

    private final Player killer;
    private final PlayerData killerData;
    private final fr.hashtek.tekore.common.player.PlayerData killerCoreData;
    private final Rank killerRank;

    private final ItemStack weapon;

    private final DeathReason reason;


    /**
     * Creates a new instance of Death (without killer).
     *
     * @param   main    BreakFFA instance
     * @param   victim  Death victim
     * @param   reason  Death reason
     */
    public Death(BreakFFA main, Player victim, DeathReason reason)
    {
        this(main, victim, null, null, reason);
    }

    /**
     * Creates a new instance of Death (without weapon).
     *
     * @param   main    BreakFFA instance
     * @param   victim  Death victim
     * @param   killer  Death author
     * @param   reason  Death reason
     */
    public Death(BreakFFA main, Player victim, Player killer, DeathReason reason)
    {
        this(main, victim, killer, null, reason);
    }

    /**
     * Creates a new instance of Death.
     *
     * @param   main    BreakFFA instance
     * @param   victim  Death victim
     * @param   killer  Death author
     * @param   weapon  Weapon used
     * @param   reason  Death reason
     */
    public Death(BreakFFA main, Player victim, Player killer, ItemStack weapon, DeathReason reason)
    {
        final Tekore core;

        this.main = main;
        core = this.main.getCore();
        this.gameManager = this.main.getGameManager();

        this.victim = victim;
        this.victimData = this.gameManager.getPlayerData(this.victim);
        this.victimCoreData = core.getPlayerData(this.victim);
        this.victimRank = this.victimCoreData.getRank();

        this.killer = killer;

        if (this.killer != null) {
            this.killerData = this.gameManager.getPlayerData(this.killer);
            this.killerCoreData = core.getPlayerData(this.killer);
            this.killerRank = this.killerCoreData.getRank();
        } else {
            this.killerData = null;
            this.killerCoreData = null;
            this.killerRank = null;
        }

        this.weapon = weapon;

        this.reason = reason;
    }


    /**
     * Broadcasts the death.
     */
    private void broadcast()
    {
        final StringBuilder message = new StringBuilder();

        message
            .append(this.reason.getSymbol())
            .append(" ")
            .append(this.victimRank.getColor())
            .append(this.victimRank.getShortName())
            .append(" ")
            .append(this.victimCoreData.getUsername())
            .append(" ").append(ChatColor.WHITE).append(this.reason.getReason());

        if (this.killer != null) {
            message
                .append(" ")
                .append(this.reason.getBy())
                .append(" ")
                .append(this.killerRank.getColor())
                .append(this.killerRank.getShortName())
                .append(" ")
                .append(this.killerCoreData.getUsername());

            if (this.weapon != null && this.weapon.getType() != Material.AIR) {
                message
                    .append(ChatColor.WHITE + " avec ")
                    .append(this.weapon.getItemMeta().getDisplayName());
            }
        }

        message.append(ChatColor.WHITE + ".");

        this.main.getServer().broadcastMessage(message.toString());
    }

    /**
     * Sends an action bar message and plays a little sound to the killer.
     */
    private void confirmDeath()
    {
        if (this.killer == null)
            return;

        try {
            new ActionBar(
                "" + ChatColor.DARK_RED + ChatColor.UNDERLINE + ChatColor.BOLD + "⚔" +
                this.victimRank.getColor() + " " + this.victimRank.getShortName() + " " + this.victimCoreData.getUsername() + " " +
                ChatColor.AQUA + ChatColor.BOLD + "+" + killerData.getKillRewardShards()
            ).send(this.killer);
        } catch (Exception exception) {
            HashError.SRV_PACKET_SEND_FAIL
                .log(this.main.getHashLogger(), this, exception);
        }

        this.killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 100, 2);
    }

    /**
     * Resets victim's wanted data.
     */
    private void processVictim()
    {
        this.victimData.setKillStreak(0);
        this.victimData.setLastDamager(null);
        this.victimData.setLastDamagerWeapon(null);
        this.victimData.setKillRewardShards(1);
    }

    /**
     * Updates killer's data.
     */
    private void processKiller()
    {
        if (this.killerData == null)
            return;

        HashGuiHitListener.processHit(
            this.killer,
            this.victim,
            this.weapon,
            true,
            this.main.getGuiManager().getHitManager()
        );

        this.killerData.addTotalKills(1);
        this.killerData.addKillStreak(1);
        this.killerData.addShards(this.killerData.getKillRewardShards());

        this.killer.setHealth(killer.getMaxHealth());
        this.main.getShopManager().giveShop(this.killerData);
    }

    /**
     * Executes the death.
     */
    public void execute()
    {
        this.victimData.getPlayerManager().backToLobby();

        this.processVictim();
        this.processKiller();
        this.confirmDeath();
        this.broadcast();
    }

}
