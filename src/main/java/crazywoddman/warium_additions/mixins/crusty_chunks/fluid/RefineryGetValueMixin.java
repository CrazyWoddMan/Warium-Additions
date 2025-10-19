package crazywoddman.warium_additions.mixins.crusty_chunks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
    targets = {
        "net.mcreator.crustychunks.procedures.RefineryOnTickUpdateProcedure$1",
        "net.mcreator.crustychunks.procedures.RefineryOnTickUpdateProcedure$2",
        "net.mcreator.crustychunks.procedures.RefineryOnTickUpdateProcedure$3",
        "net.mcreator.crustychunks.procedures.RefineryOnTickUpdateProcedure$4"
    },
    remap = false
)
public class RefineryGetValueMixin {

    @Inject(
        method = "getValue",
        at = @At("HEAD"),
        cancellable = true
    )
    private void replaceGetValue(LevelAccessor world, BlockPos pos, String tag, CallbackInfoReturnable<Double> cir) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity != null) {
            IFluidHandler handler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).orElse(null);

            if (handler != null) {
                cir.setReturnValue((double)handler.getFluidInTank(0).getAmount());
                return;
            }
        }

        cir.setReturnValue(-1.0);
    }
}
