package crazywoddman.warium_additions.items;

import java.util.function.Supplier;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.create.WariumCreateItems;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIECuttersItem;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIEEnergyMeterItem;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIEHammerItem;
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.mcreator.crustychunks.init.CrustyChunksModItems;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = WariumAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WariumAdditionsItems {
    public static void register(IEventBus bus) {
        WariumAdditionsItems.WARIUM_REGISTRY.register(bus);
    }
    
    private static final DeferredRegister<Item> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "crusty_chunks");
    
    static {
        if (WariumAdditions.IMMERSIVEENGINEERING) {
            override(CrustyChunksModItems.HAMMER, ModifiedIEHammerItem::new);
            override(CrustyChunksModItems.CUTTERS, ModifiedIECuttersItem::new);
            override(CrustyChunksModItems.ENERGY_METER, ModifiedIEEnergyMeterItem::new);
        }

        override(
            CrustyChunksModItems.ENERGY_METER,
            WariumAdditions.IMMERSIVEENGINEERING ? ModifiedIEEnergyMeterItem::new : ModifiedEnergyMeterItem::new
        );

        // fix to Warium's buckets not being recognized as buckets by other mods
        fixBucket(CrustyChunksModFluids.CRUDE_OIL);
        fixBucket(CrustyChunksModFluids.OIL);
        fixBucket(CrustyChunksModFluids.DIESEL);
        fixBucket(CrustyChunksModFluids.KEROSENE);
        fixBucket(CrustyChunksModFluids.PETROLIUM);
    }

    private static RegistryObject<Item> fixBucket(RegistryObject<FlowingFluid> fluid) {
        return WARIUM_REGISTRY.register(fluid.getId().getPath() + "_bucket", () -> new BucketItem(fluid, new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

    private static void override(RegistryObject<Item> target, Supplier<Item> replacement) {
        WARIUM_REGISTRY.register(target.getId().getPath(), replacement);
    }

    // might need this
    // private static RegistryObject<Item> registerBlock(RegistryObject<Block> block) {
    //     return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Properties()));
    // }

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (WariumAdditions.CREATE)
            WariumCreateItems.addToCreativeTab(event);
    }
}
