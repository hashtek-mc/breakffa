package fr.hashtek.spigot.breakffa.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ListenerWeatherChange implements Listener
{

    /**
     * Called when weather changes.
     * In this case, we cancel.
     */
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event)
    {
        event.setCancelled(event.toWeatherState());
    }

}
