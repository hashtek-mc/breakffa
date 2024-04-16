package fr.hashtek.spigot.breakffa.kit.lobby;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum KitLobbyItem
{

    BACK_TO_LOBBY (
        new HashItem(Material.BED)
            .setName(ChatColor.RED + "Retour au lobby")
            .addInteractHandler(
                new InteractHandler()
                    .addAllInteractTypes()
                    .setInteractAction((Player player, ItemStack item, int slot) -> {
                        player.sendMessage("Will send you to the lobby when ready.");
                    })
            )
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        player.sendMessage("Will send you to the lobby when ready.");
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager()),
        0
    ),

    COSMETICS (
        new HashItem(Material.EMERALD)
            .setName(ChatColor.GREEN + "Cosmétiques")
            .addInteractHandler(
                new InteractHandler()
                    .addAllInteractTypes()
                    .setInteractAction((Player player, ItemStack item, int slot) -> {
                        player.sendMessage("Cosmotikes!!!");
                    })
            )
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        player.sendMessage("Cosmotikes!!!");
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager()),
        2
    ),

    PLAY (
        new HashItem(Material.DIAMOND_AXE)
            .setName(ChatColor.AQUA + "Jouer")
            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
            .addInteractHandler(
                new InteractHandler()
                    .addAllInteractTypes()
                    .setInteractAction((Player player, ItemStack item, int slot) -> {
                        final BreakFFA main = BreakFFA.getInstance();
                        final PlayerData playerData = main.getGameManager().getPlayerData(player);

                        playerData.getPlayerManager().play();
                    })
            )
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        final BreakFFA main = BreakFFA.getInstance();
                        final PlayerData playerData = main.getGameManager().getPlayerData(player);

                        playerData.getPlayerManager().play();
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager()),
        4
    ),

    STATS (
        new HashItem(Material.PAPER)
            .setName(ChatColor.DARK_AQUA + "Statistiques")
            .setLore(Arrays.asList(
                ChatColor.WHITE + "Map actuelle : " + ChatColor.RED + "Infection",
                "" + ChatColor.GRAY + ChatColor.ITALIC + "Réalisée par @mad et @hopecalypse",
                "",
                ChatColor.WHITE + "Total d'éliminations : " + 0,
                ChatColor.WHITE + "Série d'éliminations : " + 0,
                ChatColor.WHITE + "Nexus brisés : " + 0,
                ChatColor.WHITE + "Nexus brisés d'affilé : " + 0,
                ChatColor.WHITE + "Temps joué au total : 0m"
            ))
            .build(BreakFFA.getInstance().getGuiManager()),
        6
    ),

    SPECTATE (
        new HashItem(Material.EYE_OF_ENDER)
            .setName(ChatColor.YELLOW + "Mode spectateur")
            .addInteractHandler(
                new InteractHandler()
                    .addAllInteractTypes()
                    .setInteractAction((Player player, ItemStack item, int slot) -> {
                        player.sendMessage("pas mtn mdr");
                    })
            )
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        player.sendMessage("pas mtn mdr");
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager()),
        8
    );


    private final HashItem item;
    private final int slotIndex;


    /**
     * Creates a new instance of KitLobby.
     *
     * @param   item        Item
     * @param   slotIndex   Slot index
     */
    KitLobbyItem(HashItem item, int slotIndex)
    {
        this.item = item;
        this.slotIndex = slotIndex;
    }


    /**
     * @return  Item
     */
    public HashItem getItem()
    {
        return this.item;
    }

    /**
     * @return  Slot index
     */
    public int getSlotIndex()
    {
        return this.slotIndex;
    }

}
