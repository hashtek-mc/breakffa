package fr.hashtek.spigot.breakffa.hotbar;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.player.PlayerSettings;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class HotbarEditor
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();
    private static final HashGuiManager GUI_MANAGER = MAIN.getGuiManager();

    private enum Items
    {

        GREEN_SEP (HashItem.separator(Material.LIME_STAINED_GLASS_PANE, GUI_MANAGER)),
        BLACK_SEP (HashItem.separator(Material.BLACK_STAINED_GLASS_PANE, GUI_MANAGER)),
        LIGHT_BLUE_SEP(HashItem.separator(Material.LIGHT_BLUE_STAINED_GLASS_PANE, GUI_MANAGER)),

        TITLE (new HashItem(Material.WARPED_SIGN)
            .setName(Component.text(ChatColor.GOLD + "Configuration de la Hotbar"))
            .setLore(Arrays.asList(
                Component.text(
                    ChatColor.GRAY + "Disposez ici chaque objet de départ\n" +
                    "du " + ChatColor.RED + "BreakFFA" + ChatColor.GRAY + " dans les emplacements que\n" +
                    "vous voulez afin qu'ils restent ainsi\n" +
                    "à chaque " + ChatColor.GREEN + "réapparition" + ChatColor.GRAY + " et " + ChatColor.GREEN + "reconnexion" + ChatColor.GRAY + "."
                ),
                Component.text(""),
                Component.text(
                    ChatColor.GRAY + "À " + ChatColor.BLUE + "droite" + ChatColor.GRAY + ", vous avez un emplacement qui\n" +
                    "correspond à votre " + ChatColor.YELLOW + "seconde main" + ChatColor.GRAY + ".\n" +
                    ChatColor.GRAY + "À " + ChatColor.BLUE + "gauche" + ChatColor.GRAY + " se trouve un bouton permettant\n" +
                    "de " + ChatColor.DARK_GREEN + "sauvegarder" + ChatColor.GRAY + " vos paramètres."
                ),
                Component.text(""),
                Component.text(
                    ChatColor.GRAY + "Pour " + ChatColor.RED + "annuler" + ChatColor.GRAY + " vos modifications, cliquez\n" +
                    "sur ce " + ChatColor.YELLOW + "panneau" + ChatColor.GRAY + " pour revenir en arrière."
                )
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                        .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                            gui.close(player);
                            MAIN.getGameManager().getPlayerManager(player).backToLobby();
                        })
            )
            .build(GUI_MANAGER)
        ),

        SAVE (new HashItem(Material.LIME_DYE)
            .setName(Component.text(ChatColor.GREEN + "Sauvegarder"))
            .addLore(Component.text(
                ChatColor.GRAY + "Sauvegarde votre " + ChatColor.DARK_GREEN + "configuration" + ChatColor.GRAY + " personnalisée\n" +
                "de la " + ChatColor.GOLD + "hotbar" + ChatColor.GRAY + " du " + ChatColor.RED + "BreakFFA" + ChatColor.GRAY + "."
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        HotbarEditor.updatePlayerHotbarLayout(player);
                        player.sendMessage(Component.text(ChatColor.GREEN + "Vos modifications ont été enregistrées."));

                        gui.close(player);
                        MAIN.getGameManager().getPlayerManager(player).backToLobby();
                    })
            )
            .build(GUI_MANAGER)
        ),

        RESET (new HashItem(Material.PAPER)
            .setName(Component.text(ChatColor.AQUA + "Réinitialiser"))
            .addLore(Component.text(ChatColor.GRAY + "Restore la hotbar par défaut."))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        HotbarEditor.updateHotbarLayout(player.getInventory(), PlayerSettings.DEFAULT_HOTBAR_LAYOUT);
                    })
            )
            .build(GUI_MANAGER)
        );

        private final HashItem item;


        Items(HashItem item)
        {
            this.item = item;
        }


        public HashItem getItem()
        {
            return this.item;
        }

    }


    public HotbarEditor(Player player)
    {
        this.init(
            player.getInventory(),
            MAIN.getGameManager().getPlayerManager(player).getData()
                .getSettings().getHotbarLayout()
        );
    }


    public void init(PlayerInventory inventory, String hotbarLayout)
    {
        final Mask mask = new Mask(inventory);

        mask.setItem('g', Items.GREEN_SEP.getItem())
            .setItem('n', Items.BLACK_SEP.getItem())
            .setItem('l', Items.LIGHT_BLUE_SEP.getItem())

            .setItem('N', Items.TITLE.getItem())
            /*                 ^ 'N' and not 'T' because 'T' is already used by the TNT in the lobby kit. */
            .setItem('5', Items.SAVE.getItem())
            /*                 ^ '5' and not 'S' because 'S' is already used by the sword in the lobby kit. */
            .setItem('R', Items.RESET.getItem());

        mask.pattern(2, "gggnnnlll")
            .pattern(3, "g5gnNnlRl")
            .pattern(4, "gggnnnlll");

        mask.apply();

        updateHotbarLayout(inventory, hotbarLayout);
    }

    private static void updateHotbarLayout(PlayerInventory inventory, String hotbarLayout)
    {
        final Mask mask = new Mask(inventory);
        final char lastCharOfHotbarLayout = hotbarLayout.charAt(hotbarLayout.length() - 1);

        for (KitStarter.Items item : KitStarter.Items.values()) {
            if (item.getCharacter() == ' ') {
                continue;
            }
            final ItemStack i = item.getItem().getItemStack().clone();
            i.setAmount(1);
            mask.setItem(item.getCharacter(), i);
        }

        mask.pattern(hotbarLayout.substring(0, 9));
        mask.apply();

        if (lastCharOfHotbarLayout != ' ') {
            /* NPE no-check is okay because we're verifying first that the last char is not empty.  ↓ */
            final ItemStack i = KitStarter.Items.getItemFromCharacter(lastCharOfHotbarLayout).getItemStack().clone();
            i.setAmount(1);
            inventory.setItemInOffHand(i);
        }
    }

    public static void updatePlayerHotbarLayout(Player player)
    {
        final PlayerSettings playerSettings = MAIN.getGameManager().getPlayerManager(player).getData().getSettings();
        final StringBuilder newHotbarLayout = new StringBuilder();
        final PlayerInventory inventory = player.getInventory();

        /* Getting the player's hotbar content and creating a new hotbar layout from it. */
        for (int k = 0; k < 9; k++) {
            final ItemStack item = inventory.getItem(k);
            newHotbarLayout.append(KitStarter.Items.getItemCharacter(item));
        }

        /* Off-hand management */
        final ItemStack itemInOffHand = inventory.getItemInOffHand();
        newHotbarLayout.append(KitStarter.Items.getItemCharacter(itemInOffHand));

        playerSettings.setHotbarLayout(newHotbarLayout.toString());
    }

}
