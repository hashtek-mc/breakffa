package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.lobby.KitLobby;
import fr.hashtek.spigot.breakffa.kit.starter.KitStarter;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.tekore.common.Rank;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class PlayerManager
{

    private final BreakFFA main;
    private final PlayerData playerData;
    private final Player player;
    private final fr.hashtek.tekore.common.player.PlayerData corePlayerData;
    private final GameManager gameManager;


    /**
     * Creates a new instance of PlayerManager.
     *
     * @param   main        BreakFFA instance
     * @param   playerData  Player's data
     */
    public PlayerManager(BreakFFA main, PlayerData playerData)
    {
        this.main = main;
        this.playerData = playerData;
        this.corePlayerData = this.playerData.getCorePlayerData();
        this.player = this.playerData.getPlayer();
        this.gameManager = this.main.getGameManager();
    }


    /**
     * Clear's player's armor.
     *
     * @param   inventory   Player's inventory
     */
    private void clearArmor(PlayerInventory inventory)
    {
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);
    }

    /**
     * Teleport the player in the game.
     */
    public void play()
    {
        final PlayerInventory playerInventory = this.player.getInventory();
        final KitStarter starterKit = new KitStarter(this.main);

        final int spawnIndex = new Random().nextInt(this.gameManager.getSpawnLocations().size());
        final Location spawn = this.gameManager.getSpawnLocations().get(spawnIndex);

        this.player.teleport(spawn);
        this.playerData.setState(PlayerState.PLAYING);
        this.player.setGameMode(GameMode.SURVIVAL);

        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(0);
        starterKit.giveItems(this.player);
    }

    /**
     * Teleports the player back to the lobby.
     */
    public void backToLobby()
    {
        final PlayerInventory playerInventory = this.player.getInventory();
        final KitLobby lobbyKit = new KitLobby(this.main);

        this.playerData.setState(PlayerState.AT_LOBBY);
        this.player.setGameMode(GameMode.ADVENTURE);

        this.player.teleport(this.gameManager.getLobbySpawnLocation());

        this.player.setMaxHealth(20.0);
        this.player.setHealth(this.player.getMaxHealth());

        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(4);
        lobbyKit.giveItems(this.player);
    }

    /**
     * Kills the player.
     */
    public void kill()
    {
        final Player killer = this.playerData.getLastDamager();
        final Rank playerRank = this.corePlayerData.getRank();

        this.backToLobby();

        String killMessage = playerRank.getColor() + playerRank.getFullName() + " " + this.corePlayerData.getUsername() + " " + ChatColor.RESET;

        if (killer != null) {
            final PlayerData killerData = this.gameManager.getPlayerData(killer);
            final fr.hashtek.tekore.common.player.PlayerData coreKillerData = killerData.getCorePlayerData();
            final Rank killerRank = coreKillerData.getRank();

            killer.setHealth(killer.getMaxHealth());
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 100, 2);
            killerData.addTotalKills(1);

            killMessage += "s'est fait tuer par " + killerRank.getColor() + killerRank.getFullName() + " " + coreKillerData.getUsername();
        } else
            killMessage += "est mort.";

        this.main.getServer().broadcastMessage(killMessage);

        this.playerData.setKillStreak(0);
        this.playerData.setLastDamager(null);

        for (PlayerData pData : this.gameManager.getPlayersData().values()) {
            if (pData.getPlayer().equals(this.player) || pData.getLastDamager() == null)
                continue;

            if (pData.getLastDamager().equals(this.player))
                pData.setLastDamager(null);
        }
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public PlayerData getPlayerData()
    {
        return this.playerData;
    }

}
