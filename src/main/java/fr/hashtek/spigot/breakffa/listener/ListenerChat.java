package fr.hashtek.spigot.breakffa.listener;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.tekore.common.rank.Rank;
import fr.hashtek.tekore.common.account.Account;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ListenerChat
    implements Listener
{

    /**
     * Called when a player sends a message in the chat.
     */
    @EventHandler
    public void onChat(AsyncChatEvent event)
    {
        final Player player = event.getPlayer();
        final Account playerData = BreakFFA.getInstance().getCore()
            .getPlayerManagersManager().getPlayerManager(player).getAccount();
        final Rank playerRank = playerData.getRank();

        event.renderer(new ChatRenderer() {
            @NotNull
            @Override
            public Component render(
                @NotNull Player source,
                @NotNull Component sourceDisplayName,
                @NotNull Component message,
                @NotNull Audience viewer
            ) {
                return Component.text(
                    playerRank.getUsernameColor() + player.getName() + "@" + playerRank.getName()
                    + ChatColor.DARK_GRAY + " $ "
                    + ChatColor.WHITE + ((TextComponent) message).content()
                );
            }
        });

    }

}
