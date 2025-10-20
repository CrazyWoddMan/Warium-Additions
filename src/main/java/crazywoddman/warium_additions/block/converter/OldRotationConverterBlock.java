package crazywoddman.warium_additions.block.converter;

import crazywoddman.warium_additions.block.converter.OldKineticConverterBlock.OldKineticConverterBlockEntity;
import crazywoddman.warium_additions.registrate.CreateBlockEntities;
import crazywoddman.warium_additions.registrate.CreateBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class OldRotationConverterBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public OldRotationConverterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OldKineticConverterBlockEntity(CreateBlockEntities.OLD_KINETIC_CONVERTER.get(), pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : (lvl, pos, bs, blockEntity) -> ((OldRotationConverterBlockEntity)blockEntity).tick();
    }

    public static class OldRotationConverterBlockEntity extends BlockEntity {

        public OldRotationConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(CreateBlockEntities.OLD_ROTATION_CONVERTER.get(), pos, state);
        }

        public void tick() {
            if (level != null && !level.isClientSide()) {
                BlockState oldState = getBlockState();
                CompoundTag NBT = saveWithoutMetadata();
                level.setBlock(
                    worldPosition,
                    CreateBlocks.ROTATION_CONVERTER
                    .get()
                    .defaultBlockState()
                    .setValue(FACING, oldState.getValue(FACING))
                    .setValue(FACE, oldState.getValue(FACE)),
                    UPDATE_ALL
                );
                BlockEntity newBlockEntity = level.getBlockEntity(worldPosition);
                
                if (newBlockEntity != null) {
                    newBlockEntity.load(NBT);
                    newBlockEntity.setChanged();
                }
            }
        }
    }
}
