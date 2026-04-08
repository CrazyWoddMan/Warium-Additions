package crazywoddman.warium_additions.mixins.crusty_chunks;

import net.mcreator.crustychunks.block.RedirectorShaftBlock;
import net.mcreator.crustychunks.procedures.RedirectorShaftTickProcedure;
import net.mcreator.crustychunks.procedures.TestShaftProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedirectorShaftTickProcedure.class)
public class RedirectorShaftTickProcedureMixin {

     @Inject(
        method = "execute",
        at = @At("HEAD"),
        remap = false,
        cancellable = true
    )
    private static void altExecute(LevelAccessor world, double x, double y, double z, BlockState blockstate, CallbackInfo ci) {
        ci.cancel();

        if (world.isClientSide())
            return;

        Direction facing = blockstate.getValue(RedirectorShaftBlock.FACING);
        double power = 0;

        for (Direction side : Direction.values())
            if (side != facing)
                power += TestShaftProcedure.execute(world, x, y, z, blockstate, side);

        world.getBlockEntity(BlockPos.containing(x, y, z)).getPersistentData().putDouble("KineticPower", power);
    }
}
