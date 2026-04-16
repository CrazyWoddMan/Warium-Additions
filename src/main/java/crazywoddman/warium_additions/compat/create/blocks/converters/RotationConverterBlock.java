package crazywoddman.warium_additions.compat.create.blocks.converters;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import crazywoddman.warium_additions.compat.create.WariumCreateBlockEntities;
import crazywoddman.warium_additions.compat.create.Shaper;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RotationConverterBlock extends DirectionalKineticBlock implements IBE<RotationConverterBlockEntity> {
    public static final BooleanProperty DIAL = BooleanProperty.create("dial");
    public static final VoxelShaper SHAPE;
    static {
        VoxelShaper base = Shaper
        .shape(0, 0, 0, 16, 16, 4)
        .add(2, 2, 4, 14, 14, 15)
        .forDirectional();
        SHAPE = base.withVerticalShapes(base.get(Direction.DOWN));
    }
    
    public RotationConverterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, DIAL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(DIAL, context.getClickedFace().getAxis() != Direction.Axis.Y);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING).getOpposite();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public Class<RotationConverterBlockEntity> getBlockEntityClass() {
        return RotationConverterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RotationConverterBlockEntity> getBlockEntityType() {
        return WariumCreateBlockEntities.ROTATION_CONVERTER.get();
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (context.getClickedFace().getAxis().equals(state.getValue(FACING).getAxis())) {
            state = state.setValue(DIAL, !state.getValue(DIAL));
            IWrenchable.playRotateSound(context.getLevel(), context.getClickedPos());
        }

        return super.onWrenched(state, context);
    }
}
