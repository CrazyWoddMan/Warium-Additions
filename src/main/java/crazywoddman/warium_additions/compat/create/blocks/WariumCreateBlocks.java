package crazywoddman.warium_additions.compat.create.blocks;

import crazywoddman.warium_additions.compat.create.blocks.converters.KineticConverterBlock;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import static crazywoddman.warium_additions.compat.create.WariumCreate.REGISTRATE;

public class WariumCreateBlocks {
    private static final DeferredRegister<Block> WARIUM_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "crusty_chunks");

    public static void register(IEventBus bus) {
        WARIUM_REGISTRY.register(bus);
    }

    static {
        WARIUM_REGISTRY.register("firebox", ModifiedFireboxBlock::new);
        WARIUM_REGISTRY.register("oil_firebox", ModifiedOilFireboxBlock::new);
        WARIUM_REGISTRY.register("electric_firebox", ModifiedElectricFireboxBlock::new);
    }
    
    public static final BlockEntry<RotationConverterBlock> ROTATION_CONVERTER = REGISTRATE
        .block("rotation_converter", RotationConverterBlock::new)
        .properties(p -> p.strength(1).mapColor(MapColor.METAL).sound(SoundType.ANVIL))
        .simpleItem()
        .register();

    public static final BlockEntry<KineticConverterBlock> KINETIC_CONVERTER = REGISTRATE
        .block("kinetic_converter", KineticConverterBlock::new)
        .properties(p -> p.strength(1).mapColor(MapColor.METAL).sound(SoundType.ANVIL))
        .simpleItem()
        .register();
}
