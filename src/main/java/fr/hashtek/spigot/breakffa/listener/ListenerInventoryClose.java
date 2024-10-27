package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ListenerInventoryClose
    implements Listener
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        final PlayerData playerData = MAIN.getGameManager().getPlayerManager(player).getData();

        if (playerData.getState() == PlayerState.EDITING_HOTBAR_SETTINGS) {
            // TODO: Either cancel event or save. For now we cancel the event (by re-opening player's inventory).
            player.openInventory(player.getInventory());
        }
    }

}
