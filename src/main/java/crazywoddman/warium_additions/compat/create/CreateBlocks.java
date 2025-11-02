package crazywoddman.warium_additions.compat.create;

import crazywoddman.warium_additions.compat.create.blocks.converters.KineticConverterBlock;
import crazywoddman.warium_additions.compat.create.blocks.converters.OldKineticConverterBlock;
import crazywoddman.warium_additions.compat.create.blocks.converters.OldRotationConverterBlock;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterBlock;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class CreateBlocks {
    
    public static final BlockEntry<RotationConverterBlock> ROTATION_CONVERTER = Registrate.REGISTRATE
        .block("rotation_converter", RotationConverterBlock::new)
        .properties(p -> p.strength(1.0F).mapColor(MapColor.METAL).sound(SoundType.ANVIL))
        .simpleItem()
        .register();

    public static final BlockEntry<OldRotationConverterBlock> OLD_ROTATION_CONVERTER = Registrate.REGISTRATE_OLDID
        .block("converter_out", OldRotationConverterBlock::new)
        .register();

    public static final BlockEntry<KineticConverterBlock> KINETIC_CONVERTER = Registrate.REGISTRATE
        .block("kinetic_converter", KineticConverterBlock::new)
        .properties(p -> p.strength(1.0F).mapColor(MapColor.METAL).sound(SoundType.ANVIL))
        .simpleItem()
        .register();

    public static final BlockEntry<OldKineticConverterBlock> OLD_KINETIC_CONVERTER = Registrate.REGISTRATE_OLDID
        .block("converter_in", OldKineticConverterBlock::new)
        .register();

    public static void register() {}
}
