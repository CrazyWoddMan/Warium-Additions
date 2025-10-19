package crazywoddman.warium_additions.registrate;

import crazywoddman.warium_additions.item.YellowcakeItem;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class CreateItems {
    public static final ItemEntry<YellowcakeItem> YELLOWCAKE = Registrate.REGISTRATE
        .item("yellowcake", YellowcakeItem::new)
        .properties(p -> p
            .food(new FoodProperties.Builder()
                .nutrition(20)
                .saturationMod(20_000_000.0f)
                .alwaysEat()
                .build()
            )
        )
        .register();
        
    public static final ItemEntry<Item> PETROLIUM_BOTTLE = Registrate.REGISTRATE
        .item("petrolium_bottle", Item::new)
        .properties(p -> p.stacksTo(16))
        .register();

    public static void register() {}
}
