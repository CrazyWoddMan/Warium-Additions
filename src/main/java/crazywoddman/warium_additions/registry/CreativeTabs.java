package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.registrate.CreateBlocks;
import crazywoddman.warium_additions.registrate.CreateFluids;
import crazywoddman.warium_additions.registrate.CreateItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WariumAdditions.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabs {

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (WariumAdditions.createLoaded) {
            if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "warium_logistics"))) {
                event.accept(CreateBlocks.KINETIC_CONVERTER.asStack());
                event.accept(CreateBlocks.ROTATION_CONVERTER.asStack());
            }

            if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "crusty_production")))
                event.accept(CreateItems.YELLOWCAKE.get());

            if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("crusty_chunks", "crusty_components"))) {
                event.accept(CreateItems.PETROLIUM_BOTTLE.get());
                event.accept(CreateFluids.YELLOWCAKE_FLUID.getBucket().get());
            }
        }

        if (WariumAdditions.valkyrienWariumLoaded && event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath("valkyrien_warium", "warium_vs")))
            event.accept(crazywoddman.warium_additions.registry.RegistryItems.CONTROLLABLE_TRIGGER);
    }
}
