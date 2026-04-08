package crazywoddman.warium_additions.blocks;

import java.util.function.Supplier;

import crazywoddman.warium_additions.WariumAdditions;
import net.mcreator.crustychunks.init.CrustyChunksModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WariumAdditionsBlocks {
    public static void register(IEventBus bus) {
        WariumAdditionsBlocks.WARIUM_REGISTRY.register(bus);
    }
    
    private static final DeferredRegister<Block> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "crusty_chunks");

    static {
        override(CrustyChunksModBlocks.REFINERY_TOWER, ModifiedRefineryTower::new);
        override(CrustyChunksModBlocks.REDIRECTOR_SHAFT, ModifiedRedirectorShaft::new);

        if (WariumAdditions.IMMERSIVEENGINEERING)
            override(CrustyChunksModBlocks.ENERGY_BATTERY, ModifiedEnergyBattery::new);
    }

    private static void override(RegistryObject<Block> target, Supplier<Block> replacement) {
        WARIUM_REGISTRY.register(target.getId().getPath(), replacement);
    }
}
