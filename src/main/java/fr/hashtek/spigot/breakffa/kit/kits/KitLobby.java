package fr.hashtek.spigot.breakffa.kit.kits;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.gui.GuiCosmetics;
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
import fr.hashtek.tekore.bukkit.Tekore;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitLobby
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();


    public enum Items implements KitItems
    {

        BACK_TO_LOBBY (
            new HashItem(Material.IRON_DOOR)
                .setName(Component.text(ChatColor.RED + "Revenir au Lobby"))
                .setLore(Arrays.asList(
                    Component.text(ChatColor.GRAY + "Quittez le mode de jeu " + ChatColor.RED + "BreakFFA"),
                    Component.text(ChatColor.GRAY + "pour revenir au " + ChatColor.WHITE + "Hub" + ChatColor.GRAY + " principal.")
                ))
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            final Tekore core = BreakFFA.getInstance().getCore();

                            core.getPlayerManager(player).sendToServer("lobby01");
                        })
                )
                .build(GUI_MANAGER),
            -1
        ),

        COSMETICS (
            new HashItem(Material.EMERALD)
                .setName(Component.text(ChatColor.AQUA + "Customisation"))
                .setLore(Arrays.asList(
                    Component.text(ChatColor.GRAY + "Accédez au menu des " + ChatColor.AQUA + "cosmétiques" + ChatColor.GRAY + " du"),
                    Component.text(ChatColor.RED + "BreakFFA" + ChatColor.GRAY + " et gérez vos préférences.")
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            new GuiCosmetics(player).open(player);
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            new GuiCosmetics(player).open(player);
                        })
                )
                .build(GUI_MANAGER),
            1
        ),

        HOTBAR_EDITOR (
            new HashItem(Material.COMPARATOR)
                .setName(Component.text(ChatColor.GOLD + "Hotbar"))
                .setLore(Arrays.asList(
                    Component.text(ChatColor.GRAY + "Personnalisez votre " + ChatColor.GOLD + "Hotbar" + ChatColor.GRAY + " en fonction"),
                    Component.text(ChatColor.GRAY + "de votre " + ChatColor.YELLOW + "style" + ChatColor.GRAY + " de jeu.")
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
                .build(GUI_MANAGER),
            2
        ),

        PLAY (
            new HashItem(Material.DIAMOND_SWORD)
                .setName(Component.text(ChatColor.YELLOW + "Jouer"))
                .setLore(Arrays.asList(
                    Component.text(ChatColor.GRAY + "Plongez dans " + ChatColor.GOLD + "l'action" + ChatColor.GRAY + " et tentez de"),
                    Component.text(ChatColor.GRAY + "briser le " + ChatColor.DARK_RED + "Nexus" + ChatColor.GRAY + " au centre !")
                ))
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            final PlayerData playerData = MAIN.getGameManager().getPlayerData(player);

                            playerData.getPlayerManager().play();
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            final PlayerData playerData = MAIN.getGameManager().getPlayerData(player);

                            playerData.getPlayerManager().play();
                        })
                )
                .build(GUI_MANAGER),
            4
        ),

        SPECTATE (
            new HashItem(Material.ENDER_EYE)
                .setName(Component.text(ChatColor.DARK_AQUA + "Observer"))
                .setLore(Arrays.asList(
                    Component.text(ChatColor.GRAY + "Devenez un " + ChatColor.DARK_AQUA + "fantôme spectateur"),
                    Component.text(ChatColor.GRAY + "volant et sans collisions.")
                ))
                .addInteractHandler(
                    new InteractHandler()
                        .addAllInteractTypes()
                        .setInteractAction((Player player, ItemStack item, int slot) -> {
                            final PlayerData playerData = MAIN.getGameManager().getPlayerData(player);
                            final PlayerManager playerManager = playerData.getPlayerManager();

                            if (playerManager.getSpectatorMode() == null) {
                                playerManager.setSpectatorMode(new SpectatorMode(MAIN, player));
                            }

                            playerManager.getSpectatorMode().go();
                        })
                )
                .addClickHandler(
                    new ClickHandler()
                        .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            final PlayerData playerData = MAIN.getGameManager().getPlayerData(player);
                            final PlayerManager playerManager = playerData.getPlayerManager();

                            if (playerManager.getSpectatorMode() == null) {
                                playerManager.setSpectatorMode(new SpectatorMode(MAIN, player));
                            }

                            playerManager.getSpectatorMode().go();
                        })
                )
                .build(GUI_MANAGER),
            6
        ),

        STATS (
            new HashItem(Material.PAPER)
                .setName(Component.text(ChatColor.BLUE + "Statistiques"))
                .setLore(Arrays.asList(
                    Component.text(ChatColor.GRAY + "Affichez vos " + ChatColor.BLUE + "statistiques détaillées"),
                    Component.text(ChatColor.GRAY + "dans le mode " + ChatColor.RED + "BreakFFA")
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
                .build(GUI_MANAGER),
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
            if (this.slotIndex == -1) {
                return;
            }
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
        final BreakFFA main = this.main;
        final HashGuiManager guiManager = main.getGuiManager();
        final Inventory inventory = player.getInventory();

        final HashItem blackGlass = HashItem.separator(Material.BLACK_STAINED_GLASS_PANE, guiManager);
        final HashItem redGlass = HashItem.separator(Material.RED_STAINED_GLASS_PANE, guiManager);

        final Mask mask = new Mask(inventory);

        mask.setItem('b', blackGlass)
            .setItem('r', redGlass)
            .setItem('l', Items.BACK_TO_LOBBY.getItem());

        mask.pattern(2, "bbbrrrbbb")
            .pattern(3, "bbbrlrbbb")
            .pattern(4, "bbbrrrbbb");

        mask.apply();

        for (Items item : Items.values()) {
            item.give(player);
        }
    }

}
