package crazywoddman.warium_additions.mixins.crusty_chunks.weapons;

import net.mcreator.crustychunks.procedures.MissileHardpointFireProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import crazywoddman.warium_additions.util.WariumAdditionsUtil;

/**
 * <b>Creative Mode</b> handeling for hardpoints
 * <p><p>In this hardpoints won't consume missiles
 * @see WariumAdditionsUtil#CREATIVE_MODE
 */
@Mixin(MissileHardpointFireProcedure.class)
public class HardpointsCreativeMode {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
        )
    )
    private static boolean redirectSetBlock(LevelAccessor level, BlockPos pos, BlockState state, int update) {
        return level.getBlockState(pos).getValue(WariumAdditionsUtil.CREATIVE_MODE) ? false : level.setBlock(pos, state, update);
    }
}
