package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.entity.SolarGeneratorBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.common.util.LazyOptional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;

@Mixin(SolarGeneratorBlockEntity.class)
public class SolarGeneratorBlockEntityMixin {

    private boolean isDay;
    private final int generation = Config.SERVER.solarGeneration.get();

    @Inject(
        method = "load",
        at = @At("TAIL")
    )
    private void injectRead(CompoundTag compound, CallbackInfo ci) {
        this.isDay = compound.getBoolean("isDay");
    }

    @Inject(
        method = "saveAdditional",
        at = @At("TAIL")
    )
    private void injectSave(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("isDay", ((BlockEntity)(Object)this).getLevel().isDay());
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY) {
            BlockEntity blockEntity = (BlockEntity)(Object)this;
            Level level = blockEntity.getLevel();
            cir.setReturnValue(LazyOptional.of(() -> new EnergyStorage(
                generation,
                0,
                generation,
                ((level.isClientSide ? isDay : level.isDay()) && level.canSeeSky(blockEntity.getBlockPos()))
                    ? generation
                    : 0
            ) {
                @Override
                public int extractEnergy(int maxExtract, boolean simulate) {
                    return 0;
                }
            }).cast());
        }
    }
}
