package fr.hashtek.spigot.breakffa.kit;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum KitLobby
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
            .build(BreakFFA.getInstance().getGUIManager()),
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
            .build(BreakFFA.getInstance().getGUIManager()),
        2
    ),

    PLAY (
        new HashItem(Material.DIAMOND_AXE)
            .setName(ChatColor.AQUA + "Jouer")
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
            .build(BreakFFA.getInstance().getGUIManager()),
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
            .build(BreakFFA.getInstance().getGUIManager()),
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
            .build(BreakFFA.getInstance().getGUIManager()),
        8
    );


    private final HashItem item;
    private final int slotIndex;


    KitLobby(HashItem item, int slotIndex)
    {
        this.item = item;
        this.slotIndex = slotIndex;
    }


    public static void giveItems(Player player)
    {
        final Inventory playerInventory = player.getInventory();

        for (KitLobby kit : KitLobby.values())
            playerInventory.setItem(kit.getSlotIndex(), kit.getItem().getItemStack());
    }


    public HashItem getItem()
    {
        return this.item;
    }

    public int getSlotIndex()
    {
        return this.slotIndex;
    }

}
