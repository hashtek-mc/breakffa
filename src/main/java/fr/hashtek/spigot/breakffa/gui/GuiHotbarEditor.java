package fr.hashtek.spigot.breakffa.gui;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.game.GameManager;
import fr.hashtek.spigot.breakffa.kit.kits.KitStarter;
import fr.hashtek.spigot.breakffa.player.PlayerData;
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

import java.util.Arrays;
import java.util.Map;

public class GuiHotbarEditor extends HashGui
{

    protected enum Items
    {

        ORANGE_SEP (HashItem.separator(Material.ORANGE_STAINED_GLASS_PANE, BreakFFA.getInstance().getGuiManager())),
        RED_SEP (HashItem.separator(Material.RED_STAINED_GLASS_PANE, BreakFFA.getInstance().getGuiManager())),

        TITLE (new HashItem(Material.OAK_SIGN)
            .setName(Component.text(ChatColor.GOLD + "Configuration de la Hotbar"))
            .setLore(Arrays.asList(
                Component.text(""),
                Component.text(ChatColor.GRAY + "La " + ChatColor.GOLD + "hotbar" + ChatColor.GRAY + " représente les 9 et uniques " + ChatColor.YELLOW + "emplacements"),
                Component.text(ChatColor.GRAY + "d'inventaire que vous pouvez utiliser dans le " + ChatColor.RED + "BreakFFA" + ChatColor.GRAY + "."),
                Component.text(""),
                Component.text(ChatColor.GRAY + "Les objets obtenus via le " + ChatColor.AQUA + "Marché Nexus" + ChatColor.GRAY + " (ou autre)"),
                Component.text(ChatColor.GRAY + "seront " + ChatColor.GREEN + "placés" + ChatColor.GRAY + " dans le slot libre le plus " + ChatColor.YELLOW + "à gauche" + ChatColor.GRAY + ".")
            ))
            .setUntakable(true)
            .build(BreakFFA.getInstance().getGuiManager())
        ),

        CONFIRM (new HashItem(Material.INK_SAC, 1, (byte) 10)
            .setName(Component.text(ChatColor.GREEN + "Valider"))
            .addLore(Component.text(ChatColor.GRAY + "Ferme la configuration en sauvegardant."))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        if (!(gui instanceof GuiHotbarEditor guiHotbarEditor)) {
                            return;
                        }

                        guiHotbarEditor.updatePlayerHotbarLayout(guiHotbarEditor);
                        player.sendMessage(ChatColor.GREEN + "Vos modifications ont été enregistrées.");
                        guiHotbarEditor.close(player);
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager())
        ),

        RESET (new HashItem(Material.PAPER)
            .setName(Component.text(ChatColor.AQUA + "Réinitialiser"))
            .addLore(Component.text(ChatColor.GRAY + "Restore la hotbar par défaut."))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        if (!(gui instanceof GuiHotbarEditor guiHotbarEditor)) {
                            return;
                        }

                        guiHotbarEditor.getMask()
                            .pattern(3, PlayerSettings.DEFAULT_HOTBAR_LAYOUT)
                            .apply();

                        guiHotbarEditor.update(player);
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager())
        ),

        CANCEL (new HashItem(Material.INK_SAC, 1, (byte) 1)
            .setName(Component.text(ChatColor.RED + "Annuler"))
            .addLore(Component.text(ChatColor.GRAY + "Ferme la configuration sans sauvegarder."))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        gui.close(player);
                        player.sendMessage(ChatColor.RED + "Modifications annulées.");
                    })
            )
            .build(BreakFFA.getInstance().getGuiManager())
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


    private Mask mask;
    private final PlayerSettings playerSettings;


    /**
     * Creates a new Hotbar editor GUI.
     *
     * @param   player  Player
     */
    public GuiHotbarEditor(Player player)
    {
        super(
            Component.text("" + ChatColor.WHITE + ChatColor.BOLD + "Personnalisation ● " + ChatColor.GOLD + ChatColor.BOLD + "Hotbar"),
            6
        );

        final BreakFFA main = BreakFFA.getInstance();
        final GameManager gameManager = main.getGameManager();
        final PlayerData playerData = gameManager.getPlayerData(player);

        this.playerSettings = playerData.getPlayerSettings();

        this.createGui();
    }


    /**
     * Creates the GUI.
     */
    private void createGui()
    {
        this.mask = new Mask(this);

        this.mask
            .setItem('T', Items.TITLE.getItem())
            .setItem('C', Items.CONFIRM.getItem())
            .setItem('R', Items.RESET.getItem())
            .setItem('c', Items.CANCEL.getItem())
            .setItem('O', Items.ORANGE_SEP.getItem())
            .setItem('r', Items.RED_SEP.getItem());

        this.setHotbar();

        this.mask
            .pattern("rOOOTOOOr")
            .pattern("rrrrrrrrr")
            .pattern(this.playerSettings.getHotbarLayout())
            .pattern("rrrrrrrrr")
            .pattern("rOOOOOOOr")
            .pattern("rOCOROcOr");

        this.mask.apply();
    }

    /**
     * Sets GUI's hotbar
     */
    private void setHotbar()
    {
        final HashItem sword = new HashItem(KitStarter.Items.SWORD.getItem()).setAmount(1);
        final HashItem pickaxe = new HashItem(KitStarter.Items.PICKAXE.getItem()).setAmount(1);
        final HashItem tnt = new HashItem(KitStarter.Items.INSTANT_TNT.getItem()).setAmount(1);
        final HashItem gapples = new HashItem(KitStarter.Items.GOLDEN_APPLES.getItem()).setAmount(1);
        final HashItem blocks = new HashItem(KitStarter.Items.BLOCKS.getItem()).setAmount(1);

        this.mask
            .setItem('s', sword)
            .setItem('p', pickaxe)
            .setItem('t', tnt)
            .setItem('g', gapples)
            .setItem('b', blocks);
    }

    /**
     * Updates Player's Hotbar layout based on the GUI content.
     *
     * @param   gui     GUI
     */
    public void updatePlayerHotbarLayout(GuiHotbarEditor gui)
    {
        final StringBuilder newHotbarLayout = new StringBuilder();

        /* Not really explicit, but here we're getting the GUI's 3rd line content. */
        for (int k = 18; k < 27; k++) {
            final ItemStack item = gui.getInventory().getItem(k);

            if (item == null || item.getType() == Material.AIR) {
                newHotbarLayout.append(" ");
                continue;
            }

            for (Map.Entry<Character, HashItem> entry : this.mask.getItems().entrySet()) {
                if (entry.getValue().getItemStack().equals(item)) {
                    newHotbarLayout.append(entry.getKey());
                    break;
                }
            }
        }

        this.playerSettings.setHotbarLayout(newHotbarLayout.toString());
    }


    /**
     * @return  GUI's Mask
     */
    public Mask getMask()
    {
        return this.mask;
    }

}
