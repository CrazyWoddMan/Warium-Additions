package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.procedures.SolarGeneratorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
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
    private static void cancelExecute(LevelAccessor world, double x, double y, double z, CallbackInfo ci) {
        BlockPos pos = BlockPos.containing(x, y, z);

        if (world instanceof Level level && !level.isClientSide() && level.isDay() && world.canSeeSkyFromBelowWater(pos)) {
            BlockEntity belowBlock = world.getBlockEntity(pos.below());

            if (belowBlock != null)
                belowBlock.getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> {
                    if (storage.canReceive())
                        storage.receiveEnergy(Config.SERVER.solarGeneration.get() * 20, false);
                });
        }

        ci.cancel();
    }
}
