package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import crazywoddman.warium_additions.config.Config;
import net.mcreator.crustychunks.block.entity.ElectricFireboxBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
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

@Mixin(ElectricFireboxBlockEntity.class)
public class ElectricFireboxBlockEntityMixin {

    @Unique
    private EnergyStorage energyStorage;
    private final int capacity = Config.SERVER.electricFireboxCapacity.get();
    private final int consumption = Config.SERVER.electricFireboxConsumption.get() * 40;
    private final int transLimit = Config.SERVER.energyTransferLimit.get();

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void injectEnergyStorage(BlockPos pos, BlockState state, CallbackInfo ci) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        this.energyStorage = new EnergyStorage(this.capacity, transLimit, 0) {

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                int received = super.receiveEnergy(maxReceive, simulate);

                if (!simulate && received > 0) {
                    blockEntity.setChanged();
                    blockEntity.getLevel().sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                }

                return received;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                if (simulate || energy < consumption)
                    return 0;
                
                energy -= consumption;

                return consumption;
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
        if (capability == ForgeCapabilities.ENERGY)
            cir.setReturnValue(LazyOptional.of(() -> this.energyStorage).cast());
    }
}
