package fr.hashtek.spigot.breakffa.player;

public class PlayerSettings
{

    public static final String DEFAULT_HOTBAR_LAYOUT = "sptgb    ";

    private String hotbarLayout;


    public PlayerSettings()
    {
        this(DEFAULT_HOTBAR_LAYOUT);
    }

    public PlayerSettings(String hotbarLayout)
    {
        this.hotbarLayout = hotbarLayout;
    }


    public String getHotbarLayout()
    {
        return this.hotbarLayout;
    }

    public void setHotbarLayout(String hotbarLayout)
    {
        this.hotbarLayout = hotbarLayout;
    }

}
