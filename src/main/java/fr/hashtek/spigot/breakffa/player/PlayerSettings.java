package fr.hashtek.spigot.breakffa.player;

public class PlayerSettings
{

    public static final String DEFAULT_HOTBAR_LAYOUT = "sptgb    ";


    private String hotbarLayout;


    /**
     * Creates a new instance of PlayerSettings.
     */
    public PlayerSettings()
    {
        this(DEFAULT_HOTBAR_LAYOUT);
    }

    /**
     * Creates a new instance of PlayerSettings (with custom hotbar layout).
     *
     * @param   hotbarLayout    Player's Hotbar layout
     */
    public PlayerSettings(String hotbarLayout)
    {
        this.hotbarLayout = hotbarLayout;
    }


    /**
     * @return  Hotbar layout
     */
    public String getHotbarLayout()
    {
        return this.hotbarLayout;
    }

    /**
     * @param   hotbarLayout    New Hotbar layout
     */
    public void setHotbarLayout(String hotbarLayout)
    {
        this.hotbarLayout = hotbarLayout;
    }

}
