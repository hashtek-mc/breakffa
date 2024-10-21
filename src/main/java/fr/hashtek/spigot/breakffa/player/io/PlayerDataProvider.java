package fr.hashtek.spigot.breakffa.player.io;

import fr.hashtek.spigot.breakffa.constants.Constants;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.tekore.common.data.io.AbstractProvider;
import fr.hashtek.tekore.common.data.redis.RedisAccess;

public class PlayerDataProvider
    extends AbstractProvider<PlayerData>
{

    public PlayerDataProvider(RedisAccess redisAccess)
    {
        super(redisAccess, Constants.PLAYER_DATA_PREFIX);
    }

}
