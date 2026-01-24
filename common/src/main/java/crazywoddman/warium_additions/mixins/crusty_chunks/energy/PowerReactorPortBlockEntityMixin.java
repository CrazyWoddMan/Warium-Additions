package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import crazywoddman.warium_additions.config.Config;
import net.mcreator.crustychunks.block.entity.PowerReactorPortBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.common.util.LazyOptional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowerReactorPortBlockEntity.class)
public class PowerReactorPortBlockEntityMixin {

    @Unique
    private final EnergyStorage energyStorage = new EnergyStorage(
        Config.SERVER.powerReactorCapacity.get(),
        Config.SERVER.energyTransferLimit.get(),
        Config.SERVER.energyTransferLimit.get()
    );

    @Redirect(
        method = "load",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraftforge/energy/EnergyStorage;deserializeNBT(Lnet/minecraft/nbt/Tag;)V",
            remap = false
        )
    )
    private void redirectDeserializeNBT(EnergyStorage storage, Tag tag) {
        this.energyStorage.deserializeNBT(tag);
    }

    @Redirect(
        method = "saveAdditional",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;"
        )
    )
    private Tag redirectCompoundPut(CompoundTag compound, String key, Tag tag) {
        return compound.put("energyStorage", this.energyStorage.serializeNBT());
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY && (facing == null || facing == Direction.DOWN))
            cir.setReturnValue(LazyOptional.of(() -> this.energyStorage).cast());
    }
}
