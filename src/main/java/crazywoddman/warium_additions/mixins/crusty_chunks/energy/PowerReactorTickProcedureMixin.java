package crazywoddman.warium_additions.mixins.crusty_chunks.energy;
import net.mcreator.crustychunks.procedures.PowerReactorTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;

@Mixin(
    value = PowerReactorTickProcedure.class,
    targets = "net.mcreator.crustychunks.procedures.PowerReactorTickProcedure$9"
)
public class PowerReactorTickProcedureMixin {

    @Redirect(
        method = "execute",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putDouble(Ljava/lang/String;D)V"
        ),
        require = 0
    )
    private static void redirectPutDouble(CompoundTag compound, String key, double value, LevelAccessor world, double x, double y, double z) {
        world
        .getBlockEntity(BlockPos.containing(x, y - 2.0, z))
        .getCapability(ForgeCapabilities.ENERGY)
        .orElse(null)
        .receiveEnergy(Config.SERVER.reactorGeneration.get() * 10, false);
    }

    @Inject(
        method = "getValue",
        at = @At("HEAD"),
        cancellable = true,
        remap = false,
        require = 0
    )
    private void modifyGetValue(LevelAccessor world, BlockPos pos, String tag, CallbackInfoReturnable<Double> cir) {
        IEnergyStorage storage = world
            .getBlockEntity(pos)
            .getCapability(ForgeCapabilities.ENERGY)
            .orElse(null);
        
        cir.setReturnValue(storage.getEnergyStored() < storage.getMaxEnergyStored() ? -1.0 : 0.0);
    }
}
