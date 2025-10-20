package crazywoddman.warium_additions.block;

import crazywoddman.warium_additions.registry.RegistryBlockEntities;
import crazywoddman.warium_additions.registry.RegistryBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class OldControllableTriggerBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public OldControllableTriggerBlock() {
        super(Properties.of());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OldControllableTriggerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : (lvl, pos, bs, blockEntity) -> ((OldControllableTriggerBlockEntity)blockEntity).tick();
    }

    public static class OldControllableTriggerBlockEntity extends BlockEntity {

        public OldControllableTriggerBlockEntity(BlockPos pos, BlockState state) {
            super(RegistryBlockEntities.OLD_CONTROLLABLE_TRIGGER.get(), pos, state);
        }

        public void tick() {
            if (level != null && !level.isClientSide()) {
                BlockState state = getBlockState();
                CompoundTag NBT = saveWithoutMetadata();
                level.setBlock(
                    worldPosition,
                    RegistryBlocks.CONTROLLABLE_TRIGGER
                    .get()
                    .defaultBlockState()
                    .setValue(FACING, state.getValue(FACING)),
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
