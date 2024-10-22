package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeHat;
import fr.hashtek.spigot.breakffa.death.Death;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.kits.KitLobby;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.player.io.PlayerDataProvider;
import fr.hashtek.spigot.breakffa.player.io.PlayerDataPublisher;
import fr.hashtek.spigot.breakffa.spectator.SpectatorMode;
import fr.hashtek.tekore.common.data.redis.RedisAccess;
import fr.hashtek.tekore.common.exceptions.EntryNotFoundException;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Random;

public class PlayerManager implements HashLoggable
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();

    private final Player player;
    private PlayerData playerData;

    private final CosmeticManager cosmeticManager;
    private SpectatorMode spectatorMode;

    private Player lastDamagedPlayer;
    private ItemStack lastDamagedWith;
    private Player lastDamager;
    private ItemStack lastDamagerWeapon;


    /**
     * Creates a new instance of PlayerManager.
     *
     * @param   player  Player's data
     */
    public PlayerManager(Player player)
    {
        this.player = player;

        this.cosmeticManager = new CosmeticManager();
        // spectator mode?

        this.lastDamagedPlayer = null;
        this.lastDamagedWith = null;
        this.lastDamager = null;
        this.lastDamagerWeapon = null;

        this.setPlayerData(player.getUniqueId().toString(), MAIN.getCore().getRedisAccess());
    }


    private void setPlayerData(String playerUuid, RedisAccess redisAccess)
    {
        try {
            /* Try to fetch the player's data from the Redis database */
            this.playerData = new PlayerDataProvider(redisAccess)
                .get(playerUuid);
        }
        catch (EntryNotFoundException exception) {
            /* If player data does not exist, create it. */
            this.playerData = new PlayerData();

            this.pushData(redisAccess);
        }
    }

    /**
     * Pushes player's account data to the Redis database.
     *
     * @param   redisAccess     Redis access
     */
    public void pushData(RedisAccess redisAccess)
    {
        new PlayerDataPublisher(redisAccess)
            .push(this.player.getUniqueId().toString(), this.playerData);
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
     * Teleports the player in the game.
     */
    public void play()
    {
        final GameManager gameManager = MAIN.getGameManager();

        final PlayerInventory inventory = this.player.getInventory();
        final KitStarter starterKit = new KitStarter();

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
        starterKit.giveItems(this.player);

        /* Sets player's custom helmet. */
        final Cosmetic<CosmeticTypeHat> currentPlayerCustomHelmetCosmetic =
            this.cosmeticManager.getCurrentHat();

        if (currentPlayerCustomHelmetCosmetic != null) {
            inventory.setHelmet(currentPlayerCustomHelmetCosmetic.getCosmetic().getHat().getItemStack());
        }
    }

    /**
     * Teleports the player back to the lobby.
     */
    public void backToLobby()
    {
        final GameManager gameManager = MAIN.getGameManager();

        final PlayerInventory playerInventory = this.player.getInventory();
        final KitLobby lobbyKit = new KitLobby();

        /* Updates player's data. */
        this.playerData.setState(PlayerState.AT_LOBBY);
        this.player.setGameMode(GameMode.ADVENTURE);

        /* Teleports player back to the lobby. */
        this.player.teleport(gameManager.getLobbySpawnLocation());

        /* Resets player's health. */
        this.player.setHealthScale(20.0);
        this.player.setHealth(this.player.getHealthScale());
        this.player.setFoodLevel(20);

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
            this.player,
            this.getLastDamager(),
            this.getLastDamagerWeapon(),
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
    public PlayerData getData()
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
     * @return  Player's last damaged player
     */
    public Player getLastDamagedPlayer()
    {
        return this.lastDamagedPlayer;
    }

    /**
     * @return  Player's last damaged player weapon
     */
    public ItemStack getLastDamagedWith()
    {
        return this.lastDamagedWith;
    }

    /**
     * @return  Player's last damager
     */
    public Player getLastDamager()
    {
        return this.lastDamager;
    }

    /**
     * @return  Player's last damager weapon
     */
    public ItemStack getLastDamagerWeapon()
    {
        return this.lastDamagerWeapon;
    }

    /**
     * @param   spectatorMode   Spectator mode
     */
    public void setSpectatorMode(SpectatorMode spectatorMode)
    {
        this.spectatorMode = spectatorMode;
    }

    /**
     * @param   damager     Player's last damager
     */
    public void setLastDamager(Player damager)
    {
        this.lastDamager = damager;
    }

    /**
     * @param   weapon  Player's last damager's weapon
     */
    public void setLastDamagerWeapon(ItemStack weapon)
    {
        this.lastDamagerWeapon = weapon;
    }

    /**
     * @param   damagedPlayer   Player's last damager
     */
    public void setLastDamagedPlayer(Player damagedPlayer)
    {
        this.lastDamagedPlayer = damagedPlayer;
    }

    /**
     * @param   weapon  Player's last damager's weapon
     */
    public void setLastDamagedWith(ItemStack weapon)
    {
        this.lastDamagedWith = weapon;
    }

}
