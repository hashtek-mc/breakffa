package fr.hashtek.spigot.breakffa;

import fr.hashktek.spigot.hashboard.HashBoard;
import fr.hashktek.spigot.hashboard.HashTeam;
import fr.hashtek.hashconfig.HashConfig;
import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.command.CommandGuiDump;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.listener.*;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.breakffa.tablist.Tablist;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.tekore.bukkit.Tekore;
import fr.hashtek.tekore.common.Rank;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.io.IOException;

public class BreakFFA extends JavaPlugin implements HashLoggable
{

    private static BreakFFA instance;
    private Tekore core;
    private HashLogger logger;

    private PluginManager pluginManager;
    private HashGuiManager guiManager;

    private HashConfig hashConfig;

    private GameManager gameManager;
    private ShopManager shopManager;

    private HashBoard board;
    private Tablist tablist;

    private HashMap<String, HashTeam> rankTeams;

    private World world;


    /**
     * Called on server start.
     * Core error handling uses System.err.println because
     * HashLogger isn't loaded yet.
     */
    @Override
    public void onEnable()
    {
        instance = this;

        try {
            this.core = Tekore.getInstance();
        } catch (NoClassDefFoundError exception) {
            System.err.println("Tekore failed to load, stopping server.");
            this.getServer().shutdown();
            return;
        }

        this.setupHashLogger();
        this.setupConfig();

        this.logger.info(this, "Starting BreakFFA...");

        this.setupManagers();
        this.setupWorld();
        this.setupBoard();
        this.registerListeners();
        this.registerCommands();

        this.logger.info(this, "BreakFFA loaded.");
    }

    /**
     * Called on server stop.
     */
    @Override
    public void onDisable()
    {
        this.logger.info(this, "Disabling BreakFFA...");

        this.gameManager.reset();

        this.logger.info(this, "BreakFFA disabled.");
    }

    /**
     * Creates a new instance of HashConfig, to read configuration files.
     * Also creates a new instance of LobbyConfiguration.
     */
    private void setupConfig()
    {
        final String configFilename = "config.yml";

        try {
            this.hashConfig = new HashConfig(
                this.getClass(),
                configFilename,
                this.getDataFolder().getPath() + "/" + configFilename,
                false
            );
        } catch (IOException exception) {
            this.logger.fatal(this, "Failed to read config file. Stopping server.", exception);
            this.getServer().shutdown();
        }
    }

    /**
     * Creates an instance of HashLogger.
     * Based on Tekore's HashLogger's settings and history.
     */
    private void setupHashLogger()
    {
        final HashLogger coreLogger = this.core.getHashLogger();

        this.logger = new HashLogger(this, coreLogger.getSettings().getLogLevel(), coreLogger.getHistory());
    }

    /**
     * Setups all managers.
     */
    private void setupManagers()
    {
        this.logger.info(this, "Setting up managers...");

        this.pluginManager = this.getServer().getPluginManager();

        this.guiManager = new HashGuiManager(this, this.pluginManager);
        this.guiManager.setup();

        this.setupGameManager();
        this.shopManager = new ShopManager(this);

        this.logger.info(this, "Managers set up!");
    }

    /**
     * Setups game manager.
     */
    private void setupGameManager()
    {
        this.logger.info(this, "Setting up Game manager...");

        this.gameManager = new GameManager(this);

        try {
            this.gameManager.setup(this.hashConfig);
        } catch (Exception exception) {
            HashError.UNKNOWN
                .log(this.logger, this, exception);

            this.getServer().shutdown();
        }

        this.logger.info(this, "Game manager set up!");
    }

    private void setupWorld()
    {
        this.world = this.getServer().getWorld("breakffa");

        this.world.setGameRuleValue("doDaylightCycle", "false");
        this.world.setGameRuleValue("doFireTick", "false");
        this.world.setGameRuleValue("doMobLoot", "false");
        this.world.setGameRuleValue("doMobSpawning", "false");
        this.world.setGameRuleValue("doTileDrops", "false");
        this.world.setGameRuleValue("keepInventory", "true");
        this.world.setDifficulty(Difficulty.NORMAL);
        this.world.setAutoSave(false);
    }

    /**
     * Setups main board.
     * FIXME: Change HashError field. (CFG_EXCEPTION ?)
     */
    private void setupBoard()
    {
        this.board = new HashBoard();
        this.tablist = new Tablist(this);
        this.rankTeams = new HashMap<String, HashTeam>();

        try {
            this.tablist.setup(this.hashConfig.getYaml());
        } catch (NoSuchFieldException exception) {
            HashError.UNKNOWN
                .log(this.logger, this, exception);
        }

        int i = 0;
        for (Rank rank : this.core.getRanks()) {
            HashTeam team = new HashTeam(i, rank.getShortName() + " ", "", 10, this.board); // TODO: Set team size to 0 when HashBoard is updated.
            rankTeams.put(rank.getUuid(), team);
            i++;
        }
    }

    /**
     * Registers all event listeners.
     */
    private void registerListeners()
    {
        this.logger.info(this, "Registering listeners...");

        this.pluginManager.registerEvents(new ListenerJoin(this), this);
        this.pluginManager.registerEvents(new ListenerQuit(this), this);
        this.pluginManager.registerEvents(new ListenerDamage(this), this);
        this.pluginManager.registerEvents(new ListenerFoodLose(), this);
        this.pluginManager.registerEvents(new ListenerMove(this), this);
        this.pluginManager.registerEvents(new ListenerBreak(this), this);
        this.pluginManager.registerEvents(new ListenerPlace(this), this);
        this.pluginManager.registerEvents(new ListenerDrop(this), this);
        this.pluginManager.registerEvents(new ListenerDamageByEntity(this), this);
        this.pluginManager.registerEvents(new ListenerDeath(), this);
        this.pluginManager.registerEvents(new ListenerRespawn(this), this);
        this.pluginManager.registerEvents(new ListenerExplosion(this), this);
        this.pluginManager.registerEvents(new ListenerInteract(this), this);
        this.pluginManager.registerEvents(new ListenerChat(this), this);
        this.pluginManager.registerEvents(new ListenerWeatherChange(), this);

        this.logger.info(this, "Listeners loaded!");
    }

    /**
     * Registers all command listeners.
     */
    private void registerCommands()
    {
        this.logger.info(this, "Registering commands...");

        getCommand("guidump").setExecutor(new CommandGuiDump());
        /* ... */

        this.logger.info(this, "Commands registered!");
    }

    /**
     * @return	BreakFFA instance
     */
    public static BreakFFA getInstance()
    {
        return instance;
    }

    /**
     * @return	Tekore instance
     */
    public Tekore getCore()
    {
        return this.core;
    }

    /**
     * @return	Logger
     */
    public HashLogger getHashLogger()
    {
        return this.logger;
    }

    /**
     * @return	Configuration manager
     */
    public HashConfig getHashConfig()
    {
        return this.hashConfig;
    }

    /**
     * @return	GUI manager
     */
    public HashGuiManager getGuiManager()
    {
        return this.guiManager;
    }

    /**
     * @return  Game manager
     */
    public GameManager getGameManager()
    {
        return this.gameManager;
    }

    /**
     * @return  Shop manager
     */
    public ShopManager getShopManager()
    {
        return this.shopManager;
    }

    /**
     * @return  Main board
     */
    public HashBoard getBoard()
    {
        return this.board;
    }

    /**
     * @return  Tablist
     */
    public Tablist getTablist()
    {
        return this.tablist;
    }

    /**
     * @return  Rank teams
     */
    public HashMap<String, HashTeam> getRankTeams()
    {
        return this.rankTeams;
    }

    /**
     * @return  World
     */
    public World getWorld()
    {
        return this.world;
    }

}
