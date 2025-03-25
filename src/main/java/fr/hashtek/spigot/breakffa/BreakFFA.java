package fr.hashtek.spigot.breakffa;

import fr.hashtek.spigot.breakffa.command.CommandCosmeticDump;
import fr.hashtek.spigot.breakffa.scoreboard.ScoreboardManager;
import fr.hashtek.spigot.hashboard.HashTeam;
import fr.hashtek.hashconfig.HashConfig;
import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.command.CommandGuiDump;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.listener.*;
import fr.hashtek.spigot.breakffa.shop.ShopManager;
import fr.hashtek.spigot.breakffa.tablist.TablistManager;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.tekore.spigot.Tekore;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.io.IOException;
import java.util.Objects;

public class BreakFFA extends JavaPlugin implements HashLoggable {

    private static BreakFFA instance;
    private Tekore core;
    private HashLogger logger;

    private PluginManager pluginManager;
    private HashGuiManager guiManager;

    private HashConfig hashConfig;

    private GameManager gameManager;
    private ShopManager shopManager;
    private ScoreboardManager scoreboardManager;
    private TablistManager tablistManager;

    private HashMap<String, HashTeam> rankTeams;

    private World world;

    @Override
    public void onEnable() {
        instance = this;

        try {
            this.core = Tekore.getInstance();
        } catch (NoClassDefFoundError exception) {
            System.err.println("Tekore failed to load, stopping server.");
            this.getServer().shutdown();
            return;
        }

        setupConfig();
        setupHashLogger();

        logger.info(this, "Starting BreakFFA...");

        setupManagers();
        setupWorld();
        setupBoard();
        registerListeners();
        registerCommands();

        logger.info(this, "BreakFFA loaded.");
    }

    @Override
    public void onDisable() {
        logger.info(this, "Disabling BreakFFA...");

        gameManager.resetMap();

        logger.info(this, "BreakFFA disabled.");
    }

    private void setupConfig() {
        final String configFilename = "config.yml";

        try {
            this.hashConfig = new HashConfig(
                this.getClass(),
                configFilename,
                this.getDataFolder().getPath() + "/" + configFilename,
                true
            );
        } catch (IOException exception) {
            logger.fatal(this, "Failed to read config file. Stopping server.", exception);
            this.getServer().shutdown();
        }
    }

    private void setupHashLogger() {
        try {
            this.logger = HashLogger.fromEnvConfig(this, this.hashConfig.getEnv());
        } catch (IllegalArgumentException | NullPointerException exception) {
            System.err.println("Can't initialize HashLogger. Stopping.");
            this.getServer().shutdown();
        }
    }

    private void setupManagers() {
        logger.info(this, "Setting up managers...");

        this.pluginManager = this.getServer().getPluginManager();

        this.guiManager = new HashGuiManager(this, this.pluginManager);
        this.guiManager.setup();

        setupGameManager();
        this.shopManager = new ShopManager();

        logger.info(this, "Managers set up!");
    }

    private void setupGameManager() {
        logger.info(this, "Setting up Game manager...");

        this.gameManager = new GameManager();

        try {
            this.gameManager.setup(this.hashConfig);
        } catch (Exception exception) {
            HashError.UNKNOWN.log(this.logger, this, exception);
            this.getServer().shutdown();
        }

        logger.info(this, "Game manager set up!");
    }

    private void setupWorld() {
        this.world = this.getServer().getWorld("breakffa");

        if (this.world == null) {
            return;
        }

        this.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.world.setGameRule(GameRule.DO_FIRE_TICK, false);
        this.world.setGameRule(GameRule.DO_MOB_LOOT, false);
        this.world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        this.world.setGameRule(GameRule.DO_TILE_DROPS, false);
        this.world.setGameRule(GameRule.KEEP_INVENTORY, true);
        this.world.setDifficulty(Difficulty.NORMAL);
        this.world.setAutoSave(false);
    }

    private void setupBoard() {
        // Setup scoreboard and tablist managers
    }

    private void registerListeners() {
        logger.info(this, "Registering listeners...");

        this.pluginManager.registerEvents(new ListenerJoin(), this);
        this.pluginManager.registerEvents(new ListenerQuit(), this);
        this.pluginManager.registerEvents(new ListenerDamage(), this);
        this.pluginManager.registerEvents(new ListenerFoodLose(), this);
        this.pluginManager.registerEvents(new ListenerMove(this), this);
        this.pluginManager.registerEvents(new ListenerBreak(), this);
        this.pluginManager.registerEvents(new ListenerPlace(), this);
        this.pluginManager.registerEvents(new ListenerDrop(), this);
        this.pluginManager.registerEvents(new ListenerDamageByEntity(), this);
        this.pluginManager.registerEvents(new ListenerDeath(), this);
        this.pluginManager.registerEvents(new ListenerRespawn(), this);
        this.pluginManager.registerEvents(new ListenerExplosion(), this);
        this.pluginManager.registerEvents(new ListenerInteract(), this);
        this.pluginManager.registerEvents(new ListenerChat(), this);
        this.pluginManager.registerEvents(new ListenerWeatherChange(), this);

        logger.info(this, "Listeners loaded!");
    }

    private void registerCommands() {
        logger.info(this, "Registering commands...");

        Objects.requireNonNull(getCommand("guidump")).setExecutor(new CommandGuiDump());
        Objects.requireNonNull(getCommand("cosmeticdump")).setExecutor(new CommandCosmeticDump());

        logger.info(this, "Commands registered!");
    }

    public static BreakFFA getInstance() {
        return instance;
    }

    public Tekore getCore() {
        return this.core;
    }

    public HashLogger getHashLogger() {
        return this.logger;
    }

    public HashConfig getHashConfig() {
        return this.hashConfig;
    }

    public HashGuiManager getGuiManager() {
        return this.guiManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }

    public ScoreboardManager getBoardManager() {
        return this.scoreboardManager;
    }

    public TablistManager getTablistManager() {
        return this.tablistManager;
    }

    public HashMap<String, HashTeam> getRankTeams() {
        return this.rankTeams;
    }

    public World getWorld() {
        return this.world;
    }
}
