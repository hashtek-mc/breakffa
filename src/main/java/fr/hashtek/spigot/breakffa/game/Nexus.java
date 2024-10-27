package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashutils.HashUtils;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import fr.hashtek.spigot.breakffa.shop.category.categories.ShopCategoryDefensive;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.tekore.common.rank.Rank;
import fr.hashtek.tekore.common.account.Account;
import fr.hashtek.tekore.spigot.Tekore;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Nexus
    implements HashLoggable
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();
    private static final Tekore CORE = MAIN.getCore();

    private Block block;


    /**
     * Executes Shop weapons abilities.
     * TODO: This CAN be optimised.
     *
     * @param   player      Player
     * @param   playerData  Player's data
     */
    private void executeShopWeaponsAbilities(Player player, PlayerData playerData)
    {
        final PlayerInventory inventory = player.getInventory();

        /* Lucky Boots handling */
        for (ItemStack i : inventory.getArmorContents()) {
            if (ShopCategoryDefensive.Articles.LUCKY_BOOTS.equals(i)) {
                playerData.addShards(3);
            }
        }
    }

    /**
     * Destroys the Nexus.
     *
     * @param   player  Player who destroyed the Nexus.
     */
    public void destroy(Player player)
    {
        final PlayerData playerData = MAIN.getGameManager().getPlayerManager(player).getData();
        final Account corePlayerData = CORE.getPlayerManagersManager().getPlayerManager(player).getAccount();
        final Rank playerRank = corePlayerData.getRank();

        /* Clears all drops. */
        new HashUtils.World(MAIN.getWorld()).clearItems();

        for (Player p : MAIN.getServer().getOnlinePlayers()) {
            final PlayerData pData = MAIN.getGameManager().getPlayerManager(p).getData();

            if (pData.getState() != PlayerState.PLAYING) {
                continue;
            }

            /* Nexus breaking SFX / VFX */
            p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 100, 0);
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 0);
            p.playSound(p.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 100, 0);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 100, 0);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1, 0), false);

            /* Nexus breaking messages */
            p.sendMessage(
                "" + ChatColor.DARK_RED + ChatColor.UNDERLINE + "⚕" + ChatColor.RESET + " " +
                playerRank.getUsernameColor() + playerRank.getShortName() + " " + corePlayerData.getUsername() + " " +
                ChatColor.RED + "a brisé le " + ChatColor.DARK_RED + ChatColor.BOLD + "Nexus" + ChatColor.RESET + ChatColor.RED + " !"
            );

            p.sendTitle(
                ChatColor.RED + "⚕ " + ChatColor.DARK_RED + ChatColor.BOLD + "NEXUS" + ChatColor.RED + " ⚕",
                ChatColor.RED + "Brisé par " + playerRank.getUsernameColor() + corePlayerData.getUsername(),
                0,
                20,
                40
            );

            /* Resetting player's nexus break streak */
            if (!p.equals(player)) {
                pData.setNexusBreaksStreak(0);
            }
        }

        this.executeShopWeaponsAbilities(player, playerData);

        MAIN.getGameManager().resetMap();

        /* Updates player's data */
        playerData.addNexusBreaks(1);
        playerData.addNexusBreaksStreak(1);
        playerData.addShards(5);

//        try {
//            this.main.getBoardManager().getPlayerSidebar(player).refreshSidebar();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            // TODO: Write proper error handling
//        }
    }


    /**
     * @return  Nexus' block
     */
    public Block getBlock()
    {
        return this.block;
    }

    /**
     * @param   block   Nexus' block
     */
    public void setBlock(Block block)
    {
        this.block = block;
    }

}
