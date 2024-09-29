package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.death.Death;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.kits.KitLobby;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.spectator.SpectatorMode;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Random;

public class PlayerManager implements HashLoggable
{

    private final BreakFFA main;
    private final PlayerData playerData;
    private final Player player;
    private final fr.hashtek.tekore.common.player.PlayerData corePlayerData;

    private final CosmeticManager cosmeticManager;
    private SpectatorMode spectatorMode;


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
        this.cosmeticManager = new CosmeticManager();
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
        final GameManager gameManager = this.main.getGameManager();

        final PlayerInventory inventory = this.player.getInventory();
        final KitStarter starterKit = new KitStarter(this.main);

        /* Finds a random spawn and teleports the player to it. */
        final int spawnIndex = new Random().nextInt(gameManager.getSpawnLocations().size());
        final Location spawn = gameManager.getSpawnLocations().get(spawnIndex);
        this.player.teleport(spawn);

        /* Updates player's data. */
        this.playerData.setState(PlayerState.PLAYING);
        this.player.setGameMode(GameMode.SURVIVAL);

        /* Sets player's inventory. */
        inventory.clear();
        this.clearArmor(inventory);
        inventory.setHeldItemSlot(0);
        starterKit.giveItems(this.playerData);
    }

    /**
     * Teleports the player back to the lobby.
     */
    public void backToLobby()
    {
        final GameManager gameManager = this.main.getGameManager();

        final PlayerInventory playerInventory = this.player.getInventory();
        final KitLobby lobbyKit = new KitLobby(this.main);

        /* Updates player's data. */
        this.playerData.setState(PlayerState.AT_LOBBY);
        this.player.setGameMode(GameMode.ADVENTURE);

        /* Teleports player back to the lobby. */
        this.player.teleport(gameManager.getLobbySpawnLocation());

        /* Resets player's health. */
        this.player.setHealthScale(20.0);
        this.player.setHealth(this.player.getHealthScale());

        /* Resets player's potion effects. */
        for (PotionEffect potionEffect : this.player.getActivePotionEffects()) {
            this.player.removePotionEffect(potionEffect.getType());
        }

        /* Sets player's inventory. */
        playerInventory.clear();
        this.clearArmor(playerInventory);
        playerInventory.setHeldItemSlot(4);
        lobbyKit.giveItems(this.player);
    }

    /**
     * Kills the player.
     */
    public void kill(DeathReason reason)
    {
        new Death(
            this.main,
            this.player,
            this.playerData.getLastDamager(),
            this.playerData.getLastDamagerWeapon(),
            reason
        ).execute();
    }

    /**
     * @return  Player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * @return  Player's data
     */
    public PlayerData getPlayerData()
    {
        return this.playerData;
    }

    /**
     * @return  Cosmetic manager
     */
    public CosmeticManager getCosmeticManager()
    {
        return this.cosmeticManager;
    }

    /**
     * @return  Spectator mode
     */
    public SpectatorMode getSpectatorMode()
    {
        return this.spectatorMode;
    }

    /**
     * @param   spectatorMode   Spectator mode
     */
    public void setSpectatorMode(SpectatorMode spectatorMode)
    {
        this.spectatorMode = spectatorMode;
    }

}
