package crazywoddman.warium_additions.compat.create;

import crazywoddman.warium_additions.compat.create.blocks.converters.KineticConverterBlockEntity;
import crazywoddman.warium_additions.compat.create.blocks.converters.KineticConverterRenderer;
import crazywoddman.warium_additions.compat.create.blocks.converters.OldRotationConverterBlock.OldRotationConverterBlockEntity;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterBlockEntity;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterRenderer;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterVisual;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import crazywoddman.warium_additions.compat.create.blocks.converters.OldKineticConverterBlock.OldKineticConverterBlockEntity;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class CreateBlockEntities {
    public static final BlockEntityEntry<RotationConverterBlockEntity> ROTATION_CONVERTER = Registrate.REGISTRATE
        .blockEntity("rotation_converter", RotationConverterBlockEntity::new)
        .visual(() -> RotationConverterVisual::new)
        .validBlocks(CreateBlocks.ROTATION_CONVERTER)
        .renderer(() -> RotationConverterRenderer::new)
        .register();

    public static final BlockEntityEntry<OldRotationConverterBlockEntity> OLD_ROTATION_CONVERTER = Registrate.REGISTRATE_OLDID
        .blockEntity("converter_out", OldRotationConverterBlockEntity::new)
        .validBlocks(CreateBlocks.OLD_ROTATION_CONVERTER)
        .register();

    public static final BlockEntityEntry<KineticConverterBlockEntity> KINETIC_CONVERTER = Registrate.REGISTRATE
        .blockEntity("kinetic_converter", KineticConverterBlockEntity::new)
        .visual(() -> (context, blockEntity, partialTick) -> new OrientedRotatingVisual<>(
                context,
                blockEntity,
                partialTick,
                Direction.SOUTH,
                blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING),
                Models.partial(AllPartialModels.SHAFT_HALF)
        ))
        .validBlocks(CreateBlocks.KINETIC_CONVERTER)
        .renderer(() -> KineticConverterRenderer::new)
        .register();

    public static final BlockEntityEntry<OldKineticConverterBlockEntity> OLD_KINETIC_CONVERTER = Registrate.REGISTRATE_OLDID
        .blockEntity("converter_in", OldKineticConverterBlockEntity::new)
        .validBlocks(CreateBlocks.OLD_KINETIC_CONVERTER)
        .register();

    public static void register() {}
}
