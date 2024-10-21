package fr.hashtek.spigot.breakffa.player.io;

import fr.hashtek.spigot.breakffa.constants.Constants;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.tekore.common.data.io.AbstractPublisher;
import fr.hashtek.tekore.common.data.redis.RedisAccess;

public class PlayerDataPublisher
    extends AbstractPublisher<PlayerData>
{

    public PlayerDataPublisher(RedisAccess redisAccess)
    {
        super(redisAccess, Constants.PLAYER_DATA_PREFIX);
    }

}
