package fr.hashtek.spigot.breakffa.command;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.Cosmetic;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeCustomHelmet;
import fr.hashtek.spigot.breakffa.cosmetics.types.CosmeticTypeKSFX;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Debug command
 */
public class CommandCosmeticDump implements CommandExecutor
{

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String[] args
    ) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        final BreakFFA main = BreakFFA.getInstance();
        final GameManager gameManager = main.getGameManager();
        final PlayerManager playerManager = gameManager.getPlayerManager(player);
        final CosmeticManager cosmeticManager = playerManager.getCosmeticManager();

        player.sendMessage(Component.text("" + ChatColor.GOLD + ChatColor.BOLD + "COSMETIC DUMP"));

        player.sendMessage(Component.text("KSFX:"));
        if (cosmeticManager.getCurrentKillSfx() == null) {
            player.sendMessage(Component.text("Current: null"));
        } else {
            player.sendMessage(Component.text("Current: " + cosmeticManager.getCurrentKillSfx().getName()));
        }
        player.sendMessage(Component.text("Owned:"));
        for (Cosmetic<CosmeticTypeKSFX> cosmetic : cosmeticManager.getOwnedKillSfxs()) {
            player.sendMessage(Component.text("  - " + cosmetic.getName()));
        }

        player.sendMessage(Component.text("\n\nCustom helmet:"));
        if (cosmeticManager.getCurrentCustomHelmet() == null) {
            player.sendMessage(Component.text("Current: null"));
        } else {
            player.sendMessage(Component.text("Current: " + cosmeticManager.getCurrentCustomHelmet().getName()));
        }
        player.sendMessage(Component.text("Owned:"));
        for (Cosmetic<CosmeticTypeCustomHelmet> cosmetic : cosmeticManager.getOwnedCustomHelmets()) {
            player.sendMessage(Component.text("  - " + cosmetic.getName()));
        }

        return true;
    }

}
