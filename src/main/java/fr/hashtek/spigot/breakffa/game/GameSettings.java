package fr.hashtek.spigot.breakffa.game;

import fr.hashtek.hashlogger.HashLoggable;
import fr.hashtek.hashlogger.HashLogger;
import fr.hashtek.spigot.breakffa.BreakFFA;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.Arrays;
import java.util.List;

public class GameSettings implements HashLoggable
{

    private static final HashLogger LOGGER = BreakFFA.getInstance().getHashLogger();

    private double maxHeight;
    private double minHeight;


    /**
     * Creates a new instance of GameSettings.
     */
    public GameSettings()
    {
        this.maxHeight = 0;
        this.minHeight = 0;
    }


    /**
     * Setups settings from the configuration file.
     *
     * @param   yaml                    Configuration file content
     * @throws  NoSuchFieldException    Field not found (invalid content)
     */
    public void setup(YamlFile yaml)
        throws NoSuchFieldException
    {
        LOGGER.info(this, "Loading settings...");

        final String prefix = "settings";

        final List<String> requiredKeys = Arrays.asList("", ".maxHeight", ".minHeight");

        for (String key : requiredKeys) {
            if (!yaml.contains(prefix + key)) {
                throw new NoSuchFieldException("\"" + prefix + key + "\" field not found.");
            }
        }

        this.maxHeight = yaml.getDouble(prefix + ".maxHeight");
        this.minHeight = yaml.getDouble(prefix + ".minHeight");

        LOGGER.info(this, String.format(
            "Successfully loaded settings.\n" +
            "(Minimum height: %.2f, Maximum height: %.2f)",
            this.maxHeight, this.minHeight
        ));
    }


    /**
     * @return  Maximum build height
     */
    public double getMaxHeight()
    {
        return this.maxHeight;
    }

    /**
     * @return  Minimum build height
     */
    public double getMinHeight()
    {
        return this.minHeight;
    }

}
