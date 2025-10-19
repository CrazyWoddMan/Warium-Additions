package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.block.ControllableTrigger;
import crazywoddman.warium_additions.block.ModifiedElectricFireboxBlock;
import crazywoddman.warium_additions.block.ModifiedFireboxBlock;
import crazywoddman.warium_additions.block.ModifiedOilFireboxBlock;
import crazywoddman.warium_additions.block.ModifiedRefineryTowerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryBlocks {
    public static final DeferredRegister<Block> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "crusty_chunks");
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, WariumAdditions.MODID);

    public static final RegistryObject<Block> CONTROLLABLE_TRIGGER = REGISTRY.register("controllable_trigger", ControllableTrigger::new);
    public static final RegistryObject<Block> REFINERY_TOWER = WARIUM_REGISTRY.register("refinery_tower", () -> new ModifiedRefineryTowerBlock(Properties.of().randomTicks()));

    public static final RegistryObject<Block> FIREBOX = WariumAdditions.createLoaded ? WARIUM_REGISTRY.register("firebox", ModifiedFireboxBlock::new) : null;
    public static final RegistryObject<Block> OIL_FIREBOX = WariumAdditions.createLoaded ? WARIUM_REGISTRY.register("oil_firebox", ModifiedOilFireboxBlock::new) : null;
    public static final RegistryObject<Block> ELECTRIC_FIREBOX = WariumAdditions.createLoaded ? WARIUM_REGISTRY.register("electric_firebox", ModifiedElectricFireboxBlock::new) : null;
}
