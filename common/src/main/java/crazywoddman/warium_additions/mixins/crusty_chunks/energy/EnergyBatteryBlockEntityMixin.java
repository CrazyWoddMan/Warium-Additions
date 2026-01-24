package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import crazywoddman.warium_additions.WariumAdditions;
import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.BatteryProvider;
import net.mcreator.crustychunks.block.entity.EnergyBatteryBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.common.util.LazyOptional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnergyBatteryBlockEntity.class)
public class EnergyBatteryBlockEntityMixin {

    @Shadow(remap = false)
    private EnergyStorage energyStorage;
    
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 800000),
        remap = false
    )
    private int modifyCapacity(int original) {
        return Config.SERVER.batteryCapacity.get();
    }
    
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(intValue = 200),
        remap = false
    )
    private int modifyMaxTransfer(int original) {
        return Config.SERVER.energyTransferLimit.get();
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY && WariumAdditions.immersiveengineering)
            cir.setReturnValue(LazyOptional.of(() -> BatteryProvider.get((BlockEntity)(Object)this, this.energyStorage, facing)).cast());
    }
}
