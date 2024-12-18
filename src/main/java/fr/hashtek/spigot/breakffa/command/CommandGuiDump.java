package fr.hashtek.spigot.breakffa.command;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.click.HashGuiClick;
import fr.hashtek.spigot.hashgui.handler.hit.HashGuiHit;
import fr.hashtek.spigot.hashgui.handler.hit.HitHandler;
import fr.hashtek.spigot.hashgui.handler.hold.HashGuiHold;
import fr.hashtek.spigot.hashgui.handler.hold.HoldHandler;
import fr.hashtek.spigot.hashgui.handler.interact.HashGuiInteraction;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Debug command
 */
public class CommandGuiDump
    implements CommandExecutor
{

    @Override
    public boolean onCommand(
        @NonNull CommandSender sender,
        @NonNull Command command,
        @NonNull String label,
        @NonNull String[] args
    )
    {
        final BreakFFA main = BreakFFA.getInstance();
        final HashGuiManager guiManager = main.getGuiManager();
        final HashGuiClick clickManager = guiManager.getClickManager();
        final HashGuiInteraction interactManager = guiManager.getInteractionManager();
        final HashGuiHold holdManager = guiManager.getHoldManager();
        final HashGuiHit hitManager = guiManager.getHitManager();

        sender.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "Gui Manager dump");

        sender.sendMessage("\n-----------------\nClick Handlers (" + clickManager.getClickHandlers().size() + ") :\n");

        for (Component str : clickManager.getClickHandlers().keySet()) {
            final List<ClickHandler> handlers = clickManager.getClickHandlers().get(str);

            sender.sendMessage("Item: \"" + ((TextComponent) str).content() + "\" (" + handlers.size() + ")");
        }

        sender.sendMessage("\n-----------------\nInteract Handlers (" + interactManager.getInteractHandlers().size() + ") :\n");

        for (Component str : interactManager.getInteractHandlers().keySet()) {
            final List<InteractHandler> handlers = interactManager.getInteractHandlers().get(str);

            sender.sendMessage("Item: \"" + ((TextComponent) str).content() + "\" (" + handlers.size() + ")");
        }

        sender.sendMessage("\n-----------------\nHold Handlers (" + holdManager.getHoldHandlers().size() + ") :\n");

        for (Component str : holdManager.getHoldHandlers().keySet()) {
            final List<HoldHandler> handlers = holdManager.getHoldHandlers().get(str);

            sender.sendMessage("Item: \"" + ((TextComponent) str).content() + "\" (" + handlers.size() + ")");
        }

        sender.sendMessage("\n-----------------\nHit Handlers (" + hitManager.getHitHandlers().size() + ") :\n");

        for (Component str : hitManager.getHitHandlers().keySet()) {
            final List<HitHandler> handlers = hitManager.getHitHandlers().get(str);

            sender.sendMessage("Item: \"" + ((TextComponent) str).content() + "\" (" + handlers.size() + ")");
        }

        return false;
    }

}
