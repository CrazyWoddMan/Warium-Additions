package crazywoddman.warium_additions.compat.create;

import java.util.function.Supplier;

import crazywoddman.warium_additions.compat.create.items.YellowcakeItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class WariumCreateItems {
    public static final Supplier<YellowcakeItem> YELLOWCAKE = Registrate.REGISTRATE
        .defaultCreativeTab((ResourceKey<CreativeModeTab>)null)
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
        
    public static final Supplier<Item> PETROLIUM_BOTTLE = Registrate.REGISTRATE
        .defaultCreativeTab((ResourceKey<CreativeModeTab>)null)
        .item("petrolium_bottle", Item::new)
        .properties(p -> p.stacksTo(16))
        .register();

    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        ResourceLocation tab = event.getTabKey().location();

        if (!tab.getNamespace().equals("crusty_chunks"))
            return;

        switch (tab.getPath()) {
            case "crusty_production" ->  event.accept(WariumCreateItems.YELLOWCAKE.get());
            case "warium_logistics" -> {
                event.accept(WariumCreateBlocks.KINETIC_CONVERTER::asItem);
                event.accept(WariumCreateBlocks.ROTATION_CONVERTER::asItem);
            }
            case "crusty_components" -> {
                event.accept(PETROLIUM_BOTTLE);
                event.accept(WariumCreateFluids.YELLOWCAKE_FLUID.get().getBucket());
            }
        }
    }

    public static void register() {}
}
