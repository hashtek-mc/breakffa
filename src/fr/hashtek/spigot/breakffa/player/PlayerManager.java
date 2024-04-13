package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.KitLobby;
import fr.hashtek.spigot.breakffa.kit.KitStarter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

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


    private void clearArmor(PlayerInventory inventory)
    {
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);
    }

    public void play()
    {
        final PlayerInventory playerInventory = this.player.getInventory();

        final int spawnIndex = new Random().nextInt(this.gameManager.getSpawnLocations().size());
        final Location spawn = this.gameManager.getSpawnLocations().get(spawnIndex);

        this.player.teleport(spawn);
        this.playerData.setState(PlayerState.PLAYING);

        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(0);
        KitStarter.giveItems(this.player);
    }

    public void backToLobby()
    {
        final PlayerInventory playerInventory = this.player.getInventory();

        this.playerData.setState(PlayerState.AT_LOBBY);

        this.player.teleport(this.gameManager.getLobbySpawnLocation());

        this.player.setMaxHealth(20.0);
        this.player.setHealth(20.0);

        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(4);
        KitLobby.giveItems(this.player);
    }

}
