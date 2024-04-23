package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.death.DeathReason;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import fr.hashtek.spigot.breakffa.shop.category.ShopCategoryArticles;
import fr.hashtek.spigot.breakffa.shop.category.categories.ShopCategorySupport;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerDamageByEntity implements Listener
{

    private final BreakFFA main;
    private final GameManager gameManager;


    /**
     * Creates a new instance of ListenerDamageByEntity
     *
     * @param   main    BreakFFA instance
     */
    public ListenerDamageByEntity(BreakFFA main)
    {
        this.main = main;
        this.gameManager = this.main.getGameManager();
    }


    private void executeShopWeaponsAbilities(ItemStack weapon, Player victim, Player damager)
    {
        /* Baseball bat handling */
        final ShopCategoryArticles baseballBat = ShopCategorySupport.Articles.BASEBALL_BAT;

        if (baseballBat.equals(weapon)) {
            for (Player player : this.main.getServer().getOnlinePlayers()) {
                player.playSound(damager.getLocation(), Sound.ANVIL_LAND, 1, 1);
                player.playSound(damager.getLocation(), Sound.ZOMBIE_METAL, 100, 1);
            }
            return;
        }
    }

    /**
     * Called when an entity takes damage from another entity.
     * In this case, we only process players (PVP).
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;

        final Player victim = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();
        final PlayerData victimData = this.gameManager.getPlayerData(victim);
        final PlayerData damagerData = this.gameManager.getPlayerData(damager);
        final ItemStack damagerWeapon = damager.getItemInHand();

        /* If one of the two players is not playing, cancel the event. */
        if (victimData.getState() != PlayerState.PLAYING || damagerData.getState() != PlayerState.PLAYING)
            event.setCancelled(true);

        /* For kill author detection. */
        victimData.setLastDamager(damager);
        victimData.setLastDamagerWeapon(damagerWeapon);

        /* Shop weapons abilities handling. */
        if (damagerWeapon == null || damagerWeapon.getType() != Material.AIR)
            this.executeShopWeaponsAbilities(damagerWeapon, victim, damager);

        /* For kill. */
        if (victim.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            victimData.getPlayerManager().kill(DeathReason.MELEE);
        }
    }

}
