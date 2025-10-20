package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.block.ControllableTriggerBlockEntity;
import crazywoddman.warium_additions.block.OldControllableTriggerBlock.OldControllableTriggerBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WariumAdditions.MODID);
    public static final DeferredRegister<BlockEntityType<?>> OLD_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "warium_create");
    
    public static final RegistryObject<BlockEntityType<?>> CONTROLLABLE_TRIGGER = register(
        REGISTRY,
        "controllable_trigger",
        RegistryBlocks.CONTROLLABLE_TRIGGER,
        ControllableTriggerBlockEntity::new
    );
    public static final RegistryObject<BlockEntityType<?>> OLD_CONTROLLABLE_TRIGGER = register(
        OLD_REGISTRY,
        "controllable_trigger",
        RegistryBlocks.OLD_CONTROLLABLE_TRIGGER,
        OldControllableTriggerBlockEntity::new
    );

    private static RegistryObject<BlockEntityType<?>> register(DeferredRegister<BlockEntityType<?>> registry, String registryname, RegistryObject<Block> block, BlockEntitySupplier<?> supplier) {
        return registry.register(registryname, () -> Builder.of(supplier, new Block[]{block.get()}).build(null));
    }
}
