package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.spigot.breakffa.BreakFFA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerQuit implements Listener, HashLoggable
{

    private final BreakFFA main;


    public ListenerQuit(BreakFFA main)
    {
        this.main = main;
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        event.setQuitMessage(null);
    }

}
