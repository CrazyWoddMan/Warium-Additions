package crazywoddman.warium_additions.compat.create.blocks;

import crazywoddman.warium_additions.compat.create.WariumCreate;
import crazywoddman.warium_additions.compat.create.blocks.converters.KineticConverterBlockEntity;
import crazywoddman.warium_additions.compat.create.blocks.converters.KineticConverterRenderer;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterBlockEntity;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterRenderer;
import crazywoddman.warium_additions.compat.create.blocks.converters.RotationConverterVisual;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class WariumCreateBlockEntities {
    public static final BlockEntityEntry<RotationConverterBlockEntity> ROTATION_CONVERTER = WariumCreate.REGISTRATE
        .blockEntity("rotation_converter", RotationConverterBlockEntity::new)
        .visual(() -> RotationConverterVisual::new)
        .renderer(() -> RotationConverterRenderer::new)
        .validBlocks(WariumCreateBlocks.ROTATION_CONVERTER)
        .register();

    public static final BlockEntityEntry<KineticConverterBlockEntity> KINETIC_CONVERTER = WariumCreate.REGISTRATE
        .blockEntity("kinetic_converter", KineticConverterBlockEntity::new)
        .visual(() -> (context, blockEntity, partialTick) -> new OrientedRotatingVisual<>(
            context,
            blockEntity,
            partialTick,
            Direction.SOUTH,
            blockEntity.getBlockState().getValue(BlockStateProperties.FACING),
            Models.partial(AllPartialModels.SHAFT_HALF)
        ))
        .renderer(() -> KineticConverterRenderer::new)
        .validBlocks(WariumCreateBlocks.KINETIC_CONVERTER)
        .register();

    public static void register() {}
}
