package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.hashgui.handler.click.ClickAction;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;
import java.util.List;

public class ShopCategoryItem
    extends HashItem
{

    /**
     * Creates a new Shop category item.
     * TODO:
     *   - Maybe only pass the class in the constructor,
     *     so this one will create the class itself, which
     *     can shorten the code.
     *     Tried but it's hard, but this may be cool to implement.
     *   - Assign a color to this item ; then append a click indicator to the lore
     *     based on the assigned color.
     *   - Auto build?
     *
     * @param   type    Item material
     * @param   name    Item name
     * @param   lore    Item lore
     * @param   action  Shop category to open
     */
    public ShopCategoryItem(
        Material type,
        Component name,
        List<Component> lore,
        ClickAction action
    )
    {
        super(type);

        super.setName(name)
            .setLore(lore)
            .setFlags(Arrays.asList(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_UNBREAKABLE
            ))
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction(action)
            );
    }

}
