package fr.hashtek.spigot.breakffa.kit.kits;

import fr.hashtek.spigot.breakffa.BreakFFA;
import fr.hashtek.spigot.breakffa.kit.KitItems;
import fr.hashtek.spigot.breakffa.player.PlayerData;
import fr.hashtek.spigot.breakffa.player.PlayerManager;
import fr.hashtek.spigot.hashgui.HashGui;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.mask.Mask;
import fr.hashtek.spigot.hashitem.HashItem;
import fr.hashtek.spigot.hashitem.HashSkull;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitSpectator
{

    private static final BreakFFA MAIN = BreakFFA.getInstance();


    /**
     * getSlotIndex() and give() functions are empty because
     * they won't be used. Masks are going to carry this.
     */
    public enum Items
        implements KitItems
    {

        TELEPORT (
            new Object() {
                HashItem evaluate() {
                    return new HashSkull()
                        .setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTllNzk4MDM4YjU2MTA0M2U5YmNlZGQ1MmU0YTM2ZDVlZWJkOTkxNzA5NzM0ZWNjMzAxZDkyMmRhNjI1OWM0In19fQ==")
                        .setName(Component.text(ChatColor.BLUE + "Téléportation"))
                        .setLore(Arrays.asList(
                            Component.text(ChatColor.GRAY + "Cliquez pour vous " + ChatColor.BLUE + "téléporter" + ChatColor.GRAY + " au"),
                            Component.text(ChatColor.GRAY + "joueur de votre choix.")
                        ))
                        .addClickHandler(
                            new ClickHandler()
                                .addAllClickTypes()
                                .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                                    // TODO: Open teleportation GUI.
                                })
                        )
                        .build(BreakFFA.getInstance().getGuiManager());
                }
            }.evaluate()
        ),

        CLOSEST_PLAYER (
            new Object() {
                HashItem evaluate() {
                    return new HashSkull()
                        .setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI5YjE5YWQzYzUxZWRhZDY0M2U0NGRkNWRhNmI5ZDczMTc1NGMxNWE5ZmZiZjgxYzMyYzI4ODZjOTI5MDU3NyJ9fX0=")
                        .setName(Component.text(ChatColor.DARK_GREEN + "Proximité"))
                        .setLore(Arrays.asList(
                            Component.text(ChatColor.GRAY + "Cliquez pour vous " + ChatColor.BLUE + "téléporter" + ChatColor.GRAY + " au"),
                            Component.text(ChatColor.GRAY + "joueur le plus proche.")
                        ))
                        .addClickHandler(
                            new ClickHandler()
                                .addAllClickTypes()
                                .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                                    final PlayerManager playerManager = MAIN.getGameManager().getPlayerManager(player);

                                    playerManager.getSpectatorMode().teleportToClosestPlayer();
                                    gui.close(player);
                                })
                        )
                        .build(BreakFFA.getInstance().getGuiManager());
                }
            }.evaluate()
        ),

        QUIT (
            new Object() {
                HashItem evaluate() {
                    return new HashSkull()
                        .setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ5NWVlNThkNzg4MmNkZTEyZmM4NzE1Y2EwZjIxN2IzNWFlZThkMjgzYTIwZjc0YmVhZTk2OTJmOWI4MGVlMyJ9fX0=")
                        .setName(Component.text(ChatColor.DARK_GRAY + "Quitter"))
                        .setLore(Arrays.asList(
                            Component.text(ChatColor.GRAY + "Cliquez pour quitter le mode " + ChatColor.DARK_AQUA + "fantôme"),
                            Component.text(ChatColor.DARK_AQUA + "spectateur" + ChatColor.GRAY + " et redevenir mortel.")
                        ))
                        .addClickHandler(
                            new ClickHandler()
                                .addAllClickTypes()
                                .setClickAction((Player player, HashGui gui, ItemStack item, int slot) -> {
                                    final PlayerManager playerManager = MAIN.getGameManager().getPlayerManager(player);

                                    playerManager.getSpectatorMode().exit();
                                    gui.close(player);
                                })
                        )
                        .build(BreakFFA.getInstance().getGuiManager());
                }
            }.evaluate()
        );


        private final HashItem item;


        Items(HashItem item)
        {
            this.item = item;
        }


        @Override
        public HashItem getItem()
        {
            return this.item;
        }

        @Override
        public int getSlotIndex()
        {
            return -1;
        }

        @Override
        public void give(Player player) {}

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

        final HashItem blackGlass = HashItem.separator(Material.BLACK_STAINED_GLASS_PANE, guiManager);
        final HashItem grayGlass = HashItem.separator(Material.GRAY_STAINED_GLASS_PANE, guiManager);

        final Mask mask = new Mask(inventory);

        mask.setItem('b', blackGlass)
            .setItem('g', grayGlass)
            .setItem('T', Items.TELEPORT.getItem())
            .setItem('P', Items.CLOSEST_PLAYER.getItem())
            .setItem('Q', Items.QUIT.getItem());

        mask.pattern(2, "bbgggggbb")
            .pattern(3, "bggTPQggb")
            .pattern(4, "bbgggggbb");

        mask.apply();
    }

}
