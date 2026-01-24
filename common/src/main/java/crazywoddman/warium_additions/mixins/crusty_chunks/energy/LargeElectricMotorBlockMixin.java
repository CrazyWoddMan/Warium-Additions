package crazywoddman.warium_additions.mixins.crusty_chunks.energy;

import net.mcreator.crustychunks.block.LargeElectricMotorBlock;
import net.mcreator.crustychunks.block.entity.LargeElectricMotorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazywoddman.warium_additions.config.Config;
import crazywoddman.warium_additions.util.WariumAdditionsUtil;

@Mixin(LargeElectricMotorBlockEntity.class)
public class LargeElectricMotorBlockMixin {

    @Unique
    private EnergyStorage energyStorage;
    private final int limit = Config.SERVER.energyTransferLimit.get();
    private final int maxThrottle = Config.SERVER.maxThrottle.get();
    private final int consumptionLimit = Config.SERVER.motorConsumptionLimit.get();

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void injectEnergyStorage(BlockPos pos, BlockState state, CallbackInfo ci) {
        BlockEntity blockEntity = (BlockEntity)(Object)this;
        this.energyStorage = new EnergyStorage(this.limit, this.limit, 0) {

            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                int receive = blockEntity.getLevel().hasNeighborSignal(pos) ? 0 : Math.round(
                    Math.min(Math.min(this.capacity, consumptionLimit), maxReceive)
                    / (float)maxThrottle
                    * WariumAdditionsUtil.getThrottle(blockEntity, maxThrottle)
                );

                if (!simulate)
                    this.energy = receive;
            
                return receive;
            }
        };
    }

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
        compound.put(key, this.energyStorage.serializeNBT());

        if (this.energyStorage.getEnergyStored() > 0)
            this.energyStorage.receiveEnergy(0, false);

        return compound;
    }

    @Inject(
        method = "getCapability",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void injectEnergyCapability(Capability<?> capability, Direction facing, CallbackInfoReturnable<LazyOptional<?>> cir) {
        if (capability == ForgeCapabilities.ENERGY && (facing == null || facing == ((BlockEntity)(Object)this).getBlockState().getValue(LargeElectricMotorBlock.FACING).getOpposite()))
            cir.setReturnValue(LazyOptional.of(() -> this.energyStorage).cast());
    }
}
