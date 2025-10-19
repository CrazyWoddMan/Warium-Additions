package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.entity.SolarGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.common.util.LazyOptional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;

@Mixin(SolarGeneratorBlockEntity.class)
public class SolarGeneratorBlockEntityMixin {

    @Unique
    private EnergyStorage energyStorage;
    private final int generation = Config.SERVER.solarGeneration.get();

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void injectEnergyStorage(BlockPos position, BlockState state, CallbackInfo ci) {
        this.energyStorage = new EnergyStorage(generation, 0, generation) {

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                if (simulate)
                    return 0;

                this.energy = maxReceive;
                
                return maxReceive;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return 0;
            }
        };
    }

    @Inject(
        method = "load",
        at = @At("TAIL")
    )
    private void injectRead(CompoundTag compound, CallbackInfo ci) {
        this.energyStorage.deserializeNBT(compound.get("energyStorage"));
    }

    @Inject(
        method = "saveAdditional",
        at = @At("TAIL")
    )
    private void injectSave(CompoundTag compound, CallbackInfo ci) {
        compound.put("energyStorage", this.energyStorage.serializeNBT());
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
