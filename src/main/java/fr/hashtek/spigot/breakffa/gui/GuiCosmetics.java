package fr.hashtek.spigot.breakffa.gui;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.cosmetics.CosmeticManager;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class GuiCosmetics extends HashGui
{

    private final HashGuiManager guiManager;
    private final CosmeticManager cosmeticManager;


    public GuiCosmetics(Player player)
    {
        super(
            Component.text(""),
            6
        );

        final BreakFFA main = BreakFFA.getInstance();
        final GameManager gameManager = main.getGameManager();
        final PlayerData playerData = gameManager.getPlayerData(player);

        this.guiManager = main.getGuiManager();
        this.cosmeticManager = playerData.getPlayerManager().getCosmeticManager();
    }

}
