package crazywoddman.warium_additions.items;

import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WadditionsItems {
    public static void register(IEventBus bus) {
        WARIUM_REGISTRY.register(bus);
    }
    
    private static final DeferredRegister<Item> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "crusty_chunks");

    static {
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
}
