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

public enum KitLobby
{

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
