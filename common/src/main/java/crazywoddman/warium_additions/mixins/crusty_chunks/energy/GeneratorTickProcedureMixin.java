package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.GeneratorBlock;
import net.mcreator.crustychunks.init.CrustyChunksModParticleTypes;
import net.mcreator.crustychunks.procedures.GeneratorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import crazywoddman.warium_additions.config.Config;

@Mixin(GeneratorTickProcedure.class)
public class GeneratorTickProcedureMixin {

    @Inject(
        method = "execute",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void rewriteExecute(LevelAccessor world, double x, double y, double z, BlockState blockstate, CallbackInfo ci) {
        if (world instanceof ServerLevel level) {
            BlockPos pos = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            Direction facing = blockEntity.getBlockState().getValue(GeneratorBlock.FACING);
            blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
                if (Optional.ofNullable(level.getBlockEntity(pos.relative(facing.getOpposite()))).map(backBlock -> {
                    int energy = (int)Math.round(backBlock.getPersistentData().getDouble("KineticPower") * Config.SERVER.kineticToFeRate.get());

                    if (energy > 0) {
                        level.sendParticles(CrustyChunksModParticleTypes.SPARKS.get(), x + 0.5, y + 0.5, z + 0.5, 2, 0.3, 0.3, 0.3, 1.0);
                        storage.receiveEnergy(energy, false);
                        Optional.ofNullable(level.getBlockEntity(pos.relative(facing))).ifPresent(frontBlock ->
                            frontBlock.getCapability(ForgeCapabilities.ENERGY, facing.getOpposite()).ifPresent(handler -> {
                                if (handler.canReceive())
                                    handler.receiveEnergy(energy, false);
                            })
                        );

                        return false;
                    }

                    return true;
                 }).orElse(true) && level.getGameTime() % 20 == 0)
                    storage.receiveEnergy(0, false);

            });
        }

        ci.cancel();
    }
}
