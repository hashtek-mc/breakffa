package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.hashconfig.HashConfig;
import fr.hashtek.hasherror.HashError;
import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.*;

public class GameManager
    implements HashLoggable
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashLogger LOGGER = MAIN.getHashLogger();

    private final Nexus nexus;
    private Location lobbySpawnLocation;
    private final List<Location> spawnLocations;
    private Location spectatorModeSpawnLocation;

    private Date lastReset;
    private final Map<Player, PlayerManager> playerManagers;
    private final List<Block> placedBlocks;

    private final GameSettings settings;


    /**
     * Creates a new instance of GameManager
     */
    public GameManager()
    {
        this.nexus = new Nexus();
        this.spawnLocations = new ArrayList<Location>();

        this.lastReset = new Date();
        this.playerManagers = new HashMap<Player, PlayerManager>();
        this.placedBlocks = new ArrayList<Block>();

        this.settings = new GameSettings();
    }


    /**
     * Checks if keys are present in the configuration file.
     *
     * @param   yaml    Configuration file content
     * @param   keys    Keys to check
     * @return  First missing key (if one, else returns null)
     */
    private String checkRequiredKeys(YamlFile yaml, String... keys)
    {
        for (String key : keys) {
            if (!yaml.contains(key)) {
                return key;
            }
        }
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
        LOGGER.info(this, "Loading Nexus...");

        final String prefix = "nexusLocation";

        final String missingKey = this.checkRequiredKeys(yaml,
            prefix, prefix + ".x", prefix + ".y", prefix + ".z"
        );

        if (missingKey != null) {
            throw new NoSuchFieldException("\"" + missingKey + "\" field not found.");
        }

        final double x = yaml.getDouble(prefix + ".x");
        final double y = yaml.getDouble(prefix + ".y");
        final double z = yaml.getDouble(prefix + ".z");

        this.nexus.setBlock(world.getBlockAt(new Location(world, x, y, z)));

        LOGGER.info(this, String.format(
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
        LOGGER.info(this, "Loading Lobby spawn...");

        final String prefix = "lobbySpawn";
        
        final String missingKey = this.checkRequiredKeys(yaml,
            prefix, prefix + ".x", prefix + ".y", prefix + ".z"
        );

        if (missingKey != null) {
            throw new NoSuchFieldException("\"" + missingKey + "\" field not found.");
        }

        final double x = yaml.getDouble(prefix + ".x");
        final double y = yaml.getDouble(prefix + ".y");
        final double z = yaml.getDouble(prefix + ".z");
        final float pitch = (float) yaml.getDouble(prefix + ".pitch");
        final float yaw = (float) yaml.getDouble(prefix + ".yaw");

        this.lobbySpawnLocation = new Location(world, x, y, z, pitch, yaw);

        LOGGER.info(this, String.format(
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
        LOGGER.info(this, "Loading spawns...");

        final List<String> requiredKeys = Arrays.asList("x", "y", "z");

        final List<Map<String, Object>> spawns = (List<Map<String, Object>>) yaml.getList("spawns");

        if (spawns == null || spawns.isEmpty()) {
            LOGGER.fatal(this, "No spawns set.");
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
                LOGGER.critical(this, "Spawn " + index + " has missing keys. Ignoring.");
                continue;
            }

            final double x = (double) spawn.get("x");
            final double y = (double) spawn.get("y");
            final double z = (double) spawn.get("z");
            final float pitch = (float) ((double) spawn.get("pitch"));
            final float yaw = (float) ((double) spawn.get("yaw"));

            this.spawnLocations.add(new Location(world, x, y, z, pitch, yaw));

            LOGGER.info(this, String.format(
                "Successfully loaded spawn %d\n" +
                "(X: %.2f, Y: %.2f, Z: %.2f, Pitch: %.2f, Yaw: %.2f)",
                this.spawnLocations.size(),
                x, y, z, pitch, yaw
            ));
        }

        LOGGER.info(this, "Successfully loaded " + this.spawnLocations.size() + " spawns!");
    }

    /**
     * Setups Spectator mode spawn location from the configuration file.
     *
     * @param   yaml                    Configuration file content
     * @param   world                   Game world
     * @throws  NoSuchFieldException    Field not found (invalid content)
     */
    public void setupSpectatorModeSpawnLocation(YamlFile yaml, World world)
        throws NoSuchFieldException
    {
        LOGGER.info(this, "Loading Spectator mode spawn...");

        final String prefix = "spectatorModeSpawn";

        final String missingKey = checkRequiredKeys(yaml,
            prefix, prefix + ".x", prefix + ".y", prefix + ".z"
        );

        if (missingKey != null) {
            throw new NoSuchFieldException("\"" + missingKey + "\" field not found.");
        }

        final double x = yaml.getDouble(prefix + ".x");
        final double y = yaml.getDouble(prefix + ".y");
        final double z = yaml.getDouble(prefix + ".z");

        this.spectatorModeSpawnLocation = new Location(world, x, y, z);

        LOGGER.info(this, String.format(
            "Successfully loaded Spectator mode spawn.\n" +
            "(X: %.2f, Y: %.2f, Z: %.2f)",
            x, y, z
        ));
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
        LOGGER.info(this, "Setting up BreakFFA game...");

        final YamlFile yaml = config.getYaml();
        final World world = MAIN.getServer().getWorld("breakffa");

        try {
            this.setupNexusLocation(yaml, world);
            this.setupLobbySpawnLocation(yaml, world);
            this.setupSpawns(yaml, world);
            this.setupSpectatorModeSpawnLocation(yaml, world);
            this.settings.setup(yaml);
        } catch (NoSuchFieldException exception) {
            HashError.UNKNOWN
                .log(LOGGER, this, exception);
            throw new Exception("Could not properly setup game.");
        }

        LOGGER.info(this, "Successfully set up BreakFFA game!");
    }

    /**
     * Resets the map.
     * Used when {@link Nexus} is broken.
     */
    public void resetMap()
    {
        for (Player player : MAIN.getServer().getOnlinePlayers()) {
            final PlayerManager playerManager = this.getPlayerManager(player);
            playerManager.backToLobby();
        }

        for (Block block : this.placedBlocks) {
            block.setType(Material.AIR);
        }
        this.placedBlocks.clear();

        this.lastReset = new Date();
    }


    /**
     * Adds a player's manager to the main map.
     *
     * @param   player          Player
     */
    public PlayerManager createPlayerManager(Player player)
    {
        final PlayerManager playerManager = new PlayerManager(player);

        this.playerManagers.put(player, playerManager);
        return playerManager;
    }

    /**
     * Removes a player's manager from the main map.
     *
     * @param   player  Player
     */
    public PlayerManager removePlayerManager(Player player)
    {
        return this.playerManagers.remove(player);
    }

    /**
     * @param   player  Player
     * @return  Player's manager
     */
    public PlayerManager getPlayerManager(Player player)
    {
        return this.playerManagers.get(player);
    }

    /**
     * @return  Players managers
     */
    public Map<Player, PlayerManager> getPlayerManagers()
    {
        return this.playerManagers;
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
     * @return  Spectator mode Spawn location
     */
    public Location getSpectatorModeSpawnLocation()
    {
        return this.spectatorModeSpawnLocation;
    }

    /**
     * @return  Last game reset (last time a player has mined the Nexus)
     */
    public Date getLastReset()
    {
        return this.lastReset;
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
