package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.hashconfig.HashConfig;
import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.*;

public class GameManager implements HashLoggable
{

    private final BreakFFA main;
    private final HashLogger logger;

    private final Nexus nexus;
    private Location lobbySpawnLocation;
    private final List<Location> spawnLocations;

    private Date lastReset;
    private final Map<Player, PlayerData> playersData;
    private final List<Block> placedBlocks;

    private final GameSettings settings;


    /**
     * Creates a new instance of GameManager
     *
     * @param    main   BreakFFA instance
     */
    public GameManager(BreakFFA main)
    {
        this.main = main;
        this.logger = this.main.getHashLogger();

        this.nexus = new Nexus(this.main, this);
        this.spawnLocations = new ArrayList<Location>();

        this.lastReset = new Date();
        this.playersData = new HashMap<Player, PlayerData>();
        this.placedBlocks = new ArrayList<Block>();

        this.settings = new GameSettings(this.logger);
    }


    /**
     * Checks if keys are present in the configuration file.
     *
     * @param   yaml    Configuration file content
     * @param   keys    Keys to check
     * @return  Missing key (if one, else returns null)
     */
    private String checkRequiredKeys(YamlFile yaml, String... keys)
    {
        for (String key : keys)
            if (!yaml.contains(key))
                return key;

        return null;
    }

    /**
     * Setups Nexus from the configuration file.
     *
     * @param   yaml                    Configuration file content
     * @param   world                   Game world
     * @throws  NoSuchFieldException    Field not found (invalid content)
     */
    public void setupNexusLocation(YamlFile yaml, World world)
        throws NoSuchFieldException
    {
        this.logger.info(this, "Loading Nexus...");

        final String prefix = "nexusLocation";

        final String missingKey = checkRequiredKeys(yaml,
            prefix, prefix + ".x", prefix + ".y", prefix + ".z"
        );

        if (missingKey != null)
            throw new NoSuchFieldException("\"" + missingKey + "\" field not found.");

        final double x = yaml.getDouble(prefix + ".x");
        final double y = yaml.getDouble(prefix + ".y");
        final double z = yaml.getDouble(prefix + ".z");

        this.nexus.setBlock(world.getBlockAt(new Location(world, x, y, z)));

        this.logger.info(this, String.format(
            "Successfully loaded Nexus.\n" +
            "(X: %.2f, Y: %.2f, Z: %.2f, Type: %s)",
            x, y, z, this.nexus.getBlock().getType()
        ));
    }

    /**
     * Setups Lobby spawn location from the configuration file.
     *
     * @param   yaml                    Configuration file content
     * @param   world                   Game world
     * @throws  NoSuchFieldException    Field not found (invalid content)
     */
    public void setupLobbySpawnLocation(YamlFile yaml, World world)
        throws NoSuchFieldException
    {
        this.logger.info(this, "Loading Lobby spawn...");

        final String prefix = "lobbySpawn";
        
        final String missingKey = this.checkRequiredKeys(yaml,
            prefix, prefix + ".x", prefix + ".y", prefix + ".z"
        );

        if (missingKey != null)
            throw new NoSuchFieldException("\"" + missingKey + "\" field not found.");

        final double x = yaml.getDouble(prefix + ".x");
        final double y = yaml.getDouble(prefix + ".y");
        final double z = yaml.getDouble(prefix + ".z");
        final float pitch = (float) yaml.getDouble(prefix + ".pitch");
        final float yaw = (float) yaml.getDouble(prefix + ".yaw");

        this.lobbySpawnLocation = new Location(world, x, y, z, pitch, yaw);

        this.logger.info(this, String.format(
            "Successfully loaded Lobby spawn.\n" +
            "(X: %.2f, Y: %.2f, Z: %.2f, Pitch: %.2f, Yaw: %.2f)",
            x, y, z, pitch, yaw
        ));
    }

    /**
     * Setups spawns from the configuration file.
     *
     * @param   yaml                    Configuration file content
     * @param   world                   Game world
     * @throws  NoSuchFieldException    Field not found (invalid content)
     */
    public void setupSpawns(YamlFile yaml, World world)
        throws NoSuchFieldException
    {
        this.logger.info(this, "Loading spawns...");

        final List<String> requiredKeys = Arrays.asList("x", "y", "z");

        final List<Map<String, Object>> spawns = (List<Map<String, Object>>) yaml.getList("spawns");

        if (spawns == null || spawns.isEmpty()) {
            this.logger.fatal(this, "No spawns set.");
            throw new NoSuchFieldException("No spawns set.");
        }

        int index = -1;

        for (Map<String, Object> spawn : spawns) {
            index++;

            boolean hasAllFields = true;

            for (String key : requiredKeys) {
                if (!spawn.containsKey(key)) {
                    hasAllFields = false;
                    break;
                }
            }

            if (!hasAllFields) {
                this.logger.critical(this, "Spawn " + index + " has missing keys. Ignoring.");
                continue;
            }

            final double x = (double) spawn.get("x");
            final double y = (double) spawn.get("y");
            final double z = (double) spawn.get("z");
            final float pitch = (float) ((double) spawn.get("pitch"));
            final float yaw = (float) ((double) spawn.get("yaw"));

            this.spawnLocations.add(new Location(world, x, y, z, pitch, yaw));

            this.logger.info(this, String.format(
                "Successfully loaded spawn %d\n" +
                "(X: %.2f, Y: %.2f, Z: %.2f, Pitch: %.2f, Yaw: %.2f)",
                this.spawnLocations.size(),
                x, y, z, pitch, yaw
            ));
        }

        this.logger.info(this, "Successfully loaded " + this.spawnLocations.size() + " spawns!");
    }

    /**
     * Setups the game from the configuration file.
     *
     * @param   config      Configuration file
     * @throws  Exception   Error while setting up game (mainly caused by invalid content)
     */
    public void setup(HashConfig config)
        throws Exception
    {
        this.logger.info(this, "Setting up BreakFFA game...");

        final YamlFile yaml = config.getYaml();
        final World world = this.main.getServer().getWorld("breakffa");

        try {
            this.setupNexusLocation(yaml, world);
            this.setupLobbySpawnLocation(yaml, world);
            this.setupSpawns(yaml, world);
            this.settings.setup(yaml);
        } catch (NoSuchFieldException exception) {
            HashError.UNKNOWN
                .log(this.logger, this, exception);

            throw new Exception("Could not properly setup game.");
        }

        this.logger.info(this, "Successfully set up BreakFFA game!");
    }

    /**
     * Resets the map.
     * Used when Nexus is broken.
     */
    public void reset()
    {
        for (Player player : this.main.getServer().getOnlinePlayers()) {
            final PlayerData playerData = this.getPlayerData(player);
            final PlayerManager playerManager = playerData.getPlayerManager();

            playerManager.backToLobby();
        }

        for (Block block : this.placedBlocks)
            block.setType(Material.AIR);

        this.placedBlocks.clear();

        this.lastReset = new Date();
    }


    /**
     * @return  Game's Nexus
     */
    public Nexus getNexus()
    {
        return this.nexus;
    }

    /**
     * @return  Lobby spawn location
     */
    public Location getLobbySpawnLocation()
    {
        return this.lobbySpawnLocation;
    }

    /**
     * @return  Spawn locations
     */
    public List<Location> getSpawnLocations()
    {
        return this.spawnLocations;
    }

    /**
     * @return  Last game reset (last time a player has mined the Nexus)
     */
    public Date getLastReset()
    {
        return this.lastReset;
    }

    /**
     * @return  Players data
     */
    public Map<Player, PlayerData> getPlayersData()
    {
        return this.playersData;
    }

    /**
     * Adds a player's data to the main map.
     *
     * @param   player      Player
     * @param   playerData  Player's data
     */
    public void addPlayerData(Player player, PlayerData playerData)
    {
        this.playersData.put(player, playerData);
    }

    /**
     * Removes a player's data from the main map.
     *
     * @param   player  Player
     */
    public void removePlayerData(Player player)
    {
        this.playersData.remove(player);
    }

    /**
     * @param   player  Player
     * @return  Player's data
     */
    public PlayerData getPlayerData(Player player)
    {
        return this.playersData.get(player);
    }

    /**
     * @return  Placed blocks
     */
    public List<Block> getPlacedBlocks()
    {
        return this.placedBlocks;
    }

    /**
     * @return  Game settings
     */
    public GameSettings getGameSettings()
    {
        return this.settings;
    }

}
