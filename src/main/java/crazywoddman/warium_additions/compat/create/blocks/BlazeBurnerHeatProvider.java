package crazywoddman.warium_additions.compat.create.blocks;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlazeBurnerHeatProvider {
    private static final Direction[] SIDES = {Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    public static void tick(BlockEntity burner, Level level, BlockPos pos) {
        if (level.isClientSide || !BlazeBurnerBlock.getHeatLevelOf(burner.getBlockState()).isAtLeast(BlazeBurnerBlock.HeatLevel.FADING))
            return;

        for (Direction side: SIDES) {
            BlockPos offset = pos.relative(side);
            BlockEntity blockEntity = level.getBlockEntity(offset);

            if (blockEntity != null) {
                CompoundTag data = blockEntity.getPersistentData();
                int heat = data.getInt("Heat");
                
                if (heat < 1000) {
                    data.putInt("Heat", heat + 1);
                    blockEntity.setChanged();
                }
            }
        }
    }
}
