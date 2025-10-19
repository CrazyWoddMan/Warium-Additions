package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.block.ControllableTriggerBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WariumAdditions.MODID);
    
    public static final RegistryObject<BlockEntityType<?>> CONTROLLABLE_TRIGGER = register(
        "controllable_trigger", RegistryBlocks.CONTROLLABLE_TRIGGER, ControllableTriggerBlockEntity::new
    );

    private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntitySupplier<?> supplier) {
        return REGISTRY.register(registryname, () -> Builder.of(supplier, new Block[]{block.get()}).build(null));
    }
}
