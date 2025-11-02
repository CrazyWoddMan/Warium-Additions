package crazywoddman.warium_additions.blocks;

import net.mcreator.crustychunks.block.RefineryTowerBlock;
import net.mcreator.crustychunks.procedures.FuelTankTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


@SuppressWarnings("deprecation")
public class ModifiedRefineryTowerBlock extends RefineryTowerBlock {

    public ModifiedRefineryTowerBlock(Properties properties) {
        super();
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        FuelTankTickProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
        world.scheduleTick(pos, this, 1);
    }
}

