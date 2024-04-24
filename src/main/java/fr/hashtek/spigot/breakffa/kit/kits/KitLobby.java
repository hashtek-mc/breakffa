package fr.hashtek.spigot.breakffa.kit.kits;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.gui.GuiHotbarEditor;
import fr.hashtek.spigot.breakffa.kit.KitItems;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.breakffa.spectator.SpectatorMode;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.handler.interact.InteractHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitLobby
{

    public enum Items implements KitItems
    {

        BACK_TO_LOBBY (
            new HashItem(Material.IRON_DOOR)
                .setName(ChatColor.RED + "Revenir au Lobby")
                .setLore(Arrays.asList(
                    ChatColor.GRAY + "Quittez le mode de jeu " + ChatColor.RED + "BreakFFA",
                    ChatColor.GRAY + "pour revenir au " + ChatColor.WHITE + "Hub" + ChatColor.GRAY + " principal."
                ))
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            player.sendMessage(ChatColor.GOLD + "En cours de développement.");
                        })
                )
                .build(BreakFFA.getInstance().getGuiManager()),
            -1
        ),

        COSMETICS (
            new HashItem(Material.EMERALD)
                .setName(ChatColor.AQUA + "Customisation")
                .setLore(Arrays.asList(
                    ChatColor.GRAY + "Accédez au menu des " + ChatColor.AQUA + "cosmétiques" + ChatColor.GRAY + " du",
                    ChatColor.RED + "BreakFFA" + ChatColor.GRAY + " et gérez vos préférences."
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            player.sendMessage(ChatColor.GOLD + "En cours de développement.");
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            player.sendMessage(ChatColor.GOLD + "En cours de développement.");
                        })
                )
                .build(BreakFFA.getInstance().getGuiManager()),
            1
        ),

        HOTBAR_EDITOR (
            new HashItem(Material.REDSTONE_COMPARATOR)
                .setName(ChatColor.GOLD + "Hotbar")
                .setLore(Arrays.asList(
                    ChatColor.GRAY + "Personnalisez votre " + ChatColor.GOLD + "Hotbar" + ChatColor.GRAY + " en fonction",
                    ChatColor.GRAY + "de votre " + ChatColor.YELLOW + "style" + ChatColor.GRAY + " de jeu."
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            new GuiHotbarEditor(player).open(player);
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            new GuiHotbarEditor(player).open(player);
                        })
                )
                .build(BreakFFA.getInstance().getGuiManager()),
            2
        ),

        PLAY (
            new HashItem(Material.DIAMOND_SWORD)
                .setName(ChatColor.YELLOW + "Jouer")
                .setLore(Arrays.asList(
                    ChatColor.GRAY + "Plongez dans " + ChatColor.GOLD + "l'action" + ChatColor.GRAY + " et tentez de",
                    ChatColor.GRAY + "briser le " + ChatColor.DARK_RED + "Nexus" + ChatColor.GRAY + " au centre !"
                ))
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

        SPECTATE (
            new HashItem(Material.EYE_OF_ENDER)
                .setName(ChatColor.DARK_AQUA + "Observer")
                .setLore(Arrays.asList(
                    ChatColor.GRAY + "Devenez un " + ChatColor.DARK_AQUA + "fantôme spectateur",
                    ChatColor.GRAY + "volant et sans collisions."
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            final BreakFFA main = BreakFFA.getInstance();
                            final PlayerData playerData = main.getGameManager().getPlayerData(player);
                            final PlayerManager playerManager = playerData.getPlayerManager();

                            if (playerManager.getSpectatorMode() == null)
                                playerManager.setSpectatorMode(new SpectatorMode(main, player));

                            playerManager.getSpectatorMode().go();
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            final BreakFFA main = BreakFFA.getInstance();
                            final PlayerData playerData = main.getGameManager().getPlayerData(player);
                            final PlayerManager playerManager = playerData.getPlayerManager();

                            if (playerManager.getSpectatorMode() == null)
                                playerManager.setSpectatorMode(new SpectatorMode(main, player));

                            playerManager.getSpectatorMode().go();
                        })
                )
                .build(BreakFFA.getInstance().getGuiManager()),
            6
        ),

        STATS (
            new HashItem(Material.PAPER)
                .setName(ChatColor.BLUE + "Statistiques")
                .setLore(Arrays.asList(
                    ChatColor.GRAY + "Affichez vos " + ChatColor.BLUE + "statistiques détaillées",
                    ChatColor.GRAY + "dans le mode " + ChatColor.RED + "BreakFFA"
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            player.sendMessage(ChatColor.GOLD + "En cours de développement.");
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            player.sendMessage(ChatColor.GOLD + "En cours de développement.");
                        })
                )
                .build(BreakFFA.getInstance().getGuiManager()),
            7
        );


        private final HashItem item;
        private final int slotIndex;


        Items(HashItem item, int slotIndex)
        {
            this.item = item;
            this.slotIndex = slotIndex;
        }


        @Override
        public HashItem getItem()
        {
            return this.item;
        }

        @Override
        public int getSlotIndex()
        {
            return this.slotIndex;
        }

        @Override
        public void give(Player player)
        {
            if (this.slotIndex == -1)
                return;

            player.getInventory().setItem(this.slotIndex, this.getItem().getItemStack());
        }

    }

    private final BreakFFA main;


    /**
     * Creates a new instance of Lobby kit
     *
     * @param   main    BreakFFA instance
     */
    public KitLobby(BreakFFA main)
    {
        this.main = main;
    }


    /**
     * Gives the kit to a player.
     *
     * @param   player  Player
     */
    public void giveItems(Player player)
    {
        final BreakFFA main = BreakFFA.getInstance();
        final HashGuiManager guiManager = main.getGuiManager();
        final Inventory inventory = player.getInventory();

        final HashItem blackGlass = HashItem.separator((byte) 15, guiManager);
        final HashItem redGlass = HashItem.separator((byte) 14, guiManager);

        final Mask mask = new Mask(inventory);

        mask.setItem('b', blackGlass)
            .setItem('r', redGlass)
            .setItem('l', Items.BACK_TO_LOBBY.getItem());

        mask.pattern(2, "bbbrrrbbb")
            .pattern(3, "bbbrlrbbb")
            .pattern(4, "bbbrrrbbb");

        mask.apply();

        for (Items item : Items.values())
            item.give(player);
    }

}
