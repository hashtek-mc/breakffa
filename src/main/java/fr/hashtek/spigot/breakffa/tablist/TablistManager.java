package fr.hashtek.spigot.breakffa.tablist;

import fr.hashtek.spigot.hashboard.HashTabList;
import fr.hashtek.spigot.hashboard.exceptions.StrangeException;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.BreakFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.Arrays;
import java.util.List;

public class TablistManager implements HashLoggable
{

    private final BreakFFA main;
    private final HashLogger logger;

    private final HashTabList tablist;

    private String header;
    private String footer;


    /**
     * Creates a new instance of Tablist.
     *
     * @param   main    BreakFFA instance
     */
    public TablistManager(BreakFFA main)
    {
        this.main = main;
        this.logger = main.getHashLogger();

        this.tablist = new HashTabList();
    }


    /**
     * Setups the Tablist according to configuration file content.
     *
     * @param   yaml                    Configuration file content
     * @throws  NoSuchFieldException    Field not found (invalid content)
     */
    public void setup(YamlFile yaml)
        throws NoSuchFieldException
    {
        this.logger.info(this, "Loading Tablist...");

        final String prefix = "tablist";

        final List<String> requiredKeys = Arrays.asList("", ".header", ".footer");

        for (String key : requiredKeys)
            if (!yaml.contains(prefix + key))
                throw new NoSuchFieldException("\"" + prefix + key + "\" field not found.");

        this.header = ChatColor.translateAlternateColorCodes('&', yaml.getString(prefix + ".header"));
        this.footer = ChatColor.translateAlternateColorCodes('&', yaml.getString(prefix + ".footer"));

        this.logger.info(this, String.format(
            "Successfully loaded tablist.\n" +
            "(Header: `%s`, Footer: `%s`)",
            this.header, this.footer
        ));
    }

    /**
     * Refreshes Tablist content
     *
     * @param   minusOnePlayer  When disconnecting, put this to true.
     */
    public void refresh(boolean minusOnePlayer)
    {
        int onlinePlayers = this.main.getServer().getOnlinePlayers().size();

        if (minusOnePlayer)
            onlinePlayers--;

        this.tablist.setHeader(this.header);

        this.tablist.setFooter(
            this.footer
                .replace("{version}", "v1.0")
                .replace("{players}", String.valueOf(onlinePlayers))
                .replace("{plural}", onlinePlayers == 1 ? "" : "s")
        );
    }

    /**
     * Refreshes Tablist content.
     */
    public void refresh()
    {
        this.refresh(false);
    }


    /**
     * Updates the tablist for a Player.
     *
     * @param   player              Player
     * @throws  StrangeException    Tablist exception
     */
    public void update(Player player)
        throws StrangeException
    {
        this.tablist.update(player);
    }

    /**
     * Updates the tablist for every players online.
     *
     * @throws  StrangeException    Tablist exception
     */
    public void update()
        throws StrangeException
    {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            this.update(player);
    }


    /**
     * @return  Tablist
     */
    public HashTabList getTablist()
    {
        return this.tablist;
    }

}
