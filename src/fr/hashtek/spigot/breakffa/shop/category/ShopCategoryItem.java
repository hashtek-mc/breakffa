package fr.hashtek.spigot.breakffa.shop.category;

import fr.hashtek.spigot.hashgui.handler.click.ClickAction;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.Material;

import java.util.List;

public class ShopCategoryItem extends HashItem
{

    /**
     * Creates a new Shop category item.
     * TODO: Maybe only pass the class in the constructor,
     *       so this one will create the class itself, which
     *       can shorten the code.
     *       Tried but it's hard, but this may be cool to implement.
     *
     * @param   type    Item material
     * @param   name    Item name
     * @param   lore    Item lore
     * @param   action  Shop category to open
     */
    public ShopCategoryItem(
        Material type,
        String name,
        List<String> lore,
        ClickAction action
    )
    {
        super(type);

        super.setName(name)
            .setLore(lore)
            .addClickHandler(
                new ClickHandler()
                    .addAllClickTypes()
                    .setClickAction(action)
            );
    }

}
