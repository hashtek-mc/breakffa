package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
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
    private final GameManager gameManager;

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
        final PlayerInventory inventory = this.player.getInventory();
        final KitStarter starterKit = new KitStarter(this.main);

        final int spawnIndex = new Random().nextInt(this.gameManager.getSpawnLocations().size());
        final Location spawn = this.gameManager.getSpawnLocations().get(spawnIndex);

        this.player.teleport(spawn);
        this.playerData.setState(PlayerState.PLAYING);
        this.player.setGameMode(GameMode.SURVIVAL);

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
        final PlayerInventory playerInventory = this.player.getInventory();
        final KitLobby lobbyKit = new KitLobby(this.main);

        this.playerData.setState(PlayerState.AT_LOBBY);
        this.player.setGameMode(GameMode.ADVENTURE);

        this.player.teleport(this.gameManager.getLobbySpawnLocation());

        this.player.setMaxHealth(20.0);
        this.player.setHealth(this.player.getMaxHealth());

        for (PotionEffect potionEffect : this.player.getActivePotionEffects())
            this.player.removePotionEffect(potionEffect.getType());

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

    public Player getPlayer()
    {
        return this.player;
    }

    public PlayerData getPlayerData()
    {
        return this.playerData;
    }

    public SpectatorMode getSpectatorMode()
    {
        return this.spectatorMode;
    }

    public void setSpectatorMode(SpectatorMode spectatorMode)
    {
        this.spectatorMode = spectatorMode;
    }

}
