package fr.hashtek.spigot.breakffa.player;

/**
 * Different states a player can be in.
 */
public enum PlayerState
{

    AT_LOBBY (true),
    EDITING_HOTBAR_SETTINGS (true),
    PLAYING (false),
    SPECTATING (false)

    ;


    private final boolean isInLobby;


    PlayerState(boolean isInLobby)
    {
        this.isInLobby = isInLobby;
    }


    public boolean isInLobby()
    {
        return this.isInLobby;
    }

}
