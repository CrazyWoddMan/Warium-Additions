package crazywoddman.warium_additions.registry;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.blocks.ControllableTriggerBlock;
import crazywoddman.warium_additions.blocks.ModifiedEnergyBattery;
import crazywoddman.warium_additions.blocks.ModifiedRefineryTowerBlock;
import crazywoddman.warium_additions.blocks.OldControllableTriggerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryBlocks {
    public static final DeferredRegister<Block> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "crusty_chunks");
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, WariumAdditions.MODID);
    public static final DeferredRegister<Block> OLD_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "warium_create");

    public static final RegistryObject<Block> CONTROLLABLE_TRIGGER = REGISTRY.register("controllable_trigger", ControllableTriggerBlock::new);
    public static final RegistryObject<Block> OLD_CONTROLLABLE_TRIGGER = OLD_REGISTRY.register("controllable_trigger", OldControllableTriggerBlock::new);

    static {
        WARIUM_REGISTRY.register("refinery_tower", () -> new ModifiedRefineryTowerBlock(Properties.of().randomTicks()));

        if (WariumAdditions.immersiveengineering)
            WARIUM_REGISTRY.register("energy_battery", () -> new ModifiedEnergyBattery(Properties.of().randomTicks()));
    }
}
