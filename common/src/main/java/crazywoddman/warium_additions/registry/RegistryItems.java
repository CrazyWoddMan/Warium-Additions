package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIECuttersItem;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIEEnergyMeterItem;
import crazywoddman.warium_additions.compat.immersiveengineering.ModifiedIEHammerItem;
import crazywoddman.warium_additions.items.ModifiedEnergyMeterItem;
import net.mcreator.crustychunks.init.CrustyChunksModFluids;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WariumAdditions.MODID);
    public static final DeferredRegister<Item> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "crusty_chunks");

    public static final RegistryObject<Item> CONTROLLABLE_TRIGGER = registerBlock(RegistryBlocks.CONTROLLABLE_TRIGGER);
    
    static {
        if (WariumAdditions.immersiveengineering) {
            WARIUM_REGISTRY.register("hammer", ModifiedIEHammerItem::new);
            WARIUM_REGISTRY.register("cutters", ModifiedIECuttersItem::new);
        }

        WARIUM_REGISTRY.register("energy_meter", WariumAdditions.immersiveengineering ? ModifiedIEEnergyMeterItem::new : ModifiedEnergyMeterItem::new);

        registerBucket(CrustyChunksModFluids.CRUDE_OIL);
        registerBucket(CrustyChunksModFluids.OIL);
        registerBucket(CrustyChunksModFluids.DIESEL);
        registerBucket(CrustyChunksModFluids.KEROSENE);
        registerBucket(CrustyChunksModFluids.PETROLIUM);
    }

    private static RegistryObject<Item> registerBucket(RegistryObject<FlowingFluid> fluid) {
        return WARIUM_REGISTRY.register(fluid.getId().getPath() + "_bucket", () -> new BucketItem(fluid, new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

    private static RegistryObject<Item> registerBlock(RegistryObject<Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Properties()));
    }
}
