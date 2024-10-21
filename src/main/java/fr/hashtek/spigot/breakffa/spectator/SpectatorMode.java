package fr.hashtek.spigot.breakffa.spectator;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.kits.KitSpectator;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import fr.hashtek.tekore.common.account.Account;
import fr.hashtek.tekore.common.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class SpectatorMode
{

    private final BreakFFA main;
    private final GameManager gameManager;

    private final Player player;
    private final PlayerManager playerManager;


    /**
     * Creates a new instance of SpectatorMode.
     *
     * @param   main    BreakFFA instance
     * @param   player  Player
     */
    public SpectatorMode(BreakFFA main, Player player)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();

        this.player = player;
        this.playerManager = this.main.getGameManager().getPlayerManager(this.player);
    }


    /**
     * Teleports player to the closest playing player.
     */
    public void teleportToClosestPlayer()
    {
        final Location playerLocation = this.player.getLocation();

        Player closestPlayer = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        /* Find the closest playing player */
        for (Player p : BreakFFA.getInstance().getServer().getOnlinePlayers()) {
            if (p == null || p.equals(this.player)) {
                continue;
            }

            final PlayerData pData = this.gameManager.getPlayerManager(p).getData();

            if (pData.getState() != PlayerState.PLAYING) {
                continue;
            }

            final double distanceSquared = playerLocation.distanceSquared(p.getLocation());

            if (distanceSquared < closestDistanceSquared) {
                closestPlayer = p;
                closestDistanceSquared = distanceSquared;
            }
        }

        if (closestPlayer == null) {
            this.player.sendMessage(ChatColor.RED + "La partie est vide :(");
            return;
        }

        final Account closestPlayerCoreData = this.main.getCore().getPlayerManagersManager()
            .getPlayerManager(closestPlayer).getAccount();
        final Rank closestPlayerRank = closestPlayerCoreData.getRank();

        /* Teleport player to the closest player */
        this.player.teleport(closestPlayer.getLocation());
        this.player.playSound(closestPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);

        /* Notifying player */
        this.player.sendMessage(ChatColor.AQUA + "Téléportation à " + closestPlayerRank.getUsernameColor() + closestPlayerRank.getFullName() + " " + closestPlayerCoreData.getUsername());
    }

    /**
     * Go in the spectator mode.
     */
    public void go()
    {
        final PlayerInventory inventory = this.player.getInventory();
        final KitSpectator kitSpectator = new KitSpectator();

        /* Updates player's data */
        this.playerManager.getData().setState(PlayerState.SPECTATING);
        this.player.setGameMode(GameMode.SPECTATOR);

        /* Teleport player to the Spectator mode spawn */
        this.player.teleport(this.gameManager.getSpectatorModeSpawnLocation());

        /* Sets player's inventory */
        inventory.clear();
        kitSpectator.giveItems(this.player);
    }

    /**
     * Get out of the spectator mode.
     */
    public void exit()
    {
        this.playerManager.backToLobby();
    }

}
