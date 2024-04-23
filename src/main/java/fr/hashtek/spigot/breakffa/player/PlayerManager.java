package fr.hashtek.spigot.breakffa.player;

import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashutils.ActionBar;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.death.Death;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.kits.KitLobby;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.shop.category.categories.ShopCategoryOffensive;
import fr.hashtek.tekore.common.Rank;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class PlayerManager implements HashLoggable
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
//        final Player killer = this.playerData.getLastDamager();
//        final Rank playerRank = this.corePlayerData.getRank();
//
//        this.backToLobby();
//
//        String killMessage = playerRank.getColor() + playerRank.getFullName() + " " + this.corePlayerData.getUsername() + " " + ChatColor.RESET;
//
//        if (killer != null) {
//            final PlayerData killerData = this.gameManager.getPlayerData(killer);
//            final fr.hashtek.tekore.common.player.PlayerData coreKillerData = killerData.getCorePlayerData();
//            final Rank killerRank = coreKillerData.getRank();
//
//            updateKillerData(killerData, playerRank, this.playerData.getLastDamagerWeapon());
//            killMessage += "s'est fait tuer par " + killerRank.getColor() + killerRank.getFullName() + " " + coreKillerData.getUsername();
//        } else
//            killMessage += "est mort.";
//
//        this.main.getServer().broadcastMessage(killMessage);
//
//        this.playerData.setKillStreak(0);
//        this.playerData.setLastDamager(null);
//        this.playerData.setLastDamagerWeapon(null);
//        this.playerData.setKillRewardShards(1);
//
//        for (PlayerData pData : this.gameManager.getPlayersData().values()) {
//            if (pData.getPlayer().equals(this.player) || pData.getLastDamager() == null)
//                continue;
//
//            if (pData.getLastDamager().equals(this.player))
//                pData.setLastDamager(null);
//        }
    }

//    private void updateKillerData(PlayerData killerData, Rank playerRank, ItemStack weapon)
//    {
//        final Player killer = killerData.getPlayer();
//
//        killer.setHealth(killer.getMaxHealth());
//        killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 100, 2);
//
//        killerData.addTotalKills(1);
//
//        if (weapon != null && weapon.getType() != Material.AIR)
//            if (ShopCategoryOffensive.Articles.PERFECT_SWORD.equals(weapon))
//                killerData.setKillRewardShards(2);
//
//        try {
//            new ActionBar(
//                "" + ChatColor.DARK_RED + ChatColor.UNDERLINE + ChatColor.BOLD + "⚔" +
//                playerRank.getColor() + " " + playerRank.getShortName() + " " + this.player.getName() + " " +
//                ChatColor.AQUA + ChatColor.BOLD + "+" + killerData.getKillRewardShards()
//            ).send(killer);
//        } catch (Exception exception) {
//            HashError.SRV_PACKET_SEND_FAIL
//                .log(this.main.getHashLogger(), this, exception);
//        }
//
//        killerData.addShards(killerData.getKillRewardShards());
//        this.main.getShopManager().giveShop(killerData);
//    }

    public Player getPlayer()
    {
        return this.player;
    }

    public PlayerData getPlayerData()
    {
        return this.playerData;
    }

}
