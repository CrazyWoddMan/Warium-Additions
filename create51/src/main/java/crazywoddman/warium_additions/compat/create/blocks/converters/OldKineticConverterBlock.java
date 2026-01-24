package crazywoddman.warium_additions.compat.create.blocks.converters;

import crazywoddman.warium_additions.compat.create.CreateBlockEntities;
import crazywoddman.warium_additions.compat.create.CreateBlocks;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class OldKineticConverterBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public OldKineticConverterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OldKineticConverterBlockEntity(CreateBlockEntities.OLD_KINETIC_CONVERTER.get(), pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : (lvl, pos, bs, blockEntity) -> ((OldKineticConverterBlockEntity)blockEntity).tick();
    }

    public static class OldKineticConverterBlockEntity extends BlockEntity {

        public OldKineticConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(CreateBlockEntities.OLD_KINETIC_CONVERTER.get(), pos, state);
        }
        
        private int counter;

        public void tick() {
            if (level != null && !level.isClientSide()) {
                if (counter++ == 60) {
                    BlockState oldState = getBlockState();
                    CompoundTag NBT = saveWithoutMetadata();
                    level.setBlock(
                        worldPosition,
                        CreateBlocks.KINETIC_CONVERTER
                        .get()
                        .defaultBlockState()
                        .setValue(FACING, oldState.getValue(FACING)),
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
}
