package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.SolarGeneratorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;

@Mixin(SolarGeneratorTickProcedure.class)
public class SolarGeneratorTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void rewriteExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);

        if (world instanceof ServerLevel level) {
            if(level.isDay() && world.canSeeSkyFromBelowWater(pos)) {
                BlockEntity belowBlock = world.getBlockEntity(pos.below());

                if (belowBlock != null)
                    belowBlock.getCapability(ForgeCapabilities.ENERGY, Direction.UP).ifPresent(storage -> {
                        if (storage.canReceive())
                            storage.receiveEnergy(Config.SERVER.solarGeneration.get() * 20, false);
                    });
            }

            BlockState state = level.getBlockState(pos);
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
        }

        ci.cancel();
    }
}
