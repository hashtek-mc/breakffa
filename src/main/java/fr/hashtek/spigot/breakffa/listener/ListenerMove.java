package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.game.GameSettings;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerMove
    implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;
    private final GameSettings gameSettings;


    /**
     * Creates a new instance of ListenerMove.
     *
     * @param   main    BreakFFA instance
     */
    public ListenerMove(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
        this.gameSettings = this.gameManager.getGameSettings();
    }


    /**
     * TODO: Maybe use {@link Location#distanceSquared(Location)}.
     *
     * @param   world       World
     * @param   location    Location
     * @return  True if location is outside the world's border.
     */
    private boolean isOutsideBorder(World world, Location location)
    {
        final WorldBorder border = world.getWorldBorder();
        final double radius = border.getSize() / 2;
        final Location center = border.getCenter();
        double x = location.getBlockX() - center.getBlockX(), z = location.getBlockZ() - center.getBlockZ();

        return ((x > radius || (-x) > radius) || (z > radius || (-z) > radius));
    }

    /**
     * Handles Spectator mode bounds (world border and minimum height).
     *
     * @param   event   Move event
     * @param   player  Player
     */
    private void handleSpectatorModeBounds(PlayerMoveEvent event, Player player)
    {
        if (event.getTo().getBlockY() < this.gameSettings.getMinHeight()) {
            player.teleport(this.gameManager.getSpectatorModeSpawnLocation());
        }

        if (this.isOutsideBorder(this.main.getWorld(), event.getTo())) {
            event.setCancelled(true);
        }

        if (event.getTo().getBlockY() >= this.gameManager.getLobbySpawnLocation().getBlockY()) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles void death (for players in game).
     *
     * @param   event           Move event
     * @param   playerManager   Player's manager
     */
    private void handleVoidDeath(PlayerMoveEvent event, PlayerManager playerManager)
    {
        if (event.getTo().getBlockY() < this.gameSettings.getMinHeight()) {
            playerManager.kill(DeathReason.VOID);
        }
    }

    /**
     * Called when a player moves in the world.
     * <p>
     * If the player falls below the minimum height, kills the players.
     * </p>
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();
        final PlayerManager playerManager = this.gameManager.getPlayerManager(player);
        final PlayerState playerState = playerManager.getData().getState();

        switch (playerState) {
            case PLAYING:
                this.handleVoidDeath(event, playerManager);
                break;
            case SPECTATING:
                this.handleSpectatorModeBounds(event, player);
                break;
            default:
        }

    }

}
