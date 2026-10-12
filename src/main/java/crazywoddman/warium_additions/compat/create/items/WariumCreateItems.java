package crazywoddman.warium_additions.compat.create.items;

import java.util.function.Supplier;

import com.tterrag.registrate.util.entry.RegistryEntry;

import crazywoddman.warium_additions.WariumAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import static crazywoddman.warium_additions.compat.create.WariumCreate.REGISTRATE;

public class WariumCreateItems {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WariumAdditions.MODID);
    static {
        CREATIVE_TABS.register(WariumAdditions.MODID,
            () -> CreativeModeTab
                .builder()
                .title(Component.translatable("itemGroup." + WariumAdditions.MODID))
                .icon(() -> WariumCreateItems.YELLOWCAKE.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    for (RegistryEntry<Item> item : REGISTRATE.getAll(Registries.ITEM))
                        output.accept(item.get());
                })
                .build()
        );
    }
    
    public static final Supplier<YellowcakeItem> YELLOWCAKE = REGISTRATE
        .item("yellowcake", YellowcakeItem::new)
        .properties(p -> p
            .food(new FoodProperties.Builder()
                .nutrition(20)
                .saturationMod(20_000_000)
                .alwaysEat()
                .build()
            )
        )
        .register();
        
    public static final Supplier<Item> PETROLIUM_BOTTLE = REGISTRATE
        .item("petrolium_bottle", Item::new)
        .properties(p -> p.stacksTo(16))
        .register();

    public static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);
    }
}
