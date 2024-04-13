package fr.hashtek.spigot.breakffa.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ListenerFoodLose implements Listener
{

    @EventHandler
    public void onFoodLose(FoodLevelChangeEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        event.setCancelled(true);
    }

}
