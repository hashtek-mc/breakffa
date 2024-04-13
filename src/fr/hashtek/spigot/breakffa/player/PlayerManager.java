package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.KitLobby;
import fr.hashtek.spigot.breakffa.kit.KitStarter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class PlayerManager
{

    private final PlayerData playerData;
    private final Player player;
    private final GameManager gameManager;


    public PlayerManager(PlayerData playerData)
    {
        this.playerData = playerData;
        this.player = this.playerData.getPlayer();
        this.gameManager = this.playerData.getGameManager();
    }


    public void play()
    {
        final int spawnIndex = new Random().nextInt(this.gameManager.getSpawnLocations().size());
        final Location spawn = this.gameManager.getSpawnLocations().get(spawnIndex);

        this.player.teleport(spawn);
        this.playerData.setState(PlayerState.PLAYING);

        this.player.getInventory().clear();
        KitStarter.giveItems(this.player);
        this.player.getInventory().setHeldItemSlot(0);
        // ...
    }

    public void backToLobby()
    {
        this.playerData.setState(PlayerState.AT_LOBBY);

        this.player.teleport(this.gameManager.getLobbySpawnLocation());

        this.player.setMaxHealth(20.0);
        this.player.setHealth(20.0);

        this.player.getInventory().clear();
        KitLobby.giveItems(this.player);
        this.player.getInventory().setHeldItemSlot(4);
    }

}
