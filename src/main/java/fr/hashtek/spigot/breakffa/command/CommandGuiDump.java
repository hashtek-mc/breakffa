package fr.hashtek.spigot.breakffa.command;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.click.HashGuiClick;
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

        sender.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "Gui Manager dump");

        sender.sendMessage("\n-----------------\nClick Handlers (" + clickManager.getClickHandlers().size() + ") :\n");

        for (String str : clickManager.getClickHandlers().keySet()) {
            final List<ClickHandler> handlers = clickManager.getClickHandlers().get(str);

            sender.sendMessage("Item: \"" + str + "\" (" + handlers.size() + ")");
//            for (ClickHandler handler : handlers) {
//                sender.sendMessage("  - " + handler.getClickTypes());
//            }
        }

        sender.sendMessage("\n-----------------\nInteract Handlers (" + interactManager.getInteractHandlers().size() + ") :\n");

        for (String str : interactManager.getInteractHandlers().keySet()) {
            final List<InteractHandler> handlers = interactManager.getInteractHandlers().get(str);

            sender.sendMessage("Item: \"" + str + "\" (" + handlers.size() + ")");
//            for (InteractHandler handler : handlers) {
//                sender.sendMessage("  - "  + handler.getInteractTypes());
//            }
        }

        return false;
    }

}
