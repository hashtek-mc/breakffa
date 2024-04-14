package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.KitLobby;
import fr.hashtek.spigot.breakffa.kit.KitStarter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class PlayerManager
{

    private final BreakFFA main;
    private final PlayerData playerData;
    private final Player player;
    private final GameManager gameManager;


    public PlayerManager(BreakFFA main, PlayerData playerData)
    {
        this.main = main;
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
        this.player.setGameMode(GameMode.SURVIVAL);

        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(0);
        KitStarter.giveItems(this.player);
    }

    public void backToLobby()
    {
        final PlayerInventory playerInventory = this.player.getInventory();

        this.playerData.setState(PlayerState.AT_LOBBY);
        this.player.setGameMode(GameMode.ADVENTURE);

        this.player.teleport(this.gameManager.getLobbySpawnLocation());

        this.player.setMaxHealth(20.0);
        this.player.setHealth(this.player.getMaxHealth());

        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(4);
        KitLobby.giveItems(this.player);
    }

    public void kill()
    {
        final Player killer = this.playerData.getLastDamager();

        this.backToLobby();

        this.main.getServer().broadcastMessage(this.player.getDisplayName() + " died lol");

        if (killer != null) {
            final PlayerData killerData = this.gameManager.getPlayerData(killer);

            killer.setHealth(killer.getMaxHealth());
            killer.playSound(killer.getLocation(), Sound.NOTE_PLING, 1, 4);
            killerData.addTotalKills(1);
            this.main.getServer().broadcastMessage("Last damager: " + this.playerData.getLastDamager().getDisplayName());
        }
        else
            this.main.getServer().broadcastMessage("in the void ig");

        this.playerData.setKillStreak(0);
        this.playerData.setLastDamager(null);

        for (PlayerData pData : this.gameManager.getPlayersData().values()) {
            if (pData.getPlayer().equals(this.player) || pData.getLastDamager() == null)
                continue;

            if (pData.getLastDamager().equals(this.player))
                pData.setLastDamager(null);
        }
    }

}
