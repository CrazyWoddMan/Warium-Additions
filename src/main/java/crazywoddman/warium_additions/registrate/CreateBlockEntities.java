package crazywoddman.warium_additions.registrate;

import crazywoddman.warium_additions.block.converter.KineticConverterBlockEntity;
import crazywoddman.warium_additions.block.converter.KineticConverterInstance;
import crazywoddman.warium_additions.block.converter.KineticConverterRenderer;
import crazywoddman.warium_additions.block.converter.OldRotationConverterBlock.OldRotationConverterBlockEntity;
import crazywoddman.warium_additions.block.converter.RotationConverterBlockEntity;
import crazywoddman.warium_additions.block.converter.RotationConverterInstance;
import crazywoddman.warium_additions.block.converter.RotationConverterRenderer;
import crazywoddman.warium_additions.block.converter.OldKineticConverterBlock.OldKineticConverterBlockEntity;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CreateBlockEntities {
    public static final BlockEntityEntry<RotationConverterBlockEntity> ROTATION_CONVERTER = Registrate.REGISTRATE
        .blockEntity("rotation_converter", RotationConverterBlockEntity::new)
        .instance(() -> RotationConverterInstance::new, false)
        .validBlocks(CreateBlocks.ROTATION_CONVERTER)
        .renderer(() -> RotationConverterRenderer::new)
        .register();

    public static final BlockEntityEntry<OldRotationConverterBlockEntity> OLD_ROTATION_CONVERTER = Registrate.REGISTRATE_OLDID
        .blockEntity("converter_out", OldRotationConverterBlockEntity::new)
        .validBlocks(CreateBlocks.OLD_ROTATION_CONVERTER)
        .register();

    public static final BlockEntityEntry<KineticConverterBlockEntity> KINETIC_CONVERTER = Registrate.REGISTRATE
        .blockEntity("kinetic_converter", KineticConverterBlockEntity::new)
        .instance(() -> KineticConverterInstance::new, false)
        .validBlocks(CreateBlocks.KINETIC_CONVERTER)
        .renderer(() -> KineticConverterRenderer::new)
        .register();

    public static final BlockEntityEntry<OldKineticConverterBlockEntity> OLD_KINETIC_CONVERTER = Registrate.REGISTRATE_OLDID
        .blockEntity("converter_in", OldKineticConverterBlockEntity::new)
        .validBlocks(CreateBlocks.OLD_KINETIC_CONVERTER)
        .register();

    public static void register() {}
}
