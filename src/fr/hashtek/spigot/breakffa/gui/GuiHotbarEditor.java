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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

public class GuiHotbarEditor extends HashGui
{

    private Mask mask;

    private final HashGuiManager guiManager;

    private final PlayerSettings playerSettings;


    public GuiHotbarEditor(Player player)
    {
        super("" + ChatColor.WHITE + ChatColor.BOLD + "Personnalisation ● " + ChatColor.GOLD + "Hotbar", 6);

        final BreakFFA main = BreakFFA.getInstance();
        final GameManager gameManager = main.getGameManager();
        final PlayerData playerData = gameManager.getPlayerData(player);

        this.guiManager = main.getGuiManager();

        this.playerSettings = playerData.getPlayerSettings();

        this.createGui();
    }


    private void createGui()
    {
        final HashItem orangeGlass = HashItem.separator((byte) 1, this.guiManager);
        final HashItem redGlass = HashItem.separator((byte) 14, this.guiManager);

        final HashItem titleItem = new HashItem(Material.SIGN)
            .setName(ChatColor.GOLD + "Configuration de la Hotbar")
            .setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "La " + ChatColor.GOLD + "hotbar" + ChatColor.GRAY + "représente les 9 et uniques " + ChatColor.YELLOW + "emplacements",
                ChatColor.GRAY + "d'inventaire que vous pouvez utiliser dans le " + ChatColor.RED + "BreakFFA" + ChatColor.GRAY + ".",
                "",
                ChatColor.GRAY + "Les objets obtenus via le " + ChatColor.AQUA + "Marché Nexus" + ChatColor.GRAY + " (ou autre)",
                ChatColor.GRAY + "seront " + ChatColor.GREEN + "placés" + ChatColor.GRAY + "dans le slot libre le plus " + ChatColor.YELLOW + "à gauche" + ChatColor.GRAY + "."
            ))
            .setUntakable(true)
            .build(this.guiManager);

        final HashItem confirmItem = new HashItem(Material.INK_SACK, 1, (byte) 10)
            .setName(ChatColor.GREEN + "Valider")
            .addLore(ChatColor.GRAY + "Ferme la configuration en sauvegardant.")
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        if (!(gui instanceof GuiHotbarEditor))
                            return;

                        final GuiHotbarEditor guiHotbarEditor = (GuiHotbarEditor) gui;

                        guiHotbarEditor.updatePlayerHotbarLayout(player, gui);
                        player.sendMessage(ChatColor.GREEN + "Vos modifications ont été enregistrées.");
                        guiHotbarEditor.close(player);
                    })
            )
            .build(this.guiManager);

        final HashItem resetItem = new HashItem(Material.PAPER)
            .setName(ChatColor.AQUA + "Réinitialiser")
            .addLore(ChatColor.GRAY + "Restore la hotbar par défaut.")
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        if (!(gui instanceof GuiHotbarEditor))
                            return;

                        final GuiHotbarEditor guiHotbarEditor = (GuiHotbarEditor) gui;

                        guiHotbarEditor.getMask()
                            .pattern(3, PlayerSettings.DEFAULT_HOTBAR_LAYOUT)
                            .apply();

                        guiHotbarEditor.update(player);
                    })
            )
            .build(this.guiManager);

        final HashItem cancelItem = new HashItem(Material.INK_SACK, 1, (byte) 1)
            .setName(ChatColor.RED + "Annuler")
            .addLore(ChatColor.GRAY + "Ferme la configuration sans sauvegarder.")
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                        gui.close(player);
                        player.sendMessage(ChatColor.RED + "Modifications annulées.");
                    })
            )
            .build(this.guiManager);

        this.mask = new Mask(this);

        this.mask.setItem('s', KitStarter.Items.SWORD.getItem())
            .setItem('p', KitStarter.Items.PICKAXE.getItem())
            .setItem('t', KitStarter.Items.INSTANT_TNT.getItem())
            .setItem('g', KitStarter.Items.GOLDEN_APPLES.getItem())
            .setItem('b', KitStarter.Items.BLOCKS.getItem())

            .setItem('T', titleItem)
            .setItem('C', confirmItem)
            .setItem('R', resetItem)
            .setItem('c', cancelItem)
            .setItem('O', orangeGlass)
            .setItem('r', redGlass);

        this.mask
            .pattern("rOOOTOOOr")
            .pattern("rrrrrrrrr")
            .pattern(this.playerSettings.getHotbarLayout())
            .pattern("rrrrrrrrr")
            .pattern("rOOOOOOOr")
            .pattern("rOCOROcOr");

        this.mask.apply();
    }

    public void updatePlayerHotbarLayout(Player player, HashGui gui)
    {
        StringBuilder newHotbarLayout = new StringBuilder();

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

        System.out.println("New Hotbar layout: '" + newHotbarLayout + "'.");

        this.playerSettings.setHotbarLayout(newHotbarLayout.toString());
    }

    public Mask getMask()
    {
        return this.mask;
    }

}
