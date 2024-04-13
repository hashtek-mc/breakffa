package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.hashconfig.HashConfig;
import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.*;

public class GameManager implements HashLoggable
{

    private final BreakFFA main;
    private final HashLogger logger;

    private Block nexus;
    private Location lobbySpawnLocation;
    private final List<Location> spawnLocations;

    private final Date lastReset;
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

        this.spawnLocations = new ArrayList<Location>();

        this.lastReset = new Date();
        this.playersData = new HashMap<Player, PlayerData>();
        this.placedBlocks = new ArrayList<Block>();

        this.settings = new GameSettings(this.logger);
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

        final List<String> requiredKeys = Arrays.asList("", ".x", ".y", ".z");

        for (String key : requiredKeys)
            if (!yaml.contains(prefix + key))
                throw new NoSuchFieldException("\"" + prefix + key + "\" field not found.");

        double x = yaml.getDouble(prefix + ".x");
        double y = yaml.getDouble(prefix + ".y");
        double z = yaml.getDouble(prefix + ".z");

        this.nexus = world.getBlockAt(new Location(world, x, y, z));

        this.logger.info(this, String.format(
            "Successfully loaded Nexus.\n" +
            "(X: %f, Y: %f, Z: %f, Type: %s)",
            x, y, z, this.nexus.getType()
        ));
    }

    /**
     * Setups Nexus from the configuration file.
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

        final List<String> requiredKeys = Arrays.asList("", ".x", ".y", ".z");

        for (String key : requiredKeys)
            if (!yaml.contains(prefix + key))
                throw new NoSuchFieldException("\"" + prefix + key + "\" field not found.");

        double x = yaml.getDouble(prefix + ".x");
        double y = yaml.getDouble(prefix + ".y");
        double z = yaml.getDouble(prefix + ".z");
        float pitch = (float) yaml.getDouble(prefix + ".pitch");
        float yaw = (float) yaml.getDouble(prefix + ".yaw");

        this.lobbySpawnLocation = new Location(world, x, y, z, pitch, yaw);

        this.logger.info(this, String.format(
            "Successfully loaded Lobby spawn.\n" +
            "(X: %f, Y: %f, Z: %f, Pitch: %f, Yaw: %f)",
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

            double x = (double) spawn.get("x");
            double y = (double) spawn.get("y");
            double z = (double) spawn.get("z");
            float pitch = (float) ((double) spawn.get("pitch"));
            float yaw = (float) ((double) spawn.get("yaw"));

            this.spawnLocations.add(new Location(world, x, y, z, pitch, yaw));

            this.logger.info(this, String.format(
                "Successfully loaded spawn %d\n" +
                "(X: %f, Y: %f, Z: %f, Pitch: %f, Yaw: %f)",
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
     * @return  Game's Nexus
     */
    public Block getNexus()
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
