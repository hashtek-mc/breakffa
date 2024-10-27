package fr.hashtek.spigot.breakffa.death;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKillSfx;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import fr.hashtek.spigot.hashgui.listener.HashGuiHitListener;
import fr.hashtek.tekore.common.account.Account;
import fr.hashtek.tekore.spigot.Tekore;
import fr.hashtek.tekore.common.rank.Rank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Death
    implements HashLoggable
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final Tekore CORE = MAIN.getCore();


    /* Victim */
    private final Player victim;
    private final PlayerManager victimManager;
    private final PlayerData victimData;
    private final Account victimAccount;
    private final Rank victimRank;

    /* Killer */
    private final Player killer;
    private final PlayerManager killerManager;
    private final PlayerData killerData;
    private final Account killerAccount;
    private final Rank killerRank;

    private final ItemStack weapon;

    private final DeathReason reason;


    /**
     * Creates a new instance of Death (without killer).
     *
     * @param   victim  Death victim
     * @param   reason  Death reason
     */
    public Death(Player victim, DeathReason reason)
    {
        this(victim, null, null, reason);
    }

    /**
     * Creates a new instance of Death (without weapon).
     *
     * @param   victim  Death victim
     * @param   killer  Death author
     * @param   reason  Death reason
     */
    public Death(Player victim, Player killer, DeathReason reason)
    {
        this(victim, killer, null, reason);
    }

    /**
     * Creates a new instance of Death.
     *
     * @param   victim  Death victim
     * @param   killer  Death author
     * @param   weapon  Weapon used
     * @param   reason  Death reason
     */
    public Death(Player victim, Player killer, ItemStack weapon, DeathReason reason)
    {
        final GameManager gameManager = MAIN.getGameManager();

        this.victim = victim;
        this.victimManager = gameManager.getPlayerManager(this.victim);
        this.victimData = victimManager.getData();
        this.victimAccount = CORE.getPlayerManagersManager().getPlayerManager(this.victim).getAccount();
        this.victimRank = this.victimAccount.getRank();

        this.killer = killer;

        if (this.killer != null) {
            this.killerManager = gameManager.getPlayerManager(this.killer);
            this.killerData = killerManager.getData();
            this.killerAccount = CORE.getPlayerManagersManager().getPlayerManager(this.killer).getAccount();
            this.killerRank = this.killerAccount.getRank();
        } else {
            this.killerManager = null;
            this.killerData = null;
            this.killerAccount = null;
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
            .append(this.victimRank.getUsernameColor())
            .append(this.victimRank.getShortName())
            .append(" ")
            .append(this.victimAccount.getUsername())
            .append(" ").append(ChatColor.WHITE).append(this.reason.getReason());

        if (this.killer != null) {
            message
                .append(" ")
                .append(this.reason.getBy())
                .append(" ")
                .append(this.killerRank.getUsernameColor())
                .append(this.killerRank.getShortName())
                .append(" ")
                .append(this.killerAccount.getUsername());

            if (this.weapon != null && this.weapon.getType() != Material.AIR) {
                message
                    .append(ChatColor.WHITE + " avec ")
                    .append(((TextComponent) Objects.requireNonNull(this.weapon.getItemMeta().displayName())).content());
            }
        }

        message.append(ChatColor.WHITE + ".");

        MAIN.getServer().broadcast(Component.text(message.toString()));
    }

    /**
     * Sends an action bar message and plays a little sound to the killer.
     */
    private void confirmDeath()
    {
        if (this.killer == null) {
            return;
        }

        this.killer.sendActionBar(Component.text(
            "" + ChatColor.DARK_RED + ChatColor.UNDERLINE + "⚔" +
            this.victimRank.getUsernameColor() + " " + this.victimRank.getShortName() + " " + this.victimAccount.getUsername() + " " +
            ChatColor.AQUA + ChatColor.BOLD + "+" + killerData.getKillRewardShards()
        ));

        this.killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 2);
    }

    /**
     * Resets victim's wanted data.
     */
    private void processVictim()
    {
        this.victimData.setKillStreak(0);
        this.victimManager.setLastDamager(null);
        this.victimManager.setLastDamagerWeapon(null);
        this.victimData.setKillRewardShards(1);

//        try {
//            MAIN.getBoardManager().getPlayerSidebar(this.victim).refreshSidebar();
//        } catch (Exception exception) {
//            // TODO: Write proper error handling
//        }
    }

    /**
     * Updates killer's data.
     */
    private void processKiller()
    {
        if (this.killerData == null) {
            return;
        }

        HashGuiHitListener.processHit(
            this.killer,
            this.victim,
            this.weapon,
            true,
            MAIN.getGuiManager().getHitManager()
        );

        final Cosmetic<CosmeticTypeKillSfx> currentKillerKSFXCosmetic = this.killerManager.getCosmeticManager().getCurrentKillSfx();

        if (currentKillerKSFXCosmetic != null) {
            // FIXME: Rework on this
//            this.killer.playSound(this.killer.getLocation(), currentKillerKSFXCosmetic.getCosmetic().getSfx(), 1, 1);
        }

        this.killerData.addTotalKills(1);
        this.killerData.addKillStreak(1);
        this.killerData.addShards(this.killerData.getKillRewardShards());

//        try {
//            MAIN.getBoardManager().getPlayerSidebar(this.killer).refreshSidebar();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            // TODO: Write proper error handling
//        }

        this.killer.setHealth(killer.getHealthScale());

        if (this.killerManager.getData().getState() == PlayerState.PLAYING) {
            MAIN.getShopManager().giveShop(this.killer);
        }
    }

    /**
     * Executes the death.
     */
    public void execute()
    {
        this.victimManager.backToLobby();

        this.processVictim();
        this.processKiller();
        this.confirmDeath();
        this.broadcast();
    }

}
