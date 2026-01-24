package crazywoddman.warium_additions.compat.create;

import java.util.Map;
import java.util.function.Supplier;

import com.tterrag.registrate.util.entry.ItemEntry;

import crazywoddman.warium_additions.compat.create.items.YellowcakeItem;
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

    public static final Map<String, Supplier<Item>> ITEMS = Map.of(
        "kinetic_converter", CreateBlocks.KINETIC_CONVERTER::asItem,
        "rotation_converter", CreateBlocks.ROTATION_CONVERTER::asItem,
        "yellowcake", YELLOWCAKE::get,
        "petrolium_bottle", PETROLIUM_BOTTLE::get
    );

    public static void register() {}
}
