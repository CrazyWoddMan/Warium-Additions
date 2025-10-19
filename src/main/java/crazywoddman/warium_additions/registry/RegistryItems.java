package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.item.ModifiedCuttersItem;
import crazywoddman.warium_additions.item.ModifiedEnergyMeterItem;
import crazywoddman.warium_additions.item.ModifiedHammerItem;
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

    public static final RegistryObject<Item> CRUDE_OIL_BUCKET = registerBucket(CrustyChunksModFluids.CRUDE_OIL);
    public static final RegistryObject<Item> OIL_BUCKET = registerBucket(CrustyChunksModFluids.OIL);
    public static final RegistryObject<Item> DIESEL_BUCKET = registerBucket(CrustyChunksModFluids.DIESEL);
    public static final RegistryObject<Item> KEROSENE_BUCKET = registerBucket(CrustyChunksModFluids.KEROSENE);
    public static final RegistryObject<Item> PETROLIUM_BUCKET = registerBucket(CrustyChunksModFluids.PETROLIUM);

    public static final RegistryObject<Item> CONTROLLABLE_TRIGGER = registerBlock(RegistryBlocks.CONTROLLABLE_TRIGGER);
    
    public static final RegistryObject<Item> HAMMER = WariumAdditions.IEloaded ? WARIUM_REGISTRY.register("hammer", ModifiedHammerItem::new) : null;
    public static final RegistryObject<Item> CUTTERS = WariumAdditions.IEloaded ? WARIUM_REGISTRY.register("cutters", ModifiedCuttersItem::new) : null;
    public static final RegistryObject<Item> ENERGY_METER = WariumAdditions.IEloaded ? WARIUM_REGISTRY.register("energy_meter", ModifiedEnergyMeterItem::new) : null;

    private static RegistryObject<Item> registerBucket(RegistryObject<FlowingFluid> fluid) {
        return WARIUM_REGISTRY.register(fluid.getId().getPath() + "_bucket", () -> new BucketItem(fluid, new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

    private static RegistryObject<Item> registerBlock(RegistryObject<Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Properties()));
    }
}
