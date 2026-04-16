package crazywoddman.warium_additions.compat.create.blocks.converters;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import crazywoddman.warium_additions.compat.create.WariumCreateBlockEntities;
import crazywoddman.warium_additions.data.WariumAdditionsTags;
import crazywoddman.warium_additions.compat.create.Shaper;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

// TODO add vertical placement
public class KineticConverterBlock extends DirectionalKineticBlock implements IBE<KineticConverterBlockEntity> {
    public static final VoxelShaper SHAPE;
    static {
        VoxelShaper base = Shaper
        .shape(0, 0, 10, 16, 16, 16)
        .add(1, 1, 2, 15, 15, 10)
        .add(0, 0, 0, 16, 16, 2)
        .forDirectional();
        SHAPE = base.withVerticalShapes(base.get(Direction.DOWN));
    }

    public KineticConverterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }

    @Override
    public Direction getPreferredFacing(BlockPlaceContext context) {
        Direction preferred = super.getPreferredFacing(context);

        if (preferred == null) {
            for (Direction side : Iterate.directions) {
                BlockState state = context.getLevel().getBlockState(context.getClickedPos().relative(side));

                if (state.is(WariumAdditionsTags.Blocks.KINETIC_OUTPUT_FRONT) &&
                    state.getValue(state.hasProperty(BlockStateProperties.FACING) ? BlockStateProperties.FACING : BlockStateProperties.HORIZONTAL_FACING) == side.getOpposite()
                ) {
                    if (preferred == null) {
                        preferred = side;
                    } else {
                        preferred = null;
                        break;
                    }
                }
            }
        } else preferred = preferred.getOpposite();

        return preferred == null ? null : preferred;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public Class<KineticConverterBlockEntity> getBlockEntityClass() {
        return KineticConverterBlockEntity.class;
    }

    @Override
    public BlockEntityType<KineticConverterBlockEntity> getBlockEntityType() {
        return WariumCreateBlockEntities.KINETIC_CONVERTER.get();
    }
}
