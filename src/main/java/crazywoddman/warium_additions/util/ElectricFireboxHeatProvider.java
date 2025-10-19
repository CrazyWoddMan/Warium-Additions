package crazywoddman.warium_additions.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;

public class ElectricFireboxHeatProvider {

    public static void setHeatLevel(String heatLevel, BlockPos pos, Level level) {
        BlockState currentState = level.getBlockState(pos);
        BlockState newState = currentState.setValue(BlazeBurnerBlock.HEAT_LEVEL, HeatLevel.valueOf(heatLevel));

        if (currentState != newState)
            level.setBlock(pos, newState, Block.UPDATE_INVISIBLE);
    }
}
