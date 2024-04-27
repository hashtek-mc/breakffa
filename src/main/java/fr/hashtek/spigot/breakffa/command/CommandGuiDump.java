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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandGuiDump implements CommandExecutor
{

    @Override
    public boolean onCommand(
        CommandSender sender,
        Command command,
        String label,
        String[] args
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

        for (String str : clickManager.getClickHandlers().keySet()) {
            final List<ClickHandler> handlers = clickManager.getClickHandlers().get(str);

            sender.sendMessage("Item: \"" + str + "\" (" + handlers.size() + ")");
        }

        sender.sendMessage("\n-----------------\nInteract Handlers (" + interactManager.getInteractHandlers().size() + ") :\n");

        for (String str : interactManager.getInteractHandlers().keySet()) {
            final List<InteractHandler> handlers = interactManager.getInteractHandlers().get(str);

            sender.sendMessage("Item: \"" + str + "\" (" + handlers.size() + ")");
        }

        sender.sendMessage("\n-----------------\nHold Handlers (" + holdManager.getHoldHandlers().size() + ") :\n");

        for (String str : holdManager.getHoldHandlers().keySet()) {
            final List<HoldHandler> handlers = holdManager.getHoldHandlers().get(str);

            sender.sendMessage("Item: \"" + str + "\" (" + handlers.size() + ")");
        }

        sender.sendMessage("\n-----------------\nHit Handlers (" + hitManager.getHitHandlers().size() + ") :\n");

        for (String str : hitManager.getHitHandlers().keySet()) {
            final List<HitHandler> handlers = hitManager.getHitHandlers().get(str);

            sender.sendMessage("Item: \"" + str + "\" (" + handlers.size() + ")");
        }

        return false;
    }

}
